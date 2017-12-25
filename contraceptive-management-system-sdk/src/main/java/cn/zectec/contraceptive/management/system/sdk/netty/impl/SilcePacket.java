package cn.zectec.contraceptive.management.system.sdk.netty.impl;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class SilcePacket extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,List<Object> out) throws Exception {
		Object decoded = decode(ctx, in);
		if (decoded != null) {
			out.add(decoded);
		}
	}

	private Object decode(ChannelHandlerContext ctx, ByteBuf in) {
		//byte index = 
		while(in.readableBytes() > 0){
			
		}
		return null;
	}

}
