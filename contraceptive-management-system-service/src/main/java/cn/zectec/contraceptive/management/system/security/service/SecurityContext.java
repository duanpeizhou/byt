package cn.zectec.contraceptive.management.system.security.service;

import java.util.HashMap;
import java.util.Map;

import cn.zectec.contraceptive.management.system.model.Manager;

public class SecurityContext {
	public static String IP = "SecurityContext_IP";
	
	private static ThreadLocal<Manager> manager = new ThreadLocal<Manager>();
	private static ThreadLocal<Map<String, Object>> attrs = new ThreadLocal<Map<String, Object>>();
	
	public static void registerManager(Manager manager_){
		manager.set(manager_);
	}
	public static Manager getCurrentManager(){
		return manager.get();
	}
	
	public static void attr(String name,Object value){
		if(attrs.get()==null){
			attrs.set(new HashMap<String, Object>());
		}
		attrs.get().put(name, value);
	}
	
	public static Object attr(String name){
		if(attrs.get()!=null){
			return attrs.get().get(name);
		}else{
			return null;
		}
	}
	
	public static void crear(){
		manager.set(null);
		attrs.set(null);
	}
	
	public static boolean hasPermission(Object object){
		return true;
	}
}
