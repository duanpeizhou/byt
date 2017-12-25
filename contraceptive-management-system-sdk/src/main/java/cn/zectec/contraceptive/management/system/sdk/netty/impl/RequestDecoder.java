package cn.zectec.contraceptive.management.system.sdk.netty.impl;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import org.apache.log4j.Logger;

import cn.zectec.contraceptive.management.system.sdk.codec.MessageCodec;
import cn.zectec.contraceptive.management.system.sdk.message.Request;

@Sharable
public class RequestDecoder extends MessageToMessageDecoder<byte[]>{
	private MessageCodec codec = new MessageCodec();
	private static Logger logger = Logger.getLogger(RequestDecoder.class);
	
	@Override
	public void decode(ChannelHandlerContext ctx, byte[] msg, List<Object> out) throws Exception {
		Request request = codec.decode(msg);
		if(request != null){
			logger.debug("收到的数据为  "+request);
			out.add(request);
		}
	}
	
}
