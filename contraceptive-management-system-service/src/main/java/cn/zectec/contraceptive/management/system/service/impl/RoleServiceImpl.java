package cn.zectec.contraceptive.management.system.service.impl;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import cn.zectec.contraceptive.management.system.manager.IRoleManager;
import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.model.Menu;
import cn.zectec.contraceptive.management.system.model.MenuPermission;
import cn.zectec.contraceptive.management.system.model.Permission;
import cn.zectec.contraceptive.management.system.model.Role;
import cn.zectec.contraceptive.management.system.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleManager roleManager;

	/**
	 * 描述：分页获取所有角色
	 * @param page 当前页
	 * @param pageSize 当前的页数的显示的条数
	 * @return 获取到的角色
	 */
	@Override
	public Page<Role> getRole(int page, int pageSize) {
		return roleManager.getRole(page,pageSize);
	}
	/**
	 * 描述：添加角色
	 * @param role 角色类
	 * @return true 添加成功   false 添加失败
	 */
	public boolean addRole(Role role) {
		try{
			roleManager.add(role);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 描述：更新角色
	 * @param role 角色类
	 * @return true 更新成功  false 更新失败
	 */
	@Override
	public boolean updateRole(Role role) {
		Role role1 = null;
		try{
			role1 = roleManager.findOne(role.getId());
			role1.setName(role.getName());
			role1.setState(role.isState());
			role1.setRemark(role.getRemark());
			roleManager.update(role1);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 描述：删除角色
	 * @param id 角色id
	 * @return true 删除成功  false 删除失败
	 */
	@Override
	public boolean deleteRole(long id) {
		try{
			Role role = roleManager.findOne(id);
			roleManager.delete(role);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 描述：通过角色名分页查找角色
	 * @param page 当前页
	 * @param pageSize 当前的页数的显示的条数
	 * @param name 角色名
	 * @return 角色
	 */
	@Override
	public Page<Role> getRoleByName(int page, int pageSize, String name) {
		return roleManager.getRoleByName(page, pageSize, name);
	}
	/**
	 * 描述：通过id查找角色
	 * @param id 角色id
	 * @return 角色
	 */
	@Override
	public Role getRoleById(long id) {
		return roleManager.findOne(id);
	}
	/**
	 * 描述：通过roleId分页查找用户
	 * @param page 当前页
	 * @param pageSize 当前的页数的显示的条数
	 * @param roleId 角色id
	 * @return 用户
	 */
	@Override
	public Page<Manager> getUserByRole(int page, int pageSize, long roleId) {
		return roleManager.getUserByRoleId(page,pageSize,roleId);
	}
	/**
	 * 描述：通过id更新状态
	 * @param id 角色id
	 * @return true 更新成功    false 更新失败
	 */
	@Override
	public boolean updateState(long id) {
		try{
			Role role1 = roleManager.findOne(id);
			if(role1.isState()){
				role1.setState(false);
			}else{
				role1.setState(true);
				
			}
			roleManager.update(role1);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 描述：添加权限
	 * @param role 角色类
	 * @param menus
	 */
	@Override
	public void addPermission(Role role, List<Menu> menus) {
		roleManager.addPermission(role,menus);
	}
	/**
	 * 描述：通过roleId得到用户菜单
	 * @param roleId 角色id
	 * @return 用户菜单
	 */
	@Override
	public Set<Menu> getBelongMenus(long roleId) {
		Set<Menu> ms = new HashSet<Menu>();
		for(Permission p :roleManager.findOne(roleId).getPermissions()){
			if(p instanceof MenuPermission){
				ms.add(((MenuPermission) p).getMenu());
			}
		}
		return ms;
	}

}
