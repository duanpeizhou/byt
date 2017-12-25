package cn.zectec.contraceptive.management.system.model;

import java.util.Date;

/**
 * 缺货补货
 * @author wxy
 *
 */
@SuppressWarnings("serial")
public class OutStockReplenishmentRecord extends Record{
	/**
	 * 缺货时间
	 */
	private Date outStockDate;
	/**
	 * 货时间
	 */
	private Date replenishmentDate;
	/**
	 * 缺货的种类
	 */
	private Contraceptive contraceptive;
	private Aisle aisle;
	public Date getOutStockDate() {
		return outStockDate;
	}
	public void setOutStockDate(Date outStockDate) {
		this.outStockDate = outStockDate;
	}
	public Date getReplenishmentDate() {
		return replenishmentDate;
	}
	public void setReplenishmentDate(Date replenishmentDate) {
		this.replenishmentDate = replenishmentDate;
	}
	public Contraceptive getContraceptive() {
		return contraceptive;
	}
	public void setContraceptive(Contraceptive contraceptive) {
		this.contraceptive = contraceptive;
	}
	public Aisle getAisle() {
		return aisle;
	}
	public void setAisle(Aisle aisle) {
		this.aisle = aisle;
	}
	
	
}
