
package cn.zectec.contraceptive.management.system.sdk.netty.impl;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import cn.zectec.contraceptive.management.system.sdk.SDKServer;
import cn.zectec.contraceptive.management.system.sdk.Session;
import cn.zectec.contraceptive.management.system.sdk.SessionEventHandler;
import cn.zectec.contraceptive.management.system.sdk.message.Request;

@Sharable
public class ChannelHandler extends SimpleChannelInboundHandler<Request>{
	private SDKServer server;
	private Map<Channel, Session> sessionMap = Collections.synchronizedMap(new HashMap<Channel, Session>());
	private Map<Long, Session> sessionIDMap = Collections.synchronizedMap(new HashMap<Long, Session>());
	private Random random = new Random();
	private static Logger logger = Logger.getLogger(ChannelHandler.class);
	
	public ChannelHandler(SDKServer server){
		this.server = server;
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Request request)throws Exception {
		//logger.debug("--cahnnel--"+ctx.channel()+"--收到数据--"+request);
		request.getMessage().getTerminalNo();
		Session session = sessionMap.get(ctx.channel());
		if(session == null){
			logger.error("在sessionMap中没有找到channel:"+ctx.channel());
			return;
		}
		SimpleContext context = new SimpleContext(((SDKServerImpl)server).getHandlers(), ctx.channel(),session);
		context.setRequest(request);
		context.doNextHandler();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("--打开连接cahnnel--"+ctx.channel());
		fireSessionEvent(ctx,SessionEventHandler.Event.Connect);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info("--关闭连接cahnnel--"+ctx.channel());
		fireSessionEvent(ctx,SessionEventHandler.Event.DisConnect);
	}
	
	private void fireSessionEvent(ChannelHandlerContext ctx,SessionEventHandler.Event event){
		Session session = null;
		
		if(event == SessionEventHandler.Event.Connect){
			SimpleSession session_ = new SimpleSession(getNextSessionId());
			sessionIDMap.put(session_.sessionId(), session);
			sessionMap.put(ctx.channel(), session_);
			session_.setRemoteIP(getRemoteIP(ctx));
			session = session_;
			
		}else if(event == SessionEventHandler.Event.DisConnect){
			session = sessionMap.get(ctx.channel());
			if(session == null){
				return;
			}
			sessionIDMap.remove(session.sessionId());
			sessionMap.remove(session);
		}
		//logger.debug("session2Map ====== "+ sessionMap);
		SDKServerImpl s = (SDKServerImpl)server;
		SimpleContext context = new SimpleContext(((SDKServerImpl)server).getHandlers(), ctx.channel(),session);
		for(SessionEventHandler sessionEventHandler : s.getEventHandlers()){
			sessionEventHandler.handleEvent(context, event);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)throws Exception {
		/*logger.debug(".....错误...."+Arrays.toString(cause.getStackTrace()));
		ctx.close();*/
	}
	
	private String getRemoteIP(ChannelHandlerContext ctx){
		String str = ctx.channel().remoteAddress().toString();
		String ip = str.substring(0, str.indexOf(':'));
		return ip;
	}
	
	private synchronized long getNextSessionId(){
		long id = 0;
		do{
			id = random.nextLong();
		}while(sessionIDMap.containsKey(id));
		return id;
	}

}
