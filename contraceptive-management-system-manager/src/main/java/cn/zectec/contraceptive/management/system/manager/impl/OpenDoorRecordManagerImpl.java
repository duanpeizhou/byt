package cn.zectec.contraceptive.management.system.manager.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.IOpenDoorRecordManager;
import cn.zectec.contraceptive.management.system.model.OpenDoorRecord;
import cn.zectec.contraceptive.management.system.repository.IOpenDoorRecordRepository;
@Component
public class OpenDoorRecordManagerImpl extends SimpleBaseManagerImpl<OpenDoorRecord> implements IOpenDoorRecordManager {
	private IOpenDoorRecordRepository openDoorRecordRepository;
	@Autowired
	public OpenDoorRecordManagerImpl(IOpenDoorRecordRepository baseRepository) {
		super(baseRepository);
		this.openDoorRecordRepository=baseRepository;
	}

}
