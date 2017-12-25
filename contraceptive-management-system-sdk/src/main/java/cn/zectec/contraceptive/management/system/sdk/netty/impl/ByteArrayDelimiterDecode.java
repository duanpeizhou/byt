package cn.zectec.contraceptive.management.system.sdk.netty.impl;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import org.apache.log4j.Logger;

import cn.zectec.contraceptive.management.system.utils.ByteUtil;

public class ByteArrayDelimiterDecode extends ByteToMessageDecoder{
	private static Logger logger = Logger.getLogger(ByteArrayDelimiterDecode.class);
	private int maxLength = 65535;
	
	

	public ByteArrayDelimiterDecode(int maxLength) {
		super();
		this.maxLength = maxLength;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,List<Object> out) throws Exception {
		byte[] data = decode(ctx, in);
		if(data != null){
			logger.info("收到的数据为..."+ByteUtil.outputHexofByte(data));
			out.add(data);
		}
	}

	private byte[] decode(ChannelHandlerContext ctx, ByteBuf in) {
		in.markReaderIndex();
		int start = in.indexOf(in.readerIndex(), in.readerIndex()+in.readableBytes(), (byte)0xaa);
		if(start == -1){
			in.clear();
			return null;
		}
		if(in.readableBytes()<(1+start)){
			in.resetReaderIndex();
			return null;
		}
		in.skipBytes(1+start);
		int length = in.readUnsignedShort();
		//这里是保证当包异常时不会导致包冲突
		if(length > maxLength){
			in.clear();
			return null;
		}
		if(in.readableBytes()<(length+5)){
			in.resetReaderIndex();
			return null;
		}
		in.skipBytes(length+4);
		byte end = in.readByte();
		if( end!= (byte)0xcc){
			in.clear();
			return null;
		}
		in.resetReaderIndex();
		ByteBuf s = in.slice(start, length+8);
		byte[] data = new byte[s.readableBytes()];
		s.readBytes(data);
		in.readerIndex(start+length+7);
		in.discardReadBytes();
		return data;
	}

}
