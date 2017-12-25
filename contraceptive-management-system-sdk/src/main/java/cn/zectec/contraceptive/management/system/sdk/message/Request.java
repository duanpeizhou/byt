package cn.zectec.contraceptive.management.system.sdk.message;

import org.apache.log4j.Logger;

import cn.zectec.contraceptive.management.system.sdk.codec.CRC16M;
import cn.zectec.contraceptive.management.system.utils.ByteUtil;

public  class Request{
	private RequestMessage message;
	public static byte BEGIN = (byte)0xaa;
	public static byte END = (byte)0xcc;
	private static Logger logger = Logger.getLogger(Request.class);
	

	public RequestMessage getMessage() {
		return message;
	}
	public void setMessage(RequestMessage message) {
		this.message = message;
	}
	
	public  boolean parse(byte[] data){
		String outputHexofByte = ByteUtil.outputHexofByte(data);
		if(data[0] != BEGIN || data[data.length-1] != END || data.length <= 8){
			logger.debug("开始结束不正确或长度不正确 "+outputHexofByte);
			return false;
		}
		
		int crc = CRC16M.crc(data, 3, data.length-3);
		byte b1 = (byte)(crc>>8);
		byte b2 = (byte)(crc&0xff);
		if(b1 != data[data.length-3] || b2 != data[data.length-2]){
			System.out.println("=====");
			//logger.debug("crc校验不正确 " +outputHexofByte);
//			if(outputHexofByte.contains("ff ff ff ff ff ff ff ff ff ff ff ff ff ff ff ff ff ff ff ff ff ff ff"))return false;
			//return false;
		}
		try {
			message.parse(data,3,data.length-6);
		} catch (Exception e) {}
		return true;
	}
	@Override
	public String toString() {
		return "Request [message=" + message + "]";
	}
	
	
}
