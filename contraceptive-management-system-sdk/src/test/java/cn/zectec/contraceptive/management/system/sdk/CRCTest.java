package cn.zectec.contraceptive.management.system.sdk;

import cn.zectec.contraceptive.management.system.sdk.codec.CRC16M;
import cn.zectec.contraceptive.management.system.sdk.message.OnlineGetRequestMessage;
import cn.zectec.contraceptive.management.system.sdk.message.OnlineGetResponseMessage;
import cn.zectec.contraceptive.management.system.sdk.message.Request;
import cn.zectec.contraceptive.management.system.utils.ByteUtil;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class CRCTest {
	public static void main(String[] args) {
		byte[] bytes = new byte[] { (byte) 0xbb, (byte) 0x00, (byte) 0x22,
				(byte) 0x03, (byte) 0x02, (byte) 0x26, (byte) 0x00,
				(byte) 0x29, (byte) 0x60, (byte) 0x18, (byte) 0x20,
				(byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x7B,
				(byte) 0x7D, (byte) 0xF7, (byte) 0x7B, (byte) 0x00,
				(byte) 0x00, (byte) 0x89, (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0xaa, (byte) 0xaa, (byte) 0xaa, (byte) 0xaa,
				(byte) 0x01, (byte) 0x40, (byte) 0x15, (byte) 0xaa,
				(byte) 0xaa, (byte) 0xaa, (byte) 0xaa, (byte) 0x03,
				(byte) 0x86, (byte) 0xCA, (byte) 0xcc
		/* 7c 5b */
		};
		int v = CRC16M.crc(bytes, 3, bytes.length - 3);
		System.out.printf("B2=%02x, B1=%02x\n", (byte) (v >> 8),(byte) (v & 0xff));
		String str = "aa00180301260030051c25202045ff012f05daff333839433257065025c9eccc";

		System.out.println(str.substring(6, str.length() - 6));

		byte b = (byte)0x7f;
		boolean a = ((b >> 7) & 0x1) == 0;
		System.out.println(a);

		System.out.println(Long.toHexString(System.currentTimeMillis()));

		String s = "aa011701012600290303218b730f5c665f20002000200020002000200020002000200020002000200031003000310031003900390030003100320030003800914e575301775a69c4965d5fcf65ea81bb6cde5d5f729a5bbf5371518c5447952959f0535167d4591a4fdf98e765516736003900f753200020002000200020002000200020003500330032003300320033003100390039003000310032003000380030003300310037005f729a5bbf536c51895b405c2000200020002000200020002000200020003200300030003800300032003100350032003000310038003000320031003500200020002000200020002000200020002000200020002000200020002000200020002000060312514705d9ff36303847424315744370facc";
		byte[] b_ = new byte[s.length()/2];
		for(int i=0;i<b_.length;i++){
			String ss = s.substring(i*2,i*2+2);
			b_[i] = (byte)Integer.parseInt(ss, 16);
		}
		System.out.println("==="+b_.length);
		System.out.println(ByteUtil.outputHexofByte(b_));
		Request request = new Request();
		OnlineGetRequestMessage message = new OnlineGetRequestMessage();
		request.setMessage(message);
		request.parse(b_);
		System.out.println(message);

		OnlineGetResponseMessage responseMessage = new OnlineGetResponseMessage();
		responseMessage.setBillNumber(message.getBillNumber());
		responseMessage.setEnable(true);
		responseMessage.setTerminalNo(message.getTerminalNo());
		byte[] bytes1 = responseMessage.bytes();

		System.out.println(Hex.encodeHexString(bytes1));
	}
}
