package cn.zectec.contraceptive.management.system.model;

import java.util.Date;

/**
 * 温度超限
 * @author wxy
 *
 */
@SuppressWarnings("serial")
public class OverTemperatureRecord extends Record {
	/**
	 * 超限时间
	 */
	private Date overTemperatureDate;
	/**
	 * 超限温度
	 */
	private int overTemperature;
	/**
	 * 恢复时间
	 */
	private Date recoveryDate;
	/**
	 * 恢复时间
	 */
	private int recoveryTemperature;
	public Date getOverTemperatureDate() {
		return overTemperatureDate;
	}
	public void setOverTemperatureDate(Date overTemperatureDate) {
		this.overTemperatureDate = overTemperatureDate;
	}
	public int getOverTemperature() {
		return overTemperature;
	}
	public void setOverTemperature(int overTemperature) {
		this.overTemperature = overTemperature;
	}
	public Date getRecoveryDate() {
		return recoveryDate;
	}
	public void setRecoveryDate(Date recoveryDate) {
		this.recoveryDate = recoveryDate;
	}
	public int getRecoveryTemperature() {
		return recoveryTemperature;
	}
	public void setRecoveryTemperature(int recoveryTemperature) {
		this.recoveryTemperature = recoveryTemperature;
	}
	
}
