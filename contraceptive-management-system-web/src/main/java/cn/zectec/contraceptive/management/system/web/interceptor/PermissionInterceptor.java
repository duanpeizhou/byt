package cn.zectec.contraceptive.management.system.web.interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.zectec.contraceptive.management.system.model.Log.LogType;
import cn.zectec.contraceptive.management.system.security.service.SecurityContext;
import cn.zectec.contraceptive.management.system.service.IMenuService;
import cn.zectec.contraceptive.management.system.web.util.ServletUtil;

public class PermissionInterceptor extends HandlerInterceptorAdapter{
	private String noPermissionUrl;
	@Autowired
	private LogUtil logUtil;
	@Autowired
	private IMenuService menuService;
	
	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {		HttpSession session = request.getSession();
		Set<String> ownerUrl = (Set<String>)session.getAttribute("manager_menu_url");
		Set<String> allUrl = (Set<String>)session.getAttribute("all_menus_url");
		String str = ServletUtil.getURI(request).substring(1);
		if(allUrl!=null && ownerUrl==null && allUrl.contains(str)){
			if(!ownerUrl.contains(str)){
				response.sendRedirect(request.getContextPath()+noPermissionUrl);
				logUtil.wirte(request, LogType.UnauthorizedOperation, "超权操作", "用户超权操作，用户名为："+(SecurityContext.getCurrentManager()==null?"":SecurityContext.getCurrentManager().getUsername()));
				return false;
			}
		}
		return true;
	}

	public String getNoPermissionUrl() {
		return noPermissionUrl;
	}

	public void setNoPermissionUrl(String noPermissionUrl) {
		this.noPermissionUrl = noPermissionUrl;
	}
}
