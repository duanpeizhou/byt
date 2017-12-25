package cn.zectec.contraceptive.management.system.sdk.netty.impl;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import org.apache.log4j.Logger;

import cn.zectec.contraceptive.management.system.sdk.codec.MessageCodec;
import cn.zectec.contraceptive.management.system.sdk.message.Response;
import cn.zectec.contraceptive.management.system.utils.ByteUtil;

@Sharable
public class ResponseEncode extends MessageToMessageEncoder<Response>{
	private MessageCodec codec = new MessageCodec();
	private static Logger logger = Logger.getLogger(ResponseEncode.class);

	@Override
	public void encode(ChannelHandlerContext ctx, Response msg,List<Object> out) throws Exception {
		byte[] data = codec.encode(msg);
		if(data != null){
			logger.info("发送的数据为 "+ByteUtil.outputHexofByte(data));
			out.add(data);
		}
	}

}
