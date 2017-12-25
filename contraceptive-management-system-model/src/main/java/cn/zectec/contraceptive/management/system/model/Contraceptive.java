package cn.zectec.contraceptive.management.system.model;

/**
 * 药具种类
 * @author wxy
 *
 */
@SuppressWarnings("serial")
public class Contraceptive extends Base{
	/**
	 * 名称
	 */
	private String name;
	/***
	 * 说明
	 * 
	 */
	private String Description;
	/**
	 * 种类
	 */
	private String category;
	/**
	 * 规格型号
	 */
	private String type;
	/**
	 * 允许领取数量
	 */
	private int allowingNumber;
	/**
	 * 计量单位
	 */
	private String measuringUnit;
	/**
	 * 最小适用年龄
	 * @return
	 */
	private int minAge;
	/**
	 * 最大适用年龄
	 * @return
	 */
	private int maxAge;
	/**
	 * 地域限制
	 */
	private boolean regionalLimit;
	/**
	 * 性别限制
	 */
	private String sexLimit;
	
	
	@Override
	public String toString() {
		return "Contraceptive [id="+this.getId()+"name=" + name + ", Description=" + Description
				+ ", category=" + category + ", type=" + type
				+ ", allowingNumber=" + allowingNumber + ", measuringUnit="
				+ measuringUnit + ", minAge=" + minAge + ", maxAge=" + maxAge
				+ ", regionalLimit=" + regionalLimit + ", sexLimit=" + sexLimit
				+ "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getAllowingNumber() {
		return allowingNumber;
	}
	public void setAllowingNumber(int allowingNumber) {
		this.allowingNumber = allowingNumber;
	}
	public String getMeasuringUnit() {
		return measuringUnit;
	}
	public void setMeasuringUnit(String measuringUnit) {
		this.measuringUnit = measuringUnit;
	}
	public int getMinAge() {
		return minAge;
	}
	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}
	public int getMaxAge() {
		return maxAge;
	}
	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}
	public boolean isRegionalLimit() {
		return regionalLimit;
	}
	public void setRegionalLimit(boolean regionalLimit) {
		this.regionalLimit = regionalLimit;
	}
	public String getSexLimit() {
		return sexLimit;
	}
	public void setSexLimit(String sexLimit) {
		this.sexLimit = sexLimit;
	}

	
	
}
