package cn.zectec.contraceptive.management.system.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.IMenuManager;
import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.model.Menu;
import cn.zectec.contraceptive.management.system.model.MenuPermission;
import cn.zectec.contraceptive.management.system.model.Permission;
import cn.zectec.contraceptive.management.system.model.Role;
import cn.zectec.contraceptive.management.system.repository.IManagerRepository;
import cn.zectec.contraceptive.management.system.repository.IMenuRepository;
import cn.zectec.contraceptive.management.system.repository.util.DynamicSpecifications;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.Operator;

@Component
public class MenuManagerImpl extends SimpleBaseManagerImpl<Menu> implements IMenuManager {
	private IMenuRepository menuRepository;
	
	@Autowired
	private IManagerRepository managerRepository;

	@Autowired
	public MenuManagerImpl(IMenuRepository baseRepository) {
		super(baseRepository);
		this.menuRepository = baseRepository;
	}
	@Override
	public List<Menu> getMenuByLevel(int level) {
		SearchFilter sf=new SearchFilter("level", level);
		Specification<Menu> spec=DynamicSpecifications.bySearchFilter(sf);
		return menuRepository.findAll(spec);
	}
	@Override
	public Page<Menu> getSpecifiedMenu(int page, int pageSize, String id) {
		List<SearchFilter> fitlers=new ArrayList<SearchFilter>();
		if(!("-1").equals(id)){
			SearchFilter filter=new SearchFilter("parentMenu.id",id);
			fitlers.add(filter);
			return this.findBySearchFilters(fitlers, page, pageSize);
		}else{
			
			return menuRepository.findAll(new PageRequest(page, pageSize));
		}
		
	}
	@Override
	public boolean addMenu(Menu menu) {
		try {
			Menu parentMenu=this.findOne(menu.getParentMenu().getId());
			menu.setParentMenu(parentMenu);
			menu.setLevel(parentMenu.getLevel()+1);
			this.add(menu);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	@Override
	public boolean updateMenu(Menu menu) {
		try {
			Menu parentMenu=this.findOne(menu.getParentMenu().getId());
			menu.setParentMenu(parentMenu);
			menu.setLevel(parentMenu.getLevel()+1);
			this.update(menu);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public void deleteMenu(long id) {
		menuRepository.deleteMenu(id);
	}
	@Override
	public List<Menu> getUserMenu(Manager manager) {
		List<Menu> menus = new ArrayList<Menu>();
		manager = managerRepository.findOne(manager.getId());
		if(manager != null){
			for(Role role : manager.getRole()){
				for(Permission p : role.getPermissions()){
					if(p instanceof MenuPermission){
						menus.add(((MenuPermission) p).getMenu());
					}
				}
			}
		}
		return menus;
	}
	@Override
	public List<Menu> findAll(List<Long> ids) {
		return (List<Menu>)menuRepository.findAll(ids);
	}
	@Override
	public Page<Menu> getMenuByName(String name, int page, int pageSize) {
		List<SearchFilter> seracherFilters=new ArrayList<SearchFilter>();
		SearchFilter searchFilter=new SearchFilter("name",Operator.LIKE,name);
		seracherFilters.add(searchFilter);
		return this.findBySearchFilters(seracherFilters, page, pageSize);
	}


}
