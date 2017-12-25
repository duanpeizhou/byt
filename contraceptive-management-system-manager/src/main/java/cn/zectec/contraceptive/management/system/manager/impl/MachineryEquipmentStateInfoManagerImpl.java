package cn.zectec.contraceptive.management.system.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.IMachineryEquipmentStateInfoManager;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateInfo;
import cn.zectec.contraceptive.management.system.repository.IMachineryEquipmentStateInfoRepository;
@Component
public class MachineryEquipmentStateInfoManagerImpl extends SimpleBaseManagerImpl<MachineryEquipmentStateInfo> implements
		IMachineryEquipmentStateInfoManager {
	@Autowired
	public MachineryEquipmentStateInfoManagerImpl(IMachineryEquipmentStateInfoRepository baseRepository) {
		super(baseRepository);
	}

}
