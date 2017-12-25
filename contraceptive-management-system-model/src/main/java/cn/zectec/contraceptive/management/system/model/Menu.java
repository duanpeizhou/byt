package cn.zectec.contraceptive.management.system.model;

import java.util.List;

/**
 * 菜单
 * @author wxy
 *
 */
@SuppressWarnings("serial")
public class Menu extends Base{
	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 顺序
	 */
	private int order;
	/**
	 * 级别
	 */
	private int level;
	/**
	 * url地址
	 */
	private String url;
	/**
	 * 父菜单
	 */
	private Menu parentMenu;
	/**
	 * 子菜单
	 */
	private List<Menu> childMenu;
	/**
	 * 菜单说明
	 */
	private String description;
	


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Menu getParentMenu() {
		return parentMenu;
	}

	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}

	public List<Menu> getChildMenu() {
		return childMenu;
	}

	public void setChildMenu(List<Menu> childMenu) {
		this.childMenu = childMenu;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Menu [name=" + name + ", order=" + order + ", level=" + level
				+ ", url=" + url + ", childMenu=" + childMenu
				+ ", description=" + description + "]";
	}
	
	
}
