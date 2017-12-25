package cn.zectec.contraceptive.management.system.model;

import java.util.Date;
import java.util.List;

/**
 * 发放设备
 * @author wxy 
 */
@SuppressWarnings("serial")
public class MachineryEquipment extends Base{
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 * 设备编号
	 */
	private long deviceNo;
	
	private String no;
	/**
	 * 纬度
	 */
	private String latitude;
	/**
	 * 设备类型
	 */
	private   String terminalType;
	/**
	 * 设备别名
	 */
	private String alias;
	/**
	 * 内置手机号码
	 */
	private String builtinPhone;
	/**
	 * 管理员密码
	 */
	private String adminPassword;
	/**
	 * 地理位置
	 */
	private Area area;
	
	/**
	 * 发放点
	 */
	private String distributionPoints;
	/**
	 * 创建时间
	 */
	private Date creationTime = new Date();
	/**
	 * 设备状态
	 */
	private MachineryEquipmentStateInfo machineryEquipmentState;
	/**
	 * 货道
	 */
	private List<Aisle> aisles;
	/**
	 * 货道数量
	 */
	private Integer aislesNum;
	/**
	 * 设备IP
	 */
	private String ip;
	/**
	 * 数据库锁的version；
	 */
	private long version = 0;
	
	private String remark;
	
	private String sendNum;
	
	private List<Record> records;
	
	
	
	@Override
	public String toString() {
		
		return longitude+";"+deviceNo+";"+latitude+";"+terminalType+";"+alias+";"+builtinPhone+";"+adminPassword+";"+area+";"
		+distributionPoints+";"+creationTime+";"+machineryEquipmentState+";"+aisles+";"+aislesNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getSendNum() {
		return sendNum;
	}
	public void setSendNum(String sendNum) {
		this.sendNum = sendNum;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getBuiltinPhone() {
		return builtinPhone;
	}
	public void setBuiltinPhone(String builtinPhone) {
		this.builtinPhone = builtinPhone;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getDistributionPoints() {
		return distributionPoints;
	}
	public void setDistributionPoints(String distributionPoints) {
		this.distributionPoints = distributionPoints;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public Date getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public MachineryEquipmentStateInfo getMachineryEquipmentState() {
		return machineryEquipmentState;
	}
	public void setMachineryEquipmentState(
			MachineryEquipmentStateInfo machineryEquipmentState) {
		this.machineryEquipmentState = machineryEquipmentState;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public List<Aisle> getAisles() {
		return aisles;
	}
	public void setAisles(List<Aisle> aisles) {
		this.aisles = aisles;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public long getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(long deviceNo) {
		this.no = deviceNo+"";
		this.deviceNo = deviceNo;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public List<Record> getRecords() {
		return records;
	}
	public void setRecords(List<Record> records) {
		this.records = records;
	}
	public Integer getAislesNum() {
		return aislesNum;
	}
	public void setAislesNum(Integer aislesNum) {
		this.aislesNum = aislesNum;
	}
	
}
