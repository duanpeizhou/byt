package cn.zectec.contraceptive.management.system.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import cn.zectec.contraceptive.management.system.manager.IMenuManager;
import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.model.Menu;
import cn.zectec.contraceptive.management.system.security.service.SecurityContext;
import cn.zectec.contraceptive.management.system.service.IMenuService;

@Service
public class MenuServiceImpl implements IMenuService{
	@Autowired
	private IMenuManager menuManager;
	/**
	 * 根据菜单的级别来查询菜单
	 * @param level 菜单的级别
	 * @return 符合条件的菜单
	 */
	@Override
	public List<Menu> getMenuByLevel(int level) {
		return menuManager.getMenuByLevel(level);
	}
	/**
	 * 获取所有的菜单
	 * @return  所有的菜单
	 */
	@Override
	public List<Menu> getAllMenu() {
		return menuManager.findAll();
	}
	/**
	 * 通过菜单的夫id分页查询菜单
	 * @param page 当前的页码
	 * @param pageSize 当前的页显示的条数
	 * @param id 菜单的夫id
	 * @return 符合条件的菜单
	 */
	@Override
	public Page<Menu> getSpecifiedMenu(int page, int pageSize, String id) {
		return menuManager.getSpecifiedMenu(page, pageSize, id);
	}

	/**
	 * 添加菜单
	 * @param menu 添加的菜单信息
	 * @return 是否添加成功
	 */
	@Override
	public boolean addMenu(Menu menu) {
		return menuManager.addMenu(menu);
	}
	/**
	 * 更新菜单
	 * @param menu 要更新的菜单信息
	 * @return 是否更新成功
	 */
	@Override
	public boolean updateMenu(Menu menu) {
		return menuManager.updateMenu(menu);
	}
	/**
	 * 通过id删除菜单
	 * @param id 要删除的菜单的 id
	 * @return 是否删除成功
	 */
	@Override
	public boolean deleteMenu(long id) {

		try {
			Menu m = menuManager.findOne(id);
			if(m.getChildMenu().isEmpty()){
				menuManager.deleteMenu(id);
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 获取当前管理员所拥有的菜单
	 * @return 当前管理员所拥有的菜单
	 */
	public Menu getCurrentManagerMenu() {
		Manager manager = SecurityContext.getCurrentManager();
		if(manager == null){
			return null;
		}
		List<Menu> menus = menuManager.getUserMenu(manager);
		Set<Menu> ms = new HashSet<Menu>(menus);
		for(Menu m : menus){
			putMenu(ms,m.getParentMenu());
		}
		List<Menu> allMenus = menuManager.findAll();
		allMenus.removeAll(ms);
		//清除childmenus中的数据
		for(Menu menu : ms){
			for(Menu m : allMenus){
				if(menu.getChildMenu()!=null && menu.getChildMenu().contains(m)){
					menu.getChildMenu().remove(m);
				}
			}
		}
		for(Menu m: ms){
			if(m.getParentMenu()==null){
				return m;
			}
		}
		return null;
	}
	/**
	 * 将菜单放到set集合里面
	 * @param ms 目标set集合
	 * @param m 要放入的菜单
	 */
	private void putMenu(Set<Menu> ms, Menu m) {
		if(m == null){
			return;
		}
		Menu menu = m.getParentMenu();
		ms.add(m);
		putMenu(ms,menu);
	}
	/**
	 * 通过多个id得到菜单
	 * @param asList 多个id
	 * @return 符合条件的id
	 */
	@Override
	public List<Menu> findMenusById(long[] asList) {
		List<Long> ids = new ArrayList<Long>();
		for(long id : asList){
			ids.add(id);
		}
		return menuManager.findAll(ids);
	}
	/**
	 * 通过才的名字分页查新菜单
	 * @param page  当前的页码
	 * @param pageSize 当前页码所有显示的条数
	 * @param name 菜单的名字
	 * @return  符合条件的菜单
	 */
	@Override
	public Page<Menu> getMenuByName(int page, int pageSize, String name) {
		return menuManager.getMenuByName(name, page, pageSize);
	}
	
	
}
