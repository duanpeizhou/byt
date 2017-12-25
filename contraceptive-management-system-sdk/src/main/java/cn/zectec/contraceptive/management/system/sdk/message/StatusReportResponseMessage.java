package cn.zectec.contraceptive.management.system.sdk.message;

import cn.zectec.contraceptive.management.system.utils.ByteUtil;

public class StatusReportResponseMessage extends ResponseMessage{
	private boolean setting = false;

	
	
	public StatusReportResponseMessage(){
		this.type = 0x03;
		this.ownType = 0x01;
	}
	
	@Override
	public byte[] bytes() {
		if(!setting){
			byte[] result = new byte[5];
			int i =0;
			result[i++] = this.type;
			result[i++] = 0x01;
			int t = terminalNo;
			for(int j = 0; j < 3; j ++)
			{
				int l = t % 100;
				result[i+2-j] = (byte) (((l/10)<<4) + l%10);
				t /= 100;
			}
			return result;
		}
		return null;
	}

	public boolean isSetting() {
		return setting;
	}

	public void setSetting(boolean setting) {
		if(setting){
			this.ownType = 0x02;
		}
		this.setting = setting;
	}
	
	public static void main(String[] args) {
		StatusReportResponseMessage m = new StatusReportResponseMessage();
		m.setTerminalNo(230026);
		System.out.println(ByteUtil.outputHexofByte(m.bytes()));
	}

}
