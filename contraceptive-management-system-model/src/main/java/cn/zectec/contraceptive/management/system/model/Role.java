package cn.zectec.contraceptive.management.system.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色
 * @author wxy
 *
 */
@SuppressWarnings("serial")
public class Role extends  Base{
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 状态
	 */
	private boolean state = true;
	/**
	 * 管理员
	 */
	private List<Manager> managers = new ArrayList<Manager>();
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 权限
	 */
	private Set<Permission> permissions = new HashSet<Permission>();
	/**
	 * 备注
	 */
	private String remark;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public List<Manager> getManagers() {
		return managers;
	}
	public void setManagers(List<Manager> managers) {
		this.managers = managers;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
