package cn.zectec.contraceptive.management.system.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.IManagerManager;
import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.model.Role;
import cn.zectec.contraceptive.management.system.repository.IManagerRepository;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.Operator;

@Component
public class ManagerManagerImpl extends SimpleBaseManagerImpl<Manager> implements IManagerManager {
	private IManagerRepository managerRepository;
	@Autowired
	public ManagerManagerImpl(IManagerRepository baseRepository) {
		super(baseRepository);
		this.managerRepository = baseRepository;
	}

	@Override
	public Manager findOneOnLock(long id) {
		return managerRepository.findOneOnLock(id);
	}

	@Override
	public Page<Manager> getManager(int page, int pageSize) {
		return managerRepository.findAll(new PageRequest(page, pageSize));
	}

	@Override
	public Page<Manager> getManager(int page, int pageSize, int id) {
		List<SearchFilter> searchFilters = new ArrayList<>();
		SearchFilter searchFilter = new SearchFilter("department.id",id);
		searchFilters.add(searchFilter);
		
		return this.findBySearchFilters(searchFilters, page, pageSize);
	}

	@Override
	public Page<Manager> getManager(int page, int pageSize, String username) {
		List<SearchFilter> searchFilters = new ArrayList<>();
		SearchFilter searchFilter = new SearchFilter("username",Operator.LIKE,username);
		searchFilters.add(searchFilter);
		return this.findBySearchFilters(searchFilters, page, pageSize);
	}

	@Override
	public void addMananger(Manager manager) {
		this.add(manager);
	}

	@Override
	public boolean updatePassword(long id, String newPassword) {
		try {
			Manager m=this.findOne(id);
			m.setPassword(newPassword);
			m.setLastModifyPasswordTime(new Date());
			this.update(m);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean udpateManagerPermission(Manager manager) {
		try {
			this.update(manager);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Page<Manager> getManagersByRoleId(int page, int pageSize, Role role) {
		return managerRepository.findManagerByRole(role, new PageRequest(page, pageSize));
	}

}
