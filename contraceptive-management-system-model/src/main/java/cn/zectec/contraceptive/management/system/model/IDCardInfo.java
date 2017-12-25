package cn.zectec.contraceptive.management.system.model;

import java.io.Serializable;
import java.util.Date;

public class IDCardInfo implements Serializable{
	private static final long serialVersionUID = 5997857774742995128L;
	private String CardNumber;
	private String Name;
	//1男0女
	private int Gender;
	//yyyy-MM-dd
	private Date Birthday;
	//1-56
	private int Nation;
	//<96
	private String Address;
	//yyyy-MM-dd
	private Date BeginDate = new Date(System.currentTimeMillis());
	//yyyy-MM-dd
	private Date EndDate = new Date(System.currentTimeMillis());
	//发证机关,长度限制为16 个字符以内
	private String StationName;
	public String getCardNumber() {
		return CardNumber;
	}
	public void setCardNumber(String cardNumber) {
		CardNumber = cardNumber;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getGender() {
		return Gender;
	}
	public void setGender(int gender) {
		Gender = gender;
	}
	public Date getBirthday() {
		return Birthday;
	}
	public void setBirthday(Date birthday) {
		Birthday = birthday;
	}
	public int getNation() {
		return Nation;
	}
	public void setNation(int nation) {
		Nation = nation;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public Date getBeginDate() {
		return BeginDate;
	}
	public void setBeginDate(Date beginDate) {
		BeginDate = beginDate;
	}
	public Date getEndDate() {
		return EndDate;
	}
	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}
	public String getStationName() {
		return StationName;
	}
	public void setStationName(String stationName) {
		StationName = stationName;
	}
	public IDCardInfo() {
		super();
	}
	public IDCardInfo(String cardNumber, String name, int gender,
			Date birthday, int nation, String address, Date beginDate,
			Date endDate, String stationName) {
		super();
		CardNumber = cardNumber;
		Name = name;
		Gender = gender;
		Birthday = birthday;
		Nation = nation;
		Address = address;
		BeginDate = beginDate;
		EndDate = endDate;
		StationName = stationName;
	}
	@Override
	public String toString() {
		return "IDCardInfo [CardNumber=" + CardNumber + ", Name=" + Name
				+ ", Gender=" + Gender + ", Birthday=" + Birthday + ", Nation="
				+ Nation + ", Address=" + Address + ", BeginDate=" + BeginDate
				+ ", EndDate=" + EndDate + ", StationName=" + StationName + "]";
	}
}
