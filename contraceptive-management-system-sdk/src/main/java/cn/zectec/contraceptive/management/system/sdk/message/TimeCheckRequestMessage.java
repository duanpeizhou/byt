package cn.zectec.contraceptive.management.system.sdk.message;

import cn.zectec.contraceptive.management.system.utils.ByteUtil;



public class TimeCheckRequestMessage extends RequestMessage{
	
	@Override
	public void parse(byte[] data) {
		parse(data,0,data.length);
	}

	@Override
	public void parse(byte[] data, int offset, int length) {
		if(data[offset++]==0x04 && data[offset++] == 0x01 && length == 17){
			this.type = 0x04;
			this.ownType = 0x01;
			terminalNo = 0;
			for (int i = 0; i < 3; i++) {
				terminalNo *= 100;
				terminalNo += ((data[offset] >> 4) & 0x0f) * 10 + (data[offset++] & 0x0f);
			}
			unknow = new byte[8];
			for(int i=0;i<8;i++){
				unknow[i] = data[offset++];
			}
			hardNo = 0;
			for (int i = 0; i <= 3; i++) {
				hardNo *= 100;
				hardNo += ((data[offset] >> 4) & 0x0f) * 10 + (data[offset++] & 0x0f);
			}
		}else{
			throw new RuntimeException("数据不正常.....");
		}
	}

	@Override
	public String toString() {
		return "TimeCheckRequestMessage [unknow=" + ByteUtil.outputHexofByte(unknow)
				+ ", hardNo=" + hardNo + ", type=" + type + ", ownType="
				+ ownType + ", terminalNo=" + terminalNo + "]";
	}

}
