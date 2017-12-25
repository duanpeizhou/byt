package cn.zectec.contraceptive.management.system.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.IPermissionManager;
import cn.zectec.contraceptive.management.system.model.Permission;
import cn.zectec.contraceptive.management.system.repository.IPermissionRepository;

@Component
public class PermissionManagerImpl extends SimpleBaseManagerImpl<Permission> implements IPermissionManager {
	
	@Autowired
	public PermissionManagerImpl(IPermissionRepository baseRepository) {
		super(baseRepository);
		// TODO Auto-generated constructor stub
	}

}
