package cn.zectec.contraceptive.management.system.model;

import java.util.Date;

public abstract class Statistical{
	private long id;
	private Date statisticalDate;
	private Area area;
	private Area.Level level;
	private long total;

	/**
	 * 男
	 */
	private long manTotal;
	/**
	 * 女
	 */
	private long womanTotal;
	/**
	 * 本市本县
	 */
	private long countyOfCity;
	/**
	 * 本市外县
	 */
	private long countyOutCity;
	/**
	 * 本省外市
	 */
	private long provinceOutCity;
	/**
	 * 外省市 
	 */
	private long otherProvinces;
	
	
	
	public Statistical(){
		
	}
	
	public Statistical(
			long total, long manTotal, long womanTotal, long countyOfCity,
			long countyOutCity, long provinceOutCity, long otherProvinces) {
		super();
		this.total = total;
		this.manTotal = manTotal;
		this.womanTotal = womanTotal;
		this.countyOfCity = countyOfCity;
		this.countyOutCity = countyOutCity;
		this.provinceOutCity = provinceOutCity;
		this.otherProvinces = otherProvinces;
	}
	public Date getStatisticalDate() {
		return statisticalDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Area.Level getLevel() {
		return level;
	}

	public void setLevel(Area.Level level) {
		this.level = level;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public long getManTotal() {
		return manTotal;
	}

	public void setManTotal(long manTotal) {
		this.manTotal = manTotal;
	}

	public long getWomanTotal() {
		return womanTotal;
	}

	public void setWomanTotal(long womanTotal) {
		this.womanTotal = womanTotal;
	}

	public long getCountyOfCity() {
		return countyOfCity;
	}

	public void setCountyOfCity(long countyOfCity) {
		this.countyOfCity = countyOfCity;
	}

	public long getCountyOutCity() {
		return countyOutCity;
	}

	public void setCountyOutCity(long countyOutCity) {
		this.countyOutCity = countyOutCity;
	}

	public long getProvinceOutCity() {
		return provinceOutCity;
	}

	public void setProvinceOutCity(long provinceOutCity) {
		this.provinceOutCity = provinceOutCity;
	}

	public long getOtherProvinces() {
		return otherProvinces;
	}

	public void setOtherProvinces(long otherProvinces) {
		this.otherProvinces = otherProvinces;
	}

	public void setStatisticalDate(Date statisticalDate) {
		this.statisticalDate = statisticalDate;
	}
	
}
