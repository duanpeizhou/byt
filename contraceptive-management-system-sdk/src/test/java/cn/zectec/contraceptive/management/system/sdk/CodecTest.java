package cn.zectec.contraceptive.management.system.sdk;

import org.apache.commons.codec.binary.Hex;

import cn.zectec.contraceptive.management.system.sdk.message.OnlineGetResponseMessage;


public class CodecTest {
	public static void main(String[] args) {
		
		OnlineGetResponseMessage msg = new OnlineGetResponseMessage();
		msg.setBillNumber(123456);
		msg.setEnable(true);
		msg.setTerminalNo(100203);
		
		byte[] s = msg.bytes();
	
		System.out.println(Hex.encodeHexString(s));
		
	}
}
