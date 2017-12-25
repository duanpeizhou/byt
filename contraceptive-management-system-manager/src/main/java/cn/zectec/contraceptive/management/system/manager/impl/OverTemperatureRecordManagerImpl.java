package cn.zectec.contraceptive.management.system.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.IOverTemperatureRecordManager;
import cn.zectec.contraceptive.management.system.model.OverTemperatureRecord;
import cn.zectec.contraceptive.management.system.repository.IOverTemperatureRecordRepository;
@Component
public class OverTemperatureRecordManagerImpl extends SimpleBaseManagerImpl<OverTemperatureRecord> implements
		IOverTemperatureRecordManager {
	
	private IOverTemperatureRecordRepository overTemperatureRecordRepository;
	@Autowired
	public OverTemperatureRecordManagerImpl(IOverTemperatureRecordRepository baseRepository) {
		super(baseRepository);
		this.overTemperatureRecordRepository=baseRepository;
	}

}
