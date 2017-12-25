package cn.zectec.contraceptive.management.system.sdk.message;


public class OnlineGetResponseMessage extends ResponseMessage{
	private boolean enable;
	private int billNumber;
	
	public OnlineGetResponseMessage(){
		this.type = 0x01;
		this.ownType = 0x01;
		
	}
	
	@Override
	public byte[] bytes() {
		int i = 0;
		byte[] result = new byte[11];
		
		result[i++] = this.type;
		result[i++] = this.ownType;
		
		int t = terminalNo;
		for(int j = 0; j < 3; j ++)
		{
			int l = t % 100;
			result[i+2-j] = (byte) (((l/10)<<4) + l%10);
			t /= 100;
		}
		i += 3;
		
		t = billNumber;
		for(int j = 0; j < 5; j ++)
		{
			int l = t % 100;
			result[i+4-j] = (byte) (((l/10)<<4) + l%10);
			t /= 100;
		}
		i += 5;
		
		if(enable){
			result[i++] = 0x00;
		}else{
			result[i++] = 0x01;
		}
		return result;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public int getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(int billNumber) {
		this.billNumber = billNumber;
	}
}
