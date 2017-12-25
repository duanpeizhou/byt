package cn.zectec.contraceptive.management.system.sdk.netty.impl;

import io.netty.channel.Channel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.zectec.contraceptive.management.system.sdk.Context;
import cn.zectec.contraceptive.management.system.sdk.MessageReceivedHandler;
import cn.zectec.contraceptive.management.system.sdk.Session;
import cn.zectec.contraceptive.management.system.sdk.message.Request;
import cn.zectec.contraceptive.management.system.sdk.message.Response;


public class SimpleContext implements Context{
	private Map<String,Object> map = new HashMap<String,Object>();
	private List<MessageReceivedHandler> handlers = new ArrayList<MessageReceivedHandler>();
	private int index = 0;
	private Channel channel;
	private Session session;
	public Request request;

	public SimpleContext(List<MessageReceivedHandler> handlers,Channel channel,Session session) {
		this.handlers.addAll(handlers);
		this.channel = channel;
		this.session = session;
	}

	@Override
	public Object attr(String name) {
		return map.get(name);
	}

	@Override
	public void attr(String name, Object object) {
		map.put(name, object);
	}

	private MessageReceivedHandler nextHandler() {
		if(hasNextHandler()){
			return handlers.get(index++);
		}
		return null;
	}

	private boolean hasNextHandler() {
		return index < handlers.size();
	}

	@Override
	public void doNextHandler() {
		if(hasNextHandler()){
			nextHandler().handle(this, request);
		}
	}

	@Override
	public Session session() {
		return session;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	@Override
	public Request request() {
		return request;
	}

	@Override
	public void wirte(Response response) {
		channel.writeAndFlush(response);
	}


}
