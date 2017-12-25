package cn.zectec.contraceptive.management.system.model;
/**
 *  权限
 * @author wxy
 *
 */
@SuppressWarnings("serial")
public class Permission extends Base{
	/**
	 * 权限名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Permission [getName()=" + getName() + ", getDescription()="
				+ getDescription() + ", getId()=" + getId() + "]";
	}	
	
	
}
