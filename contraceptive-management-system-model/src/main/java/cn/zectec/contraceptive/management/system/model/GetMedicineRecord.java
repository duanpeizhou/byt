package cn.zectec.contraceptive.management.system.model;

import java.util.Date;

@SuppressWarnings("serial")
public class GetMedicineRecord extends Base{
	private Long billNumber;
	/**
	 * 领用时间
	 */
	private Date getMedicineDate;
	/**
	 * 添加时间
	 */
	private Date addDate = new Date();
	/**
	 * 领取时设备的状态
	 */
	private boolean currentConnectionState;
	/**
	 * 身份证号
	 */
	private String idNumber;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 性别
	 */
	private String sex;
	private Nation nation;
	/**
	 * 户籍
	 */
	private String householdRegistration;
	private String stationName;
	private String address;
	/**
	 * 年龄
	 */
	private int age;
	/**
	 * 领用药剂
	 */
	private Contraceptive contraceptive;
	/**
	 * 领用数量
	 */
	private int amount;
	/**
	 * 人员流动情况
	 */
	private String turnoverSituation;
	/**
	 * 发送情况
	 */
	private MachineryEquipment machineryEquipment;
	/**
	 * 货道编号
	 */
	private String cargoRoadNo;
	/**
	 * 货物id
	 */
	private String cargoId;
	
	/**
	 * 生日
	 */
	private Date birthDay;
	/**
	 * 身份证有效期起止
	 */
	private Date beginDate;
	private Date endDate;
	
	
	private boolean sent = false;
	
	
	public String getCargoRoadNo() {
		return cargoRoadNo;
	}
	public void setCargoRoadNo(String cargoRoadNo) {
		this.cargoRoadNo = cargoRoadNo;
	}
	public String getCargoId() {
		return cargoId;
	}
	public void setCargoId(String cargoId) {
		this.cargoId = cargoId;
	}
	public Date getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public boolean isSent() {
		return sent;
	}
	public void setSent(boolean sent) {
		this.sent = sent;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	public boolean isCurrentConnectionState() {
		return currentConnectionState;
	}
	public void setCurrentConnectionState(boolean currentConnectionState) {
		this.currentConnectionState = currentConnectionState;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public Nation getNation() {
		return nation;
	}
	public void setNation(Nation nation) {
		this.nation = nation;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getHouseholdRegistration() {
		return householdRegistration;
	}
	public void setHouseholdRegistration(String householdRegistration) {
		this.householdRegistration = householdRegistration;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Contraceptive getContraceptive() {
		return contraceptive;
	}
	public void setContraceptive(Contraceptive contraceptive) {
		this.contraceptive = contraceptive;
	}
	public int getAmount() {
		return amount;
	}
	public MachineryEquipment getMachineryEquipment() {
		return machineryEquipment;
	}
	public void setMachineryEquipment(MachineryEquipment machineryEquipment) {
		this.machineryEquipment = machineryEquipment;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getGetMedicineDate() {
		return getMedicineDate;
	}
	public void setGetMedicineDate(Date getMedicineDate) {
		this.getMedicineDate = getMedicineDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTurnoverSituation() {
		return turnoverSituation;
	}
	public void setTurnoverSituation(String turnoverSituation) {
		this.turnoverSituation = turnoverSituation;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public Long getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(Long billNumber) {
		this.billNumber = billNumber;
	}

}
