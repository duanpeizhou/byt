package cn.zectec.contraceptive.management.system.model;

/**
 * 菜单访问权限
 * @author wxy
 *
 */
@SuppressWarnings("serial")
public class MenuPermission extends Permission{
	/**
	 * 拥有访问该菜单的权限
	 */
	private Menu menu;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
		if(this.menu != null){
			this.url = menu.getUrl();
		}
	}

	@Override
	public String toString() {
		return "MenuPermission [menu=" + menu + ", url=" + url + ", getId()="
				+ getId() + "]";
	}

}
