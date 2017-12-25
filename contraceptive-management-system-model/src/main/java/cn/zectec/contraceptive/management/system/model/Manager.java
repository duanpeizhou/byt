package cn.zectec.contraceptive.management.system.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 管理员
 * @author wxy
 *
 */
@SuppressWarnings("serial")
public class Manager extends Base {
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 *
	 */
	private String password;
	
	private String name;
	private Department department;
	private Set<Role> role = new HashSet<Role>();
	private boolean enable = true;
	private Date lastLoginTime;
	private Date LastModifyPasswordTime;
	private String ip;
	private Integer loginTimes = 0;
	private int version = 0;
	private Area county;
	private Area townshipStreet;
	private boolean superManager = false;
	private String remark;

	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public boolean getSuperManager() {
		return superManager;
	}
	public void setSuperManager(boolean superManager) {
		this.superManager = superManager;
	}
	public int getVersion() {
		return version;
	}

	public Area getCounty() {
		return county;
	}
	public void setCounty(Area county) {
		this.county = county;
	}
	public Area getTownshipStreet() {
		return townshipStreet;
	}
	public void setTownshipStreet(Area townshipStreet) {
		this.townshipStreet = townshipStreet;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getLoginTimes() {
		return loginTimes;
	}
	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Set<Role> getRole() {
		return role;
	}
	public void setRole(Set<Role> role) {
		this.role = role;
	}
	public boolean getEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Date getLastModifyPasswordTime() {
		return LastModifyPasswordTime;
	}
	public void setLastModifyPasswordTime(Date lastModifyPasswordTime) {
		LastModifyPasswordTime = lastModifyPasswordTime;
	}
	@Override
	public String toString() {
		return "Manager [username=" + username + ", password=" + password
				+ ", role=" + role + ", enable=" + enable + ", lastLoginTime="
				+ lastLoginTime + ", LastModifyPasswordTime="
				+ LastModifyPasswordTime + "]";
	}
	
}
