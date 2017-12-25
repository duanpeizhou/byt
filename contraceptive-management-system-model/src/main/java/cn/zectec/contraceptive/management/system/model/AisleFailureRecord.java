package cn.zectec.contraceptive.management.system.model;

import java.util.Date;

/**
 * 货道故障
 * @author wxy
 *
 */
@SuppressWarnings("serial")
public class AisleFailureRecord extends Record{
	/**
	 * 货道
	 */
	private Aisle aisle;
	/**
	 * 故障时间
	 */
	private Date failureDate;
	/**
	 * 恢复时间
	 */
	private Date recoveryDate;
	public Aisle getAisle() {
		return aisle;
	}
	public void setAisle(Aisle aisle) {
		this.aisle = aisle;
	}
	public Date getFailureDate() {
		return failureDate;
	}
	public void setFailureDate(Date failureDate) {
		this.failureDate = failureDate;
	}
	public Date getRecoveryDate() {
		return recoveryDate;
	}
	public void setRecoveryDate(Date recoveryDate) {
		this.recoveryDate = recoveryDate;
	}
}
