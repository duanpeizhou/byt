package cn.zectec.contraceptive.management.system.model;

import java.util.Date;

/**
 * 开门关门时间
 * @author wxy
 *
 */
@SuppressWarnings("serial")
public class OpenDoorRecord extends Record {
	/**
	 * 开门时间
	 */
	private Date openDoorDate;
	/**
	 * 关门时间
	 */
	private Date closeDoorDate;
	public Date getOpenDoorDate() {
		return openDoorDate;
	}
	public void setOpenDoorDate(Date openDoorDate) {
		this.openDoorDate = openDoorDate;
	}
	public Date getCloseDoorDate() {
		return closeDoorDate;
	}
	public void setCloseDoorDate(Date closeDoorDate) {
		this.closeDoorDate = closeDoorDate;
	}
	
	
}
