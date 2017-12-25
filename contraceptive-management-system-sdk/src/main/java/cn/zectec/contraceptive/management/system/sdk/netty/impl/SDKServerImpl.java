package cn.zectec.contraceptive.management.system.sdk.netty.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.traffic.ChannelTrafficShapingHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import cn.zectec.contraceptive.management.system.sdk.MessageReceivedHandler;
import cn.zectec.contraceptive.management.system.sdk.SDKServer;
import cn.zectec.contraceptive.management.system.sdk.SessionEventHandler;

public class SDKServerImpl implements SDKServer {
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private ServerBootstrap bootstrap;
	private int port = 1084;
	private List<MessageReceivedHandler> handlers = new ArrayList<MessageReceivedHandler>();
	private List<SessionEventHandler> eventHandlers = new ArrayList<SessionEventHandler>();
	private int heartBeatSecond = 2*60;
	private static Logger logger = Logger.getLogger(SDKServerImpl.class);

	@Override
	public void init() {
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();
		bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>(){

			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline cp = ch.pipeline();
				cp.addLast(new ChannelTrafficShapingHandler(256,256));
				cp.addLast(new IdleStateHandler(heartBeatSecond,heartBeatSecond,0,TimeUnit.SECONDS));
				cp.addLast(new HeartBeatHandler());
				cp.addLast(new ByteArrayDelimiterDecode(500));
				cp.addLast(new ByteArrayEncoder());
				cp.addLast(new ResponseEncode());
				cp.addLast(new RequestDecoder());
				cp.addLast(new ChannelHandler(SDKServerImpl.this));
			}

		});
	}

	@Override
	public void run() {
		try {
			Channel channel = bootstrap.bind(port).sync().channel();
			logger.info("服务器启动成功--端口号为："+port);
			channel.closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	@Override
	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public void registerHandler(MessageReceivedHandler messageReceivedHandler) {
		if(handlers.contains(messageReceivedHandler)){
			return;
		}
		handlers.add(messageReceivedHandler);
	}

	@Override
	public void removeHandler(MessageReceivedHandler messageReceivedHandler) {
		if(handlers.contains(messageReceivedHandler)){
			handlers.remove(messageReceivedHandler);
		}
	}

	public List<MessageReceivedHandler> getHandlers() {
		return handlers;
	}

	public void setHandlers(List<MessageReceivedHandler> handlers) {
		this.handlers = handlers;
	}

	@Override
	public void registerEventHandler(SessionEventHandler eventHandler) {
		if(eventHandlers.contains(eventHandler)){
			return;
		}
		eventHandlers.add(eventHandler);
	}

	@Override
	public void removeregisterHandler(SessionEventHandler eventHandler) {
		if(eventHandlers.contains(eventHandler)){
			eventHandlers.remove(eventHandler);
		}
		
	}

	public List<SessionEventHandler> getEventHandlers() {
		return eventHandlers;
	}

	public void setEventHandlers(List<SessionEventHandler> eventHandlers) {
		this.eventHandlers = eventHandlers;
	}

	public int getHeartBeatSecond() {
		return heartBeatSecond;
	}

	public void setHeartBeatSecond(int heartBeatSecond) {
		this.heartBeatSecond = heartBeatSecond;
	}



	

}
