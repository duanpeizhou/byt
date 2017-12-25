package cn.zectec.contraceptive.management.system.sdk.message;

import org.apache.log4j.Logger;

import cn.zectec.contraceptive.management.system.sdk.codec.CRC16M;
import cn.zectec.contraceptive.management.system.utils.ByteUtil;




public  class Response {
	private ResponseMessage message;
	public static byte BEGIN = (byte)0xbb;
	public static byte END = (byte)0xcc;
	private static Logger loger = Logger.getLogger(Response.class);

	public Message getMessage() {
		return message;
	}
	public void setMessage(ResponseMessage message) {
		this.message = message;
	}
	
	public  byte[] bytes(){
		byte[] content = message.bytes();
		byte[] result = new byte[content.length+6];
		result[0] = BEGIN;
		int length = content.length-2;
		result[2] = (byte) ((byte)length & 0x00ff);
		result[1] = (byte) (length >> 8);
		System.arraycopy(content, 0, result, 3, content.length);
		int crc = CRC16M.crc(content, 0, content.length);
		byte b1 = (byte)(crc>>8);
		byte b2 = (byte)(crc&0xff);
		result[result.length-3] = b1;
		result[result.length-2] = b2;
		result[result.length-1] = END;
		loger.debug("编码结果为::::"+ByteUtil.outputHexofByte(result));
		return result;
	}

	
}
