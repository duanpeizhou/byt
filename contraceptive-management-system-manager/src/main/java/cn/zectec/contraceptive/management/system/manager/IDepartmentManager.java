package cn.zectec.contraceptive.management.system.manager;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.zectec.contraceptive.management.system.model.Department;

public interface IDepartmentManager extends IBaseManager<Department, Long> {
	public List<Department> getDepartment(int level);
	
//	public Page<Department> pageationDepartment(long id, int page, int pageSize);
	
//	public Page<Department> pageationDepartmentAll(long id, int page, int pageSize);
	
	public boolean addDepartment(Department department);
	
	public void deleteDepartment(long id);
	
	public boolean updateDepartment(Department department);
	
	public Page<Department> findByLikeName(String name,int page,int pageSize);
}
