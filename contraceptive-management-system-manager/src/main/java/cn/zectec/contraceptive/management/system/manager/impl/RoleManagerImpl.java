package cn.zectec.contraceptive.management.system.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.IManagerManager;
import cn.zectec.contraceptive.management.system.manager.IRoleManager;
import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.model.Menu;
import cn.zectec.contraceptive.management.system.model.MenuPermission;
import cn.zectec.contraceptive.management.system.model.Permission;
import cn.zectec.contraceptive.management.system.model.Role;
import cn.zectec.contraceptive.management.system.repository.IMenuPermissionRepository;
import cn.zectec.contraceptive.management.system.repository.IPermissionRepository;
import cn.zectec.contraceptive.management.system.repository.IRoleRepository;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.Operator;

@Component
public class RoleManagerImpl extends SimpleBaseManagerImpl<Role> implements IRoleManager {


	private IRoleRepository roleRepository;
	@Autowired
	private IMenuPermissionRepository menuPermissionRepository;
	@Autowired
	public RoleManagerImpl(IRoleRepository baseRepository) {
		super(baseRepository);
		this.roleRepository=baseRepository;
	}

	@Autowired
	private IManagerManager managerManager;
	


	@Override
	public Page<Role> getRole(int page, int pageSize) {
		return roleRepository.findAll(new PageRequest(page, pageSize));
	}



	@Override
	public Page<Role> getRoleByName(int page, int pageSize, String name) {
		List<SearchFilter> seracherFilters=new ArrayList<SearchFilter>();
		SearchFilter searchFilter=new SearchFilter("name",Operator.LIKE,name);
		seracherFilters.add(searchFilter);
		return this.findBySearchFilters(seracherFilters, page, pageSize);
	}



	@Override
	public Page<Manager> getUserByRoleId(int page, int pageSize, long roleId) {
		List<SearchFilter> seracherFilters=new ArrayList<SearchFilter>();
		SearchFilter searchFilter=new SearchFilter("id",roleId);
		seracherFilters.add(searchFilter);
		return managerManager.findBySearchFilters(seracherFilters, page, pageSize);
	}



	@Override
	public void addPermission(Role role, List<Menu> menus) {
		Set<Permission> ps = role.getPermissions();
		List<Permission> remove = new ArrayList<Permission>();
		List<Menu> temp = new ArrayList<Menu>();
		for(Permission p : ps){
			if(p instanceof MenuPermission){
				MenuPermission mp = (MenuPermission)p;
				if(!menus.contains(mp.getMenu())){
					remove.add(p);
				}else{
					temp.add(mp.getMenu());
				}
			}
		}
		role.getPermissions().removeAll(remove);
		List<MenuPermission> menuPermissions = new ArrayList<MenuPermission>();
		for(Menu m :  menus){
			if(!temp.contains(m)){
				MenuPermission mp = new MenuPermission();
				mp.setName("菜单权限");
				mp.setMenu(m);
				menuPermissions.add(mp);
			}
		}
		menuPermissions = (List<MenuPermission>)menuPermissionRepository.save(menuPermissions);
		role.getPermissions().addAll(menuPermissions);
		roleRepository.save(role);
	}

}
