package cn.zectec.contraceptive.management.system.model;

import java.util.Date;

@SuppressWarnings("serial")
public class MachineryEquipmentStateChangeRecord extends Record {
	/**
	 * 改变的状态
	 */
	private MachineryEquipmentState state;
	/**
	 * 发生的时间
	 */
	private Date hanpenDate;
	/**
	 * 详细描述
	 */
	private String detail;
	/**
	 * 如果为补货或缺货 的药品
	 */
	private Contraceptive contraceptive;
	
	public MachineryEquipmentState getState() {
		return state;
	}
	public void setState(MachineryEquipmentState state) {
		this.state = state;
	}
	public Date getHanpenDate() {
		return hanpenDate;
	}
	public void setHanpenDate(Date hanpenDate) {
		this.hanpenDate = hanpenDate;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Contraceptive getContraceptive() {
		return contraceptive;
	}
	public void setContraceptive(Contraceptive contraceptive) {
		this.contraceptive = contraceptive;
	}
	
	
}
