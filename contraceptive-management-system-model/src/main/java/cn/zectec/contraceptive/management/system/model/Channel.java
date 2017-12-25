package cn.zectec.contraceptive.management.system.model;

public class Channel {
	//货道编号1-4
	private long ID;
	//产品代码
	private String ProductCode;
	//是否缺货,1-缺货,0-不缺货
	private long IsEmpty;
	//货道是否可用,1-可用,0-不可用
	private long Usable;
	
	public Channel(long iD, String productCode, long SIsEmpty, long usable) {
		super();
		ID = iD;
		ProductCode = productCode;
		this.IsEmpty = SIsEmpty;
		Usable = usable;
	}
	
	public Channel() {
		super();
	}

	public long getID() {
		return ID;
	}
	public void setID(long iD) {
		ID = iD;
	}
	public String getProductCode() {
		return ProductCode;
	}
	public void setproductCode(String productCode) {
		ProductCode = productCode;
	}
	/*public long getIsEmpty() {
		return IsEmpty;
	}
	public void setFIsEmpty(long IsEmpty) {
		this.IsEmpty = IsEmpty;
	}*/
	
	public long getUsable() {
		return Usable;
	}
	public long getIsEmpty() {
		return IsEmpty;
	}

	public void setIsEmpty(long isEmpty) {
		IsEmpty = isEmpty;
	}

	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}

	public void setUsable(long usable) {
		Usable = usable;
	}
	
}
