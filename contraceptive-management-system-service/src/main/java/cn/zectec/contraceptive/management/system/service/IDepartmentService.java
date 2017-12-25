package cn.zectec.contraceptive.management.system.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.zectec.contraceptive.management.system.model.Department;

public interface IDepartmentService {
	/**
	 * @描述: 获取所有部门 通过level显示节点级别 
	 * @param level
	 * @return
	 */
	public List<Department> getDepartment(int level);

//	public Page<Department> pageationDepartment(long id, int page, int pageSize);
	/**
	 * @描述：获取所有部门（分页显示）通过id 控制要显示的子部门 默认显示所有子部门信息
	 * @param id
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Page<Department> pageationDepartmentAll(long id, int page,int pageSize);
	
	/**
	 * 描述：添加部门 （标示为那个部门下的子部门）
	 * @param department
	 * @return
	 */
	public boolean addDepartment(Department department);
	
	/**
	 * 描述： 删除部门通过 id
	 * @param id
	 */
	public void deleteDepartment(long id);
	
	/**
	 * 描述：更新部门（标示为那个部门下的子部门）
	 * @param department
	 * @return
	 */
	public boolean updateDepartment(Department department);
	/**
	 * 描述：模糊查询数据 （通过数据的名字直接查出数据位置。名字可以是模糊查询）
	 * @param name
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public Page<Department> findByLikeName(String name ,int page, int pageSize);

}
