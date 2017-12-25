package cn.zectec.contraceptive.management.system.sdk.netty.impl;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import org.apache.log4j.Logger;


public class HeartBeatHandler extends ChannelDuplexHandler{
	private static Logger logger = Logger.getLogger(HeartBeatHandler.class);
	/*private static Ack ack = new Ack();
	private int i = 0;*/
	
	@Override
	public void userEventTriggered(final ChannelHandlerContext ctx, Object evt) throws Exception {
		
		if(evt instanceof IdleStateEvent){
			IdleStateEvent ideEvent = (IdleStateEvent)evt;
			if(ideEvent.state() == IdleState.READER_IDLE){
				ctx.close();
				logger.debug("很长时间没收到数据,关闭了连接...........");
				//发送数据检测是否在连接 Ping
				/*ctx.writeAndFlush(ack).addListener(new ChannelFutureListener(){
					@Override
					public void operationComplete(ChannelFuture future)	throws Exception {
						if(!future.isSuccess()){
							i++;
						}else{
							i = 0;
						}
						logger.debug("--发送ping数据结果 --"+future.isSuccess()+";当前次数"+i);
						if(i == 3){
							ctx.fireChannelInactive();
							ctx.close();
						}
					}
					
				});*/
			}
		}
	}

}
