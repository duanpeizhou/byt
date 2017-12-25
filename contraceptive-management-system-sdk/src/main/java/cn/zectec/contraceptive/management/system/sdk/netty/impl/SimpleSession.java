package cn.zectec.contraceptive.management.system.sdk.netty.impl;

import java.util.HashMap;
import java.util.Map;

import cn.zectec.contraceptive.management.system.sdk.Session;

public class SimpleSession implements Session{
	private long sessionId;
	private Map<String,Object> map = new HashMap<String,Object>();
	private String remoteIP;
	

	public SimpleSession(long sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public long sessionId() {
		return sessionId;
	}

	@Override
	public Object attr(String name) {
		return map.get(name);
	}

	@Override
	public void attr(String name, Object object) {
		map.put(name, object);
	}

	@Override
	public String remoteIP() {
		return remoteIP;
	}

	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}
	
}
