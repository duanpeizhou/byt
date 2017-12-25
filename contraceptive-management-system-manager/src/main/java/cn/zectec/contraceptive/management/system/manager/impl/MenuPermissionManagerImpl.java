package cn.zectec.contraceptive.management.system.manager.impl;

import cn.zectec.contraceptive.management.system.manager.IMenuPermissionManager;
import cn.zectec.contraceptive.management.system.model.MenuPermission;
import cn.zectec.contraceptive.management.system.repository.IMenuPermissionRepository;

public class MenuPermissionManagerImpl extends SimpleBaseManagerImpl<MenuPermission> implements IMenuPermissionManager {

	public MenuPermissionManagerImpl(IMenuPermissionRepository baseRepository) {
		super(baseRepository);
		// TODO Auto-generated constructor stub
	}

}
