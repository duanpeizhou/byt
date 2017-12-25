package cn.zectec.contraceptive.management.system.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.zectec.contraceptive.management.system.model.Menu;
public interface IMenuService {
	/**
	 * 根据菜单的级别来查询菜单
	 * @param level 菜单的级别
	 * @return 符合条件的菜单
	 */
	public List<Menu> getMenuByLevel(int level);
	/**
	 * 获取所有的菜单
	 * @return  所有的菜单
	 */
	public List<Menu> getAllMenu();
	/**
	 * 获取当前管理员所拥有的菜单
	 * @return 当前管理员所拥有的菜单
	 */
	public Menu getCurrentManagerMenu();
	/**
	 * 通过菜单的夫id分页查询菜单
	 * @param page 当前的页码
	 * @param pageSize 当前的页显示的条数
	 * @param id 菜单的夫id
	 * @return 符合条件的菜单
	 */
	public Page<Menu> getSpecifiedMenu(int page,int pageSize,String id);
	/**
	 * 添加菜单
	 * @param menu 添加的菜单信息
	 * @return 是否添加成功
	 */
	public boolean addMenu(Menu menu);
	/**
	 * 更新菜单
	 * @param menu 要更新的菜单信息
	 * @return 是否更新成功
	 */
	public boolean updateMenu(Menu menu);
	/**
	 * 通过id删除菜单
	 * @param id 要删除的菜单的 id
	 * @return 是否删除成功
	 */
	public boolean deleteMenu(long id);
	/**
	 * 通过多个id得到菜单
	 * @param asList 多个id
	 * @return 符合条件的id
	 */
	public List<Menu> findMenusById(long[] asList);
	/**
	 * 通过才的名字分页查新菜单
	 * @param page  当前的页码
	 * @param pageSize 当前页码所有显示的条数
	 * @param name 菜单的名字
	 * @return  符合条件的菜单
	 */
	public Page<Menu> getMenuByName(int page, int pageSize, String name);

}
