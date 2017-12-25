package cn.zectec.contraceptive.management.system.web.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.model.Log;
import cn.zectec.contraceptive.management.system.model.Log.LogType;
import cn.zectec.contraceptive.management.system.security.service.SecurityContext;
import cn.zectec.contraceptive.management.system.service.ILogService;
import cn.zectec.contraceptive.management.system.web.util.ServletUtil;

@Component
public class LogUtil {
	@Autowired
	private ILogService logService;
	
	public void wirte(HttpServletRequest request,LogType logType, String title,String message){
		Log log = new Log();
		log.setIp(ServletUtil.getRealIp(request));
		log.setTitle(title);
		log.setUri(ServletUtil.getURI(request));
		log.setOperationDate(new Date());
		log.setTitleDescription(message);
		log.setLogType(logType);
		log.setOperationDate(new Date());
		if(SecurityContext.getCurrentManager() != null){
			log.setUsername(SecurityContext.getCurrentManager().getUsername());
			log.setManagerID(SecurityContext.getCurrentManager().getId());
		}
		logService.addLog(log);
	}
	
	public void wirte(HttpServletRequest request,LogType logType, String title,String message,String username){
		Log log = new Log();
		log.setIp(ServletUtil.getRealIp(request));
		log.setTitle(title);
		log.setUri(ServletUtil.getURI(request));
		log.setOperationDate(new Date());
		log.setTitleDescription(message);
		log.setLogType(logType);
		log.setOperationDate(new Date());
		log.setUsername(username);
		logService.addLog(log);
	}
}
