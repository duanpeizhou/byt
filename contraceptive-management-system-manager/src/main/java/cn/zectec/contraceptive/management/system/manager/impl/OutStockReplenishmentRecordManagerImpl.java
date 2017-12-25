package cn.zectec.contraceptive.management.system.manager.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.IOutStockReplenishmentRecordManager;
import cn.zectec.contraceptive.management.system.model.OutStockReplenishmentRecord;
import cn.zectec.contraceptive.management.system.repository.IOutStockReplenishmentRecordRepository;
@Component
public class OutStockReplenishmentRecordManagerImpl extends SimpleBaseManagerImpl<OutStockReplenishmentRecord> implements
		IOutStockReplenishmentRecordManager {
	private IOutStockReplenishmentRecordRepository outStockReplenishmentRecordRepository;
	@Autowired
	public OutStockReplenishmentRecordManagerImpl(IOutStockReplenishmentRecordRepository baseRepository) {
		super(baseRepository);
		this.outStockReplenishmentRecordRepository=baseRepository;
	}

}
