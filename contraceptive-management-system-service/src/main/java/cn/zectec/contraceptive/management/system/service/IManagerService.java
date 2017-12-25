package cn.zectec.contraceptive.management.system.service;

import org.springframework.data.domain.Page;

import cn.zectec.contraceptive.management.system.model.Manager;

public interface IManagerService {
	/**
	 * 通过 id查询管理员
	 * @param id 要查询管理员的id
	 * @return 符合条件的管理员
	 */
	Manager getManagerById(long id);
	/**
	 * 记录当前登录管理员的信息
	 * @param username 当前要登录的管理员的用户名
	 * @param password 当前要登录的管理员的密码
	 * @return 登录的管理员
	 */
	public Manager login(String username,String password);
	/**
	 * 分页查询管理员
	 * @param page 当前的页码
	 * @param pageSize 当前页码要显示的条数
	 * @return 符合条件的管理员
	 */
	Page<Manager> getManager(int page, int pageSize);
	/**
	 * 添加管理员
	 * @param manager 添加管理员信息
	 */
	void addManager(Manager manager);
	/**
	 * 更新管理信息
	 * @param manager 更新管理员的信息
	 */
	void update(Manager manager);
	/**
	 * 通过id删除管理员
	 * @param id
	 * @return 是否成功删除
	 */
	boolean delete(long id);
	/**
	 * 通过管理的用户名查询管理员
	 * @param username 管理员的用户名
	 * @return 符合条件的管理员
	 */
	Manager getManagerByUsername(String username);
	/**
	 * 通过部门的id获取此部门的管理员
	 * @param page 当前要显示的页码
	 * @param pageSize 当前每页显示的条数
	 * @param id 部门id
	 * @return 符合条件的管理员
	 */
	Page<Manager> getManagerByDep(int page, int pageSize, int id);
	/**
	 * 通过管理员的用户名来模糊分页查询管理员
	 * @param page 当前显示的页码
	 * @param pageSize 当前每页显示的条数
	 * @param username 用户
	 * @return 符合条件的管理员
	 */
	Page<Manager> getManager(int page, int pageSize, String username);
	/**
	 * 更新管理员的状态
	 * @param id 管理员的id
	 * @param roleId
	 */
	void updateState(Long id, int roleId);
	/**
	 * 更改管理员的密码
	 * @param id 管理员的id
	 * @param newPassword 新密码
	 * @return 是否修改成功
	 */
	public boolean updatePassword(long id, String newPassword);
	/**
	 * 修改管理员的所拥有的角色
	 * @param managerId 管理的id 
	 * @param roleId 角色id
	 * @return 是否修改成功
	 */
	public boolean updateManagerPermission(long managerId,long roleId);
	/**
	 * 通过角色id分页查询此角色所拥有的管理员
	 * @param page 当前显示的页码
	 * @param pageSize 当前每页显示的条数
	 * @param roleId 角色id
	 * @return 符合条件的管理员
	 */
	public Page<Manager> getManagersByRoleId(int page,int pageSize,long roleId);

}
