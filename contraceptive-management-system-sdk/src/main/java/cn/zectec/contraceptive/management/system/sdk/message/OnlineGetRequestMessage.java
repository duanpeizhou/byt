package cn.zectec.contraceptive.management.system.sdk.message;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import cn.zectec.contraceptive.management.system.utils.ByteUtil;

public class OnlineGetRequestMessage extends RequestMessage{
	private byte cargoRoadNo;
	private byte cargoId;
	private int cargoLeft;
	private String name;
	private byte gender;
	private byte nation;
	private Date birthDay;
	private String address;
	private String IDCardNo;
	private String stationName; 
	private Date beginDate;
	private Date endDate;
	private int billNumber;
	private Date tradeDate;
	private int age;

	@Override
	public void parse(byte[] data) {
		parse(data,0,data.length);
	}

	@Override
	public void parse(byte[] data, int offset, int length) {
		if(data[offset++]==0x01 && data[offset++]==0x01){
			this.type = 0x01;
			this.ownType = 0x01;
			terminalNo = 0;
			for (int i = 0; i < 3; i++) {
				terminalNo *= 100;
				terminalNo += ((data[offset] >> 4) & 0x0f) * 10 + (data[offset++] & 0x0f);
			}
			
			cargoRoadNo = data[offset++];
			cargoId = data[offset++];
			cargoLeft = data[offset++];
			
			
			try
			{
				String s = new String(data, offset, 256, "UTF-16LE").replaceAll("\\s+", " ");
				String ss[] = s.split(" ");
				name = ss[0];
				gender = Byte.parseByte(ss[1].substring(0, 1));
				nation = Byte.parseByte(ss[1].substring(1, 3));

				int _year = Integer.parseInt(ss[1].substring(3, 7));
				int _month = Integer.parseInt(ss[1].substring(7, 9))-1;
				int _date = Integer.parseInt(ss[1].substring(9, 11));
				Calendar cld = Calendar.getInstance();
				cld.set(_year, _month, _date);
				birthDay = cld.getTime();
				
				address = ss[1].substring(11);
				
				IDCardNo = ss[2].substring(0, 18);
				
				stationName = ss[2].substring(18);
				
				_year = Integer.parseInt(ss[3].substring(0, 4));
				_month = Integer.parseInt(ss[3].substring(4, 6))-1;
				_date = Integer.parseInt(ss[3].substring(6, 8));
				cld = Calendar.getInstance();
				cld.set(_year, _month, _date);
				beginDate = cld.getTime();

				if(ss[3].length() == 16){
					_year = Integer.parseInt(ss[3].substring(8, 12));
					_month = Integer.parseInt(ss[3].substring(12, 14))-1;
					_date = Integer.parseInt(ss[3].substring(14, 16));
					cld = Calendar.getInstance();
					cld.set(_year, _month, _date);
					endDate = cld.getTime();
				}else{
					cld.set(9999, 11, 31);
					endDate = cld.getTime();
				}
				
				offset += 256;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			for(int i = 0; i < 5; i ++)
			{
				billNumber *= 100;
				billNumber += ((data[offset] >> 4) & 0x0f) * 10 + (data[offset++]&0x0f);
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
			
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(new Date());
			
			int temp = billNumber;
			calendar.set(Calendar.SECOND, temp%100);
			temp /=100;
			calendar.set(Calendar.MINUTE, temp%100);
			temp /=100;
			calendar.set(Calendar.HOUR_OF_DAY, temp%100);
			temp /=100;
			calendar.set(Calendar.DAY_OF_MONTH, temp%100);
			temp /=100;
			calendar.set(Calendar.MONTH, temp%100-1);
			if(calendar.getTime().getTime() > System.currentTimeMillis()){
				calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
			}
			if(calendar.getTime().getTime() > System.currentTimeMillis() || billNumber==0){
				tradeDate = new Date();
			}else{
				tradeDate = calendar.getTime();
			}
			age = parseAge(birthDay);
		}else{
			throw new RuntimeException("数据不正常.....");
		}
	}
	

	@SuppressWarnings("deprecation")
	private int parseAge(Date birthDay) {
		return new Date().getYear()-birthDay.getYear();
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public byte getCargoRoadNo() {
		return cargoRoadNo;
	}

	public void setCargoRoadNo(byte cargoRoadNo) {
		this.cargoRoadNo = cargoRoadNo;
	}

	public byte getCargoId() {
		return cargoId;
	}

	public void setCargoId(byte cargoId) {
		this.cargoId = cargoId;
	}

	public int getCargoLeft() {
		return cargoLeft;
	}

	public void setCargoLeft(int cargoLeft) {
		this.cargoLeft = cargoLeft;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getGender() {
		return gender;
	}

	public void setGender(byte gender) {
		this.gender = gender;
	}

	public byte getNation() {
		return nation;
	}

	public void setNation(byte nation) {
		this.nation = nation;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIDCardNo() {
		return IDCardNo;
	}

	public void setIDCardNo(String iDCardNo) {
		IDCardNo = iDCardNo;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		if(endDate == null){
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)+100);
			endDate = calendar.getTime();
		}
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(int billNumber) {
		this.billNumber = billNumber;
	}

	public Date getTradeDate() {
		return tradeDate == null ? new Date() : tradeDate ;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	@Override
	public String toString() {
		return "OnlineGetRequestMessage [cargoRoadNo=" + cargoRoadNo
				+ ", cargoId=" + cargoId + ", cargoLeft=" + cargoLeft
				+ ", name=" + name + ", gender=" + gender + ", nation="
				+ nation + ", birthDay=" + birthDay + ", address=" + address
				+ ", IDCardNo=" + IDCardNo + ", stationName=" + stationName
				+ ", beginDate=" + beginDate + ", endDate=" + endDate
				+ ", billNumber=" + billNumber + ", tradeDate=" + tradeDate
				+ ", age=" + age + ", unknow=" + ByteUtil.outputHexofByte(unknow)
				+ ", hardNo=" + hardNo + ", type=" + type + ", ownType="
				+ ownType + ", terminalNo=" + terminalNo + "]";
	}



}
