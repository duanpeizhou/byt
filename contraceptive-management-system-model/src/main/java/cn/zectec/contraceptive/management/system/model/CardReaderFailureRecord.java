package cn.zectec.contraceptive.management.system.model;

import java.util.Date;

/***
 * 读卡器故障时间
 * 
 * @author wxy
 * 
 */
@SuppressWarnings("serial")
public class CardReaderFailureRecord extends Record {
	/**
	 * 故障时间
	 */
	private Date failureDate;
	/**
	 * 恢复时间
	 */
	private Date recoveryDate;
	
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
