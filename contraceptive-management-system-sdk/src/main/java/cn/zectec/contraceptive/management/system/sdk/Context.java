package cn.zectec.contraceptive.management.system.sdk;

import cn.zectec.contraceptive.management.system.sdk.message.Request;
import cn.zectec.contraceptive.management.system.sdk.message.Response;

/**
 * 上下文
 * @author wxy
 *
 */
public interface Context {
	
	public Object attr(String name);
	
	public void attr(String name,Object object);
	
	public void doNextHandler();
	
	public void wirte(Response response);
	
	public Request request();
	
	public Session session();
}
