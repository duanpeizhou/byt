package cn.zectec.contraceptive.management.system.model;

import java.util.Date;

/**
 * 设备状态信息
 * @author wxy
 *
 */
@SuppressWarnings("serial")
public class MachineryEquipmentStateInfo extends Base{
	/**
	 * 信号强度
	 */
	private int signalStrength;
	/**
	 * 设备温度
	 */
	private int deviceTemperature;
	private boolean overTemperature;
	private Date overTemperatureDate;
	/**
	 * 连接状态
	 */
	private boolean connectionState;
	/**
	 * 上线时间
	 */
	private Date onlineDate;
	/**
	 * 离线时间
	 */
	private Date offlineDate;
	/**
	 * 开门状态
	 */
	private boolean doorState;
	/**
	 * 开门时间
	 */
	private Date doorDate;
	
	/**
	 * 关门时间
	 */
	private Date closeDoorDate;
	/**
	 * 最后一次更新时间
	 */
	private Date lastUpdateTime;
	private boolean cardReaderFailure;
	private Date cardReaderFailureDate;
	
	public boolean stockOut = false;
	
	private MachineryEquipment machineryEquipment;
	
	private Integer hardNo;
	private Byte faultNo;
	private Byte ver;

	public Byte getVer() {
		return ver;
	}



	public void setVer(Byte ver) {
		this.ver = ver;
	}



	public int getSignalStrength() {
		return signalStrength;
	}



	public Integer getHardNo() {
		return hardNo;
	}



	public void setHardNo(Integer hardNo) {
		this.hardNo = hardNo;
	}

	public Byte getFaultNo() {
		return faultNo;
	}



	public void setFaultNo(Byte faultNo) {
		this.faultNo = faultNo;
	}



	public void setSignalStrength(int signalStrength) {
		this.signalStrength = signalStrength;
	}

	public int getDeviceTemperature() {
		return deviceTemperature;
	}

	public void setDeviceTemperature(int deviceTemperature) {
		this.deviceTemperature = deviceTemperature;
	}

	public boolean isOverTemperature() {
		return overTemperature;
	}

	public boolean isStockOut() {
		return stockOut;
	}

	public void setStockOut(boolean stockOut) {
		this.stockOut = stockOut;
	}

	public void setOverTemperature(boolean overTemperature) {
		this.overTemperature = overTemperature;
	}

	public boolean isConnectionState() {
		return connectionState;
	}

	public void setConnectionState(boolean connectionState) {
		this.connectionState = connectionState;
	}

	public Date getOnlineDate() {
		return onlineDate;
	}

	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}

	public Date getOfflineDate() {
		return offlineDate;
	}

	public void setOfflineDate(Date offlineDate) {
		this.offlineDate = offlineDate;
	}

	public boolean isDoorState() {
		return doorState;
	}

	public void setDoorState(boolean doorState) {
		this.doorState = doorState;
	}

	public Date getDoorDate() {
		return doorDate;
	}

	public void setDoorDate(Date doorDate) {
		this.doorDate = doorDate;
	}

	public Date getCloseDoorDate() {
		return closeDoorDate;
	}

	public void setCloseDoorDate(Date closeDoorDate) {
		this.closeDoorDate = closeDoorDate;
	}

	public Date getOverTemperatureDate() {
		return overTemperatureDate;
	}

	public void setOverTemperatureDate(Date overTemperatureDate) {
		this.overTemperatureDate = overTemperatureDate;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public boolean isCardReaderFailure() {
		return cardReaderFailure;
	}

	public void setCardReaderFailure(boolean cardReaderFailure) {
		this.cardReaderFailure = cardReaderFailure;
	}

	public Date getCardReaderFailureDate() {
		return cardReaderFailureDate;
	}

	public void setCardReaderFailureDate(Date cardReaderFailureDate) {
		this.cardReaderFailureDate = cardReaderFailureDate;
	}

	public MachineryEquipment getMachineryEquipment() {
		return machineryEquipment;
	}

	public void setMachineryEquipment(MachineryEquipment machineryEquipment) {
		this.machineryEquipment = machineryEquipment;
	}

	
	
	
	
}
