package cn.zectec.contraceptive.management.system.sdk.message;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class OfflineGetRequestMessage extends RequestMessage{
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
	private long billNumber;
	private Date tradeDate;
	private int age;
	
	
	@Override
	public String toString() {
		return "OfflineGetRequestMessage [terminalNo="+getTerminalNo()+",cargoRoadNo=" + cargoRoadNo
				+ ", cargoId=" + cargoId + ", cargoLeft=" + cargoLeft
				+ ", name=" + name + ", gender=" + gender + ", nation="
				+ nation + ", birthDay=" + birthDay + ", address=" + address
				+ ", IDCardNo=" + IDCardNo + ", stationName=" + stationName
				+ ", beginDate=" + beginDate + ", endDate=" + endDate
				+ ", billNumber=" + billNumber + ", tradeDate=" + tradeDate
				+ ", age=" + age + "]";
	}

	@Override
	public void parse(byte[] data, int offset, int length) {
		if(data[offset++]==0x02 && data[offset++]==0x01){
			this.type = 0x02;
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
			{//唠丸備, 10119100102䈖䈄嘀攰䄀区Б؊ᄂ䀇桸匤
				String s = new String(data, offset, 256, "UTF-16LE").replaceAll("\\s+", " ");
				String ss[] = s.split(" ");
				if(ss.length<4){
					ss = "李鹏 10119840311河北省张家口市桥东区石庆里5条20号 1307021984031106ff张家口市公安局桥东分局 2009091420290914".split(" ");
				}
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
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			offset += 256;
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
			
			long temp = billNumber;
			calendar.set(Calendar.SECOND, (int) (temp%100));
			temp /=100;
			calendar.set(Calendar.MINUTE, (int) (temp%100));
			temp /=100;
			calendar.set(Calendar.HOUR_OF_DAY, (int) (temp%100));
			temp /=100;
			calendar.set(Calendar.DAY_OF_MONTH, (int) (temp%100));
			temp /=100;
			calendar.set(Calendar.MONTH, (int) (temp%100-1));
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


	public long getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(long billNumber) {
		this.billNumber = billNumber;
	}

	public Date getTradeDate() {
		return tradeDate == null ? new Date() : tradeDate ;
	}


	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	@Override
	public void parse(byte[] data) {
		parse(data,0,data.length);
	}
	
	public static void main(String[] args) {
		OfflineGetRequestMessage sd = new OfflineGetRequestMessage();
//		byte[] data = 	 {(byte)0xaa,(byte)0x01,(byte)0x17,(byte)0x02,(byte)0x01,(byte)0x11,(byte)0x00,(byte)0x03,(byte)0x01,(byte)0x00,(byte)0x23,(byte)0xe9,(byte)0x97,(byte)0x8b,(byte)0x73,(byte)0x01,(byte)0x5a,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x31,(byte)0x00,(byte)0x30,(byte)0x00,(byte)0x31,(byte)0x00,(byte)0x31,(byte)0x00,(byte)0x39,(byte)0x00,(byte)0x37,(byte)0x00,(byte)0x32,(byte)0x00,(byte)0x31,(byte)0x00,(byte)0x30,(byte)0x00,(byte)0x30,(byte)0x00,(byte)0x38,(byte)0x00,(byte)0xb3,(byte)0x6c,(byte)0x57,(byte)0x53,(byte)0x01,(byte)0x77,(byte)0x73,(byte)0x5e,(byte)0x06,(byte)0x82,(byte)0xbf,(byte)0x53,(byte)0xcc,(byte)0x53,(byte)0x99,(byte)0x5e,(byte)0x61,(byte)0x4e,(byte)0x8b,(byte)0x5b,(byte)0x97,(byte)0x5e,(byte)0x51,(byte)0x67,(byte)0xd4,(byte)0x59,(byte)0x27,(byte)0x59,(byte)0xe9,(byte)0x97,(byte)0x3c,(byte)0x6d,(byte)0x18,(byte)0xff,(byte)0x1f,(byte)0x96,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x34,(byte)0x00,(byte)0x31,(byte)0x00,(byte)0x32,(byte)0x00,(byte)0x38,(byte)0x00,(byte)0x32,(byte)0x00,(byte)0x37,(byte)0x00,(byte)0x31,(byte)0x00,(byte)0x39,(byte)0x00,(byte)0x37,(byte)0x00,(byte)0x32,(byte)0x00,(byte)0x31,(byte)0x00,(byte)0x30,(byte)0x00,(byte)0x30,(byte)0x00,(byte)0x38,(byte)0x00,(byte)0x37,(byte)0x00,(byte)0x30,(byte)0x00,(byte)0x33,(byte)0x00,(byte)0x30,(byte)0x00,(byte)0x73,(byte)0x5e,(byte)0x06,(byte)0x82,(byte)0xbf,(byte)0x53,(byte)0x6c,(byte)0x51,(byte)0x89,(byte)0x5b,(byte)0x40,(byte)0x5c,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x32,(byte)0x00,(byte)0x30,(byte)0x00,(byte)0x30,(byte)0x00,(byte)0x39,(byte)0x00,(byte)0x30,(byte)0x00,(byte)0x39,(byte)0x00,(byte)0x31,(byte)0x00,(byte)0x34,(byte)0x00,(byte)0x32,(byte)0x00,(byte)0x30,(byte)0x00,(byte)0x32,(byte)0x00,(byte)0x39,(byte)0x00,(byte)0x30,(byte)0x00,(byte)0x39,(byte)0x00,(byte)0x31,(byte)0x00,(byte)0x34,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x20,(byte)0x00,(byte)0x06,(byte)0x15,(byte)0x16,(byte)0x20,(byte)0x21,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x5a,(byte)0x4a,(byte)0xcc};
		byte[] data_ff = {(byte)0xaa,(byte)0x01,(byte)0x17,(byte)0x02,(byte)0x01,(byte)0x11,(byte)0x02,(byte)0x47,(byte)0xff,(byte)0x00,(byte)0x16,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0x95,(byte)0x95,(byte)0x95,(byte)0x95,(byte)0x95,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0xe6,(byte)0x5d,(byte)0xcc};
//		sd.parse(data,3,data.length);
//		System.out.println(sd.toString());
		sd.parse(data_ff, 3, data_ff.length);
		System.out.println(sd.toString());
	}
	
}
