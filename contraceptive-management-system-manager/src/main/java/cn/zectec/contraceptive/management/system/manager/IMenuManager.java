package cn.zectec.contraceptive.management.system.manager;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.model.Menu;

public interface IMenuManager extends IBaseManager<Menu, Long> {
	public List<Menu> getMenuByLevel(int level);
	public Page<Menu> getSpecifiedMenu(int page,int pageSize,String id);
	public boolean addMenu(Menu menu);
	public boolean updateMenu(Menu menu);
	public void deleteMenu(long id);

	public List<Menu> getUserMenu(Manager manager);
	public List<Menu> findAll(List<Long> ids);
	public Page<Menu> getMenuByName(String name, int page, int pageSize);

}
