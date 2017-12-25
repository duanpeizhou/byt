package cn.zectec.contraceptive.management.system.manager.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.IDepartmentManager;
import cn.zectec.contraceptive.management.system.model.Department;
import cn.zectec.contraceptive.management.system.repository.IDepartmentRepository;
import cn.zectec.contraceptive.management.system.repository.util.DynamicSpecifications;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.Operator;

@Component
public class DepartmentManagerImpl extends SimpleBaseManagerImpl<Department>
		implements IDepartmentManager {
	private IDepartmentRepository departmentRepository;

	@Autowired
	public DepartmentManagerImpl(IDepartmentRepository baseRepository) {
		super(baseRepository);
		this.departmentRepository = baseRepository;
	}

	@Override
	public List<Department> getDepartment(int level) {
		SearchFilter filter = new SearchFilter("level", level);
		Specification<Department> spec = DynamicSpecifications
				.bySearchFilter(filter);
		return departmentRepository.findAll(spec);
	}

//	@Override
//	public Page<Department> pageationDepartment(long id, int page, int pageSize) {
//		SearchFilter filter = new SearchFilter("pranetDepartment",
//				departmentRepository.findOne(id));
//		return this.findBySearchFilters(Arrays.asList(filter), page, pageSize);
//	}

//	@Override
//	public Page<Department> pageationDepartmentAll(long id, int page,
//			int pageSize) {
//		
//		
//		SearchFilter filter = new SearchFilter("level", SearchFilter.Operator.NEQ,
//				departmentRepository.findOne(id).getLevel());
//		return this.findBySearchFilters(Arrays.asList(filter), page, pageSize);
//	}

	@Override
	public boolean addDepartment(Department department) {
		try {
			departmentRepository.save(department);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public void deleteDepartment(long id) {
		try {
			departmentRepository.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean updateDepartment(Department department) {
		try {
			this.update(department);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Page<Department> findByLikeName(String name,int page,int pageSize) {
		List<SearchFilter> seracherFilters=new ArrayList<SearchFilter>();
		SearchFilter seracherFilter = new SearchFilter("name", Operator.LIKE, name);
		seracherFilters.add(seracherFilter);
		return this.findBySearchFilters(seracherFilters, page, pageSize);
	}
}
