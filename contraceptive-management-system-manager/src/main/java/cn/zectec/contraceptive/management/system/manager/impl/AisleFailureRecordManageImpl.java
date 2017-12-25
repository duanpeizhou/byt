package cn.zectec.contraceptive.management.system.manager.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.IAisleFailureRecordManage;
import cn.zectec.contraceptive.management.system.model.AisleFailureRecord;
import cn.zectec.contraceptive.management.system.repository.IAisleFailureRecordRepository;

@Component
public class AisleFailureRecordManageImpl  extends SimpleBaseManagerImpl<AisleFailureRecord> implements IAisleFailureRecordManage {
	private IAisleFailureRecordRepository aisleFailureRecordRepository;
	
	@Autowired
	public AisleFailureRecordManageImpl(IAisleFailureRecordRepository baseRepository) {
		super(baseRepository);
		this.aisleFailureRecordRepository = baseRepository;
	}

	

}
