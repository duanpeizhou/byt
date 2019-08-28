package cn.zectec.contraceptive.management.system.sdk.message;

import cn.zectec.contraceptive.management.system.utils.ByteUtil;

public class OfflineGetResponseMessage extends ResponseMessage{
	private long billNumber;

	@Override
	public byte[] bytes() {
		int i = 0;
		byte[] result = new byte[10];

		result[i++] = this.type;
		result[i++] = this.ownType;

		long t = terminalNo;
		for(int j = 0; j < 3; j ++)
		{
			long l = t % 100;
			result[i+2-j] = (byte) (((l/10)<<4) + l%10);
			t /= 100;
		}
		i += 3;

		t = billNumber;
		for(int j = 0; j < 5; j ++)
		{
			long l = t % 100;
			result[i+4-j] = (byte) (((l/10)<<4) + l%10);
			t /= 100;
		}
		i += 5;

		return result;
	}


	public long getBillNumber() {
		return billNumber;
	}


	public void setBillNumber(long billNumber) {
		this.billNumber = billNumber;
	}


	public static void main(String[] args) {
		OfflineGetResponseMessage msg = new OfflineGetResponseMessage();
		msg.setBillNumber(1006025003);
		msg.setTerminalNo(150158);
		System.out.println(ByteUtil.outputHexofByte(msg.bytes()));
		//bb 00 08 00 01 11 00 82 06 30 10 27 42 be 71 cc
		//		   00 01 00 00 00 06 30 10 27 42
		//630102742
		//1006025003
//		//613203304
//		aa 01 17 02 01 11 00 82 02 00 20 af 51 6f 66 3a 5f 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 31 00 30 00 31 00 31 00 39 00 36 00 34 00 31 00 30 00 31 00 35 00 57 53 81 5b 02 5e 74 51 81 5b 3a 53 cb 53 31 72 57 53 ef 8d 31 00 33 00 f7 53 c6 96 53 4f 37 62 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 34 00 35 00 30 00 31 00 30 00 32 00 31 00 39 00 36 00 34 00 31 00 30 00 31 00 35 00 30 00 35 00 31 00 31 00 57 53 81 5b 02 5e 6c 51 89 5b 40 5c 74 51 81 5b 06 52 40 5c 20 00 20 00 20 00 20 00 20 00 32 00 30 00 31 00 33 00 30 00 35 00 32 00 34 00 7f 95 1f 67 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 20 00 06 30 10 27 42 00 00 00 00 00 00 00 00 00 00 00 00 9a 49 cc
		Response r = new Response();
		r.setMessage(msg);
		System.out.println(ByteUtil.outputHexofByte(r.bytes()));
	}

}
