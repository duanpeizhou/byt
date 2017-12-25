package cn.zectec.contraceptive.management.system.manager.impl;

import cn.zectec.contraceptive.management.system.manager.IRecordManager;
import cn.zectec.contraceptive.management.system.model.Record;
import cn.zectec.contraceptive.management.system.repository.IRecordRepository;

public class RecordManagerImpl extends SimpleBaseManagerImpl<Record> implements IRecordManager {

	public RecordManagerImpl(IRecordRepository baseRepository) {
		super(baseRepository);
		// TODO Auto-generated constructor stub
	}

}
