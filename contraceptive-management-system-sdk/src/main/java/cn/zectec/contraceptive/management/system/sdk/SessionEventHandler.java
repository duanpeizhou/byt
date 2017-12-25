package cn.zectec.contraceptive.management.system.sdk;

public interface SessionEventHandler {
	public static enum Event{
		Connect,
		DisConnect
	} 
	
	public void handleEvent(Context context,Event event);
}
