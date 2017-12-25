package cn.zectec.contraceptive.management.system.sdk.message;

public abstract class Message {
	protected byte type;
	protected byte ownType = 0x01;
	protected int terminalNo;

	public byte getType() {
		return type;
	}	
	public void setType(byte type) {
		this.type = type;
	}

	public int getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(int terminalNo) {
		this.terminalNo = terminalNo;
	}

	public byte getOwnType() {
		return ownType;
	}

	public void setOwnType(byte ownType) {
		this.ownType = ownType;
	}
	
}
