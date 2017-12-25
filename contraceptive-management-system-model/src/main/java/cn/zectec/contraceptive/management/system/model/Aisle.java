package cn.zectec.contraceptive.management.system.model;

import java.util.Date;

/**
 * 货道
 * @author wxy
 *
 */
@SuppressWarnings("serial")
public class Aisle extends Base {
	/**
	 * 避孕用具
	 */
	private Contraceptive contraceptive;
	/**
	 * 数量
	 */
	private int num;
	/**
	 * 设备
	 */
	private MachineryEquipment machineryEquipment;
	/**
	 * 锁的version;
	 */
	private long version;
	/**
	 * 顺序
	 */
	private int index_ = 0;
	/**
	 * 是否缺货
	 */
	private Boolean stockout = false;
	/**
	 * 缺货时间
	 */
	private Date stockoutDate;
	/**
	 * 货道是否正常
	 */
	private Boolean aisleFailure = false;
	/**
	 * 货道故障的时间
	 */
	private Date aisleFailureDate;
	public Contraceptive getContraceptive() {
		return contraceptive;
	}
	public void setContraceptive(Contraceptive contraceptive) {
		this.contraceptive = contraceptive;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public MachineryEquipment getMachineryEquipment() {
		return machineryEquipment;
	}
	public void setMachineryEquipment(MachineryEquipment machineryEquipment) {
		this.machineryEquipment = machineryEquipment;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
	public int getIndex_() {
		return index_;
	}
	public void setIndex_(int index_) {
		this.index_ = index_;
	}
	public Boolean getStockout() {
		return stockout;
	}
	public void setStockout(Boolean stockout) {
		this.stockout = stockout;
	}
	public Date getStockoutDate() {
		return stockoutDate;
	}
	public void setStockoutDate(Date stockoutDate) {
		this.stockoutDate = stockoutDate;
	}
	public Boolean getAisleFailure() {
		return aisleFailure;
	}
	public void setAisleFailure(Boolean aisleFailure) {
		this.aisleFailure = aisleFailure;
	}
	public Date getAisleFailureDate() {
		return aisleFailureDate;
	}
	public void setAisleFailureDate(Date aisleFailureDate) {
		this.aisleFailureDate = aisleFailureDate;
	}


}
