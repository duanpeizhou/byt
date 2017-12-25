package cn.zectec.contraceptive.management.system.web.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.Manager;

public class AreaOptionsInterceptor implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest a, ServletResponse b,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)a;
		HttpServletResponse response=(HttpServletResponse)b;
		
		Manager m=(Manager)request.getSession().getAttribute("session_manager");
		if(m!=null){
			Area country=m.getCounty();
			Area town=m.getTownshipStreet();
			String uri=request.getRequestURI();
			String u=uri.substring(uri.lastIndexOf("/"), uri.length());
			//System.out.println(u+"  ||||||||||-------===========");
			if(town!=null){
				//System.out.println(country.getId()+";;;;;;;;;;"+town.getId());
				if(u.equals("/townshipStreetMenu")){
					request.getRequestDispatcher("getAreaById?id="+town.getId()).forward(request, response);
				}else if(u.equals("/countryMenuOptions")){
					request.getRequestDispatcher("getAreaById?id="+country.getId()).forward(request, response);
				}else{
					chain.doFilter(request, response);
				}
				
			}else if(country!=null){
				if(u.equals("/countryMenuOptions")){
					request.getRequestDispatcher("getAreaById?id="+country.getId()).forward(request, response);
				}else{
					chain.doFilter(request, response);
				}
			}else {
				chain.doFilter(request, response);
			}
		}else{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		
	}

}
