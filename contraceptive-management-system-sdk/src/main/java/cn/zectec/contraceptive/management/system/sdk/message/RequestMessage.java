package cn.zectec.contraceptive.management.system.sdk.message;

public abstract class RequestMessage extends Message{
	protected byte[] unknow;
	protected int hardNo;
	public abstract void parse(byte[] data);
	public abstract void parse(byte[] data,int offset,int length);
	public byte[] getUnknow() {
		return unknow;
	}
	public void setUnknow(byte[] unknow) {
		this.unknow = unknow;
	}
	public int getHardNo() {
		return hardNo;
	}
	public void setHardNo(int hardNo) {
		this.hardNo = hardNo;
	}
	
	
}
