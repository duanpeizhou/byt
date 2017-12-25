package cn.zectec.contraceptive.management.system.sdk;

public interface SDKServer {
	/**
	 * 初始化
	 */
	public void init();

	/**
	 * 运行
	 */
	public void run();

	public void setPort(int port);

	public void registerHandler(MessageReceivedHandler messageReceivedHandler);

	public void removeHandler(MessageReceivedHandler messageReceivedHandler);

	public void registerEventHandler(SessionEventHandler eventHandler);

	public void removeregisterHandler(SessionEventHandler eventHandler);

}
