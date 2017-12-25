package cn.zectec.contraceptive.management.system.service;


import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.model.Menu;
import cn.zectec.contraceptive.management.system.model.Role;


public interface IRoleService {

	/**
	 * 分页查询角色
	 * @param pate 当前的页码
	 * @param pageSize 当前每页显示的记录条数
	 * @return 返回特定的角色
	 */
	Page<Role> getRole(int pate, int pageSize);
	/**
	 * 添加角色
	 * @param role 角色信息
	 * @return 是否添加成功
	 */
	boolean addRole(Role role);
	/**
	 * 更新角色
	 * @param role 更新角色信息
	 * @return 是否更新成功
	 */
	boolean updateRole(Role role);
	/**
	 * 根据角色的id删除角色
	 * @param id 要删除的角色id
	 * @return 是否成功删除角色
	 */
	boolean deleteRole(long id);
	/**
	 * 通过角色的名字分页查询角色
	 * @param page 当前的页码
	 * @param pageSize 当前的页显示的条数
	 * @param name 角色的名字
	 * @return 符合条件的角色
	 */
	Page<Role> getRoleByName(int page, int pageSize, String name);
	/**
	 * 通过id查询角色
	 * @param id 角色的id
	 * @return 符合调价的角色
	 */
	Role getRoleById(long id);
	/**
	 * 通过角色的id来分页查询管理员
	 * @param page 当前页码
	 * @param pageSize 当前每页显示的条数
	 * @param roleId 角色的id
	 * @return 符合条件的管理员
	 */
	Page<Manager> getUserByRole(int page, int pageSize, long roleId);
	/**
	 * 
	 * @param id
	 * @return
	 */
	boolean updateState(long id);

	public void addPermission(Role role, List<Menu> menus);

	public Set<Menu> getBelongMenus(long roleId);

}
