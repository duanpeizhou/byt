package cn.zectec.contraceptive.management.system.model;

import java.io.Serializable;
import java.util.Date;

public class GiveOutInfo implements Serializable{

	private static final long serialVersionUID = 1L;
	private String TFactoryID;
	private String FactoryType="4";
	private String Grove = "1";
	private String GoodsID = "327"; 
	private long BillNumber;
	private int Amount=1;
	private Date Time;
	public String getTFactoryID() {
		return TFactoryID;
	}
	public void setTFactoryID(String tFactoryID) {
		TFactoryID = tFactoryID;
	}
	public String getFactoryType() {
		return FactoryType;
	}
	public void setFactoryType(String factoryType) {
		FactoryType = factoryType;
	}
	public String getGrove() {
		return Grove;
	}
	public void setGrove(String grove) {
		Grove = grove;
	}
	public String getGoodsID() {
		return GoodsID;
	}
	public void setGoodsID(String goodsID) {
		GoodsID = goodsID;
	}
	public long getBillNumber() {
		return BillNumber;
	}
	public void setBillNumber(long billNumber) {
		BillNumber = billNumber;
	}
	public int getAmount() {
		return Amount;
	}
	public void setAmount(int amount) {
		Amount = amount;
	}
	public Date getTime() {
		return Time;
	}
	public void setTime(Date time) {
		Time = time;
	}
	
	public GiveOutInfo() {
		super();
	}
	public GiveOutInfo(String tFactoryID, String factoryType, String grove,
			String goodsID, long billNumber, Date time) {
		super();
		TFactoryID = tFactoryID;
		FactoryType = factoryType;
		Grove = grove;
		GoodsID = goodsID;
		BillNumber = billNumber;
		Time = time;
	}
	@Override
	public String toString() {
		return "GiveOutInfo [TFactoryID=" + TFactoryID + ", FactoryType="
				+ FactoryType + ", Grove=" + Grove + ", GoodsID=" + GoodsID
				+ ", BillNumber=" + BillNumber + ", Amount=" + Amount
				+ ", Time=" + Time + "]";
	}
	
}
