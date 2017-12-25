package cn.zectec.contraceptive.management.system.service;

import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.model.Menu;

public interface IPermissionService {
	/**
	 * 得到用户的顶级菜单
	 * @return
	 */
	public Menu getUserTopMenu(long managerId);
	
	/**
	 * 得到用户的顶级区域
	 * @return
	 */
	public Area getUserTopArea(long managerId);
}
