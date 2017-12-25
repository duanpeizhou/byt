package cn.zectec.contraceptive.management.system.sdk;

public interface Session {
	
	public long sessionId();

	public Object attr(String name);

	public void attr(String name, Object object);
	
	public String remoteIP();
}
