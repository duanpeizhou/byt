package cn.zectec.contraceptive.management.system.sdk;

import cn.zectec.contraceptive.management.system.sdk.message.Request;
/**
 * 
 * @author wxy
 *
 */
public interface MessageReceivedHandler {
	
	public void handle(Context context, Request request);
	
}
