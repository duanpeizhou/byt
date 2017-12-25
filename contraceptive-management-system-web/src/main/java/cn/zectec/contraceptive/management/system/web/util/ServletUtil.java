package cn.zectec.contraceptive.management.system.web.util;

import javax.servlet.http.HttpServletRequest;

public class ServletUtil {
	public static String getURI(HttpServletRequest httpRequest) {
		String uri = httpRequest.getRequestURI();
		String path = httpRequest.getContextPath();
		if (path != null && path.length() != 0) {
			uri = uri.substring(uri.indexOf(path) + path.length());
		}
		return uri;
	}

	public static String getRealIp(HttpServletRequest httpRequest) {
		String ip = httpRequest.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = httpRequest.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = httpRequest.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = httpRequest.getRemoteAddr();
		}
		return ip;

	}
}
