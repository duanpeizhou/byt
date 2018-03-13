package cn.zectec.contraceptive.management.system.web.interceptor;

import cn.zectec.contraceptive.management.system.model.Log.LogType;
import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.security.service.SecurityContext;
import cn.zectec.contraceptive.management.system.service.IManagerService;
import cn.zectec.contraceptive.management.system.web.controller.ContraceptiveController;
import cn.zectec.contraceptive.management.system.web.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter{
	private List<String> needCheckUri = new ArrayList<String>();
	private String loginUri;
	private String logoutUri;
	private String loginHandleUri;
	private String loginMethod;
	private String loginSuccessUri;
	@Autowired
	private IManagerService managerService;
	@Autowired
	private LogUtil logUtil;
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		SecurityContext.attr(SecurityContext.IP, ServletUtil.getRealIp(request));
		HttpSession session = request.getSession();
		if(session.getAttribute("session_manager")!= null){
			Manager manager = (Manager)session.getAttribute("session_manager");
			manager = managerService.getManagerById(manager.getId());
			SecurityContext.registerManager(manager);
			
		}
		String uri = ServletUtil.getURI(request);
		if(SecurityContext.getCurrentManager()!= null){
			if(uri.equals(loginUri)||uri.equals(loginHandleUri)){
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+loginSuccessUri));
				return false;
			}
		}
		if(needCheckUri.contains(uri)){
			if(SecurityContext.getCurrentManager()==null){
				redirectLogin(request,response);
				//response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+loginUri));
				logUtil.wirte(request, LogType.LoginFailure,"需要登录","系统未登录或者登陆超时，不允许访问");
				return false;
			}
		}
		if(uri.contains("shutdown")){
			return true;
		}
		if(ContraceptiveController.initApplication){
			Thread.sleep(30*1000);
			redirectLogin(request,response);
			return false;
		}
		return true;
	}
	
	private void redirectLogin(HttpServletRequest request,HttpServletResponse response){
		java.io.PrintWriter out;
		try {
			out = response.getWriter();
			out.println("<script type='text/javascript'>");  
			out.println("parent.location.href='"+request.getContextPath()+loginUri+"'");  
			out.println("</script>");  
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	   
	}

	
	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex)throws Exception {
		if(SecurityContext.getCurrentManager()!= null){
			request.getSession().setAttribute("session_manager", SecurityContext.getCurrentManager());
		}
		String uri = ServletUtil.getURI(request);
		if(uri.equals(loginHandleUri)){
			if(loginMethod!=null){
				if(request.getMethod().equalsIgnoreCase(loginMethod)){
					if(SecurityContext.getCurrentManager()!= null){
						logUtil.wirte(request, LogType.LoginSuccess,"用户登录",SecurityContext.getCurrentManager().getUsername()+" 登录成功");
					}else{
						logUtil.wirte(request, LogType.LoginFailure,"用户登录失败",request.getParameter("username")+" 用户登录失败",request.getParameter("username"));
					}
				}
			}else{
				if(SecurityContext.getCurrentManager()!= null){
					logUtil.wirte(request, LogType.LoginSuccess,"用户登录",SecurityContext.getCurrentManager().getUsername()+"登录成功");
				}else{
					logUtil.wirte(request, LogType.LoginFailure,"用户登录失败",request.getParameter("username")+"用户登录失败");
				}
			}
		}else if(uri.equals(logoutUri)){
			HttpSession session = request.getSession();
			session.setAttribute("session_manager", null);
			session.invalidate();
			logUtil.wirte(request, LogType.LogoutOperation,"用户登出","用户登出，登出的用户为："+(SecurityContext.getCurrentManager()==null?"":SecurityContext.getCurrentManager().getUsername()));
		}
		
		if(ex != null){
			logUtil.wirte(request, LogType.ExceptionOperation,"操作异常",Arrays.toString(ex.getStackTrace()));
		}
		SecurityContext.crear();
	}

	public List<String> getNeedCheckUri() {
		return needCheckUri;
	}

	public String getLoginUri() {
		return loginUri;
	}

	public void setLoginUri(String loginUri) {
		this.loginUri = loginUri;
	}

	public void setNeedCheckUri(List<String> needCheckUri) {
		this.needCheckUri = needCheckUri;
	}

	public String getLogoutUri() {
		return logoutUri;
	}
	
	public String getLoginHandleUri() {
		return loginHandleUri;
	}


	public void setLoginHandleUri(String loginHandleUri) {
		this.loginHandleUri = loginHandleUri;
	}


	public void setLogoutUri(String logoutUri) {
		this.logoutUri = logoutUri;
	}

	public LogUtil getLogUtil() {
		return logUtil;
	}

	public void setLogUtil(LogUtil logUtil) {
		this.logUtil = logUtil;
	}


	public String getLoginSuccessUri() {
		return loginSuccessUri;
	}


	public String getLoginMethod() {
		return loginMethod;
	}


	public void setLoginMethod(String loginMethod) {
		this.loginMethod = loginMethod;
	}


	public void setLoginSuccessUri(String loginSuccessUri) {
		this.loginSuccessUri = loginSuccessUri;
	}

}
