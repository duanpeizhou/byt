package cn.zectec.contraceptive.management.system.sdk.message;

import java.util.Arrays;

import cn.zectec.contraceptive.management.system.utils.ByteUtil;

public class StatusReportRequestMessage extends RequestMessage{
	private int signalStrength;
	private int temporature;
	private int cargoLeft[];
	private byte faultNo;
	private boolean[] ailseFailure;
	private boolean cardReaderFailure;
	private boolean doorstate;
	private byte version;
	
	
	@Override
	public void parse(byte[] data) {
		parse(data,0,data.length);
	}
	//[aa 00 18 03 01 15 00 31 01 1e 28 27 28 27 ff 03 00 00 00 00 00 00 00 00 00 00 00 00 00 ee be cc

	@Override
	public void parse(byte[] data, int offset, int length) {
		if(data[offset++]==0x03 && data[offset++]==0x01){
			this.type = 0x03;
			this.ownType = 0x01;
			terminalNo = 0;
			for (int i = 0; i < 3; i++) {
				terminalNo *= 100;
				terminalNo += ((data[offset] >> 4) & 0x0f) * 10 + (data[offset++] & 0x0f);
			}
			signalStrength = data[offset++];
			temporature = data[offset++];
			cargoLeft = new int[4];
			for(int i = 0; i < 4; i ++)
			{
				cargoLeft[i] = ((data[offset] >> 4) & 0x0f) * 10 + (data[offset++]&0x0f);
			}
			faultNo = data[offset++];
			doorstate = data[offset++]==0x01;
			version = data[offset++];
			unknow = new byte[8];
			for(int i=0;i<8;i++){
				unknow[i] = data[offset++];
			}
			hardNo = 0;
			for (int i = 0; i <= 3; i++) {
				hardNo *= 100;
				hardNo += ((data[offset] >> 4) & 0x0f) * 10 + (data[offset++] & 0x0f);
			}
			ailseFailure = new boolean[4];
			ailseFailure[3] = ((faultNo >> 0) & 0x1) == 0;
			ailseFailure[2] = ((faultNo >> 1) & 0x1) == 0;
			ailseFailure[1] = ((faultNo >> 2) & 0x1) == 0;
			ailseFailure[0] = ((faultNo >> 3) & 0x1) == 0;
			cardReaderFailure = ((faultNo >> 7) & 0x1) == 0;
		}else{
			throw new RuntimeException("数据不正常.....");
		}
	}

	public int getSignalStrength() {
		return signalStrength;
	}

	public void setSignalStrength(int signalStrength) {
		this.signalStrength = signalStrength;
	}

	public int getTemporature() {
		return temporature;
	}

	public void setTemporature(int temporature) {
		this.temporature = temporature;
	}

	public int[] getCargoLeft() {
		return cargoLeft;
	}

	public void setCargoLeft(int[] cargoLeft) {
		this.cargoLeft = cargoLeft;
	}

	public byte getFaultNo() {
		return faultNo;
	}

	public void setFaultNo(byte faultNo) {
		this.faultNo = faultNo;
	}

	public boolean[] getAilseFailure() {
		return ailseFailure;
	}

	public void setAilseFailure(boolean[] ailseFailure) {
		this.ailseFailure = ailseFailure;
	}

	public boolean isDoorstate() {
		return doorstate;
	}

	public boolean isCardReaderFailure() {
		return cardReaderFailure;
	}

	public void setCardReaderFailure(boolean cardReaderFailure) {
		this.cardReaderFailure = cardReaderFailure;
	}

	public void setDoorstate(boolean doorstate) {
		this.doorstate = doorstate;
	}

	public byte getVersion() {
		return version;
	}

	public void setVersion(byte version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "StatusReportRequestMessage [signalStrength=" + signalStrength
				+ ", temporature=" + temporature + ", cargoLeft="
				+ Arrays.toString(cargoLeft) + ", faultNo=" + faultNo
				+ ", ailseFailure=" + Arrays.toString(ailseFailure)
				+ ", cardReaderFailure=" + cardReaderFailure + ", doorstate="
				+ doorstate + ", version=" + version + ", unknow="
				+ ByteUtil.outputHexofByte(unknow) + ", hardNo=" + hardNo + ", type="
				+ type + ", ownType=" + ownType + ", terminalNo=" + terminalNo
				+ "]";
	}
	public static void main(String[] args) {
		StatusReportRequestMessage message  = new StatusReportRequestMessage();
		byte[] data = new byte[]{ 0x03 , 0x01 , 0x15 , 0x00 , 0x31 , 0x01 , 0x1e , 0x28 , 0x27 , 0x28 , 0x27 , (byte) 0xff , 0x01 , 0x00 , 0x00 , 0x00 , 0x00 , 0x00 , 0x00 , 0x00 , 0x00 , 0x00 , 0x00 , 0x00 , 0x00 , 0x00, (byte) 0xee, (byte) 0xbe, (byte) 0xcc};
		message.parse(data );
		System.out.println(message);
	}

}
