package cn.zectec.contraceptive.management.system.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zectec.contraceptive.management.system.manager.IManagerManager;
import cn.zectec.contraceptive.management.system.manager.IPermissionManager;
import cn.zectec.contraceptive.management.system.manager.IRoleManager;
import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.model.Menu;
import cn.zectec.contraceptive.management.system.model.MenuPermission;
import cn.zectec.contraceptive.management.system.model.Permission;
import cn.zectec.contraceptive.management.system.model.Role;
import cn.zectec.contraceptive.management.system.service.IPermissionService;

@Service
public class PermissionServiceImpl implements IPermissionService{
	@Autowired
	private IPermissionManager permissionManager;
	@Autowired
	private IRoleManager roleManager;
	@Autowired
	private IManagerManager managerManager;
	/**
	 * 得到用户的顶级菜单
	 * @param managerId 用户id
	 * @return 用户的顶级菜单
	 */
	@Override
	public Menu getUserTopMenu(long managerId) {
		Manager manager = managerManager.findOne(managerId);
		if(manager == null){
			return null;
		}
		Set<Role> roles = manager.getRole();
		Menu menu = null;
		Set<Menu> menus = new HashSet<Menu>();
		for(Role r : roles){
			Set<Permission> permissions = r.getPermissions();
			for(Permission p : permissions){
				if(p instanceof MenuPermission){
					MenuPermission menuPermission = (MenuPermission)p;
					menus.add(menuPermission.getMenu());
				}
			}
		}
		return menu;
	}
	/**
	 * 得到用户的顶级区域 
	 * @return managerId 用户id
	 * @return 用户的顶级区域
	 */
	@Override
	public Area getUserTopArea(long managerId) {
		return null;
	}

}
