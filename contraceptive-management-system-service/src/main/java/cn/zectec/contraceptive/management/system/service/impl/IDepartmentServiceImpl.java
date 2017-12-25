package cn.zectec.contraceptive.management.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import cn.zectec.contraceptive.management.system.manager.IDepartmentManager;
import cn.zectec.contraceptive.management.system.model.Area.Level;
import cn.zectec.contraceptive.management.system.model.Department;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
import cn.zectec.contraceptive.management.system.service.IDepartmentService;
@Service
public class IDepartmentServiceImpl implements IDepartmentService{
	@Autowired
	private IDepartmentManager departmentManager;
	/**
	 * @描述: 获取所有部门 通过level显示节点级别 
	 * @param level 等级
	 * @return Department 部门
	 */
	@Override
	public List<Department> getDepartment(int level) {
		// TODO Auto-generated method stub
		return departmentManager.getDepartment(level);
	}
//	@Override
//	public Page<Department> pageationDepartment(long id, int page, int pageSize) {
//		return departmentManager.pageationDepartment(id, page, pageSize);
//	}
	/**
	 * @描述：获取所有部门（分页显示）通过id 控制要显示的子部门 默认显示所有子部门信息
	 * @param id 部门id
	 * @param page 当前页
	 * @param pageSize 每页数据量
	 * @return 分页查询到的部门信息
	 */
	@Override
	public Page<Department> pageationDepartmentAll(long id, int page,int pageSize) {
		List<SearchFilter> searchFilters= new ArrayList<SearchFilter>();
		if(id==-1l){
			SearchFilter seaFilter = new SearchFilter("level",2);
			searchFilters.add(seaFilter);
			return departmentManager.findBySearchFilters(searchFilters, page, pageSize);
		}
		SearchFilter seaFilter = new SearchFilter("pranetDepartment.id",id);
		searchFilters.add(seaFilter);
		return departmentManager.findBySearchFilters(searchFilters, page, pageSize);
	}
	/**
	 * 描述：添加部门 （标示为那个部门下的子部门）
	 * @param department 部门类
	 * @return true 添加成功   false 添加失败
	 */
	@Override
	public boolean addDepartment(Department department) {
		department.setPranetDepartment(departmentManager.findOne(department.getPranetDepartment().getId()));
		return departmentManager.addDepartment(department);
	}
	/**
	 * 描述： 删除部门通过 id
	 * @param id 部门id
	 */
	@Override
	public void deleteDepartment(long id) {
	departmentManager.deleteDepartment(id);
	}
	/**
	 * 描述：更新部门（标示为那个部门下的子部门）
	 * @param department 部门类
	 * @return true 更新成功   false 更新失败
	 */
	@Override
	public boolean updateDepartment(Department department) {
		return departmentManager.updateDepartment(department);
	}
	/**
	 * 描述：模糊查询数据 （通过数据的名字直接查出数据位置。名字可以是模糊查询）
	 * @param name 部门名称
	 * @param page 当前页
	 * @param pageSize 每页数据量
	 * @return 条件查询到的部门信息
	 */
	@Override
	public Page<Department> findByLikeName(String name, int page, int pageSize) {
		return departmentManager.findByLikeName(name, page, pageSize);
	}
}
