package cn.zectec.contraceptive.management.system.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门
 * @author wxy
 *
 */
@SuppressWarnings("serial")
public class Department extends Base {
	/**
	 * 部门名称
	 */
	private String name;
	/**
	 * 部门的描述
	 */
	private String description;
	/**
	 * 所属部门
	 */
	private Department pranetDepartment;
	/**
	 * 子部门
	 */
	private List<Department> childDepartments;
	/**
	 * 部门的级别
	 */
	private long level;
	
	private List<Manager> manager = new ArrayList<Manager>();

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

	public Department getPranetDepartment() {
		return pranetDepartment;
	}

	public void setPranetDepartment(Department pranetDepartment) {
		this.pranetDepartment = pranetDepartment;
	}

	public List<Department> getChildDepartments() {
		return childDepartments;
	}

	public void setChildDepartments(List<Department> childDepartments) {
		this.childDepartments = childDepartments;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	public List<Manager> getManager() {
		return manager;
	}

	public void setManager(List<Manager> manager) {
		this.manager = manager;
	}
}
