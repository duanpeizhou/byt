package cn.zectec.contraceptive.management.system.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zectec.contraceptive.management.system.manager.IMachineryEquipmentStateInfoManager;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateInfo;
import cn.zectec.contraceptive.management.system.service.IMachineryEquipmentStateInfoService;
@Service
public class MachineryEquipmentStateInfoServiceImpl implements IMachineryEquipmentStateInfoService{

	@Resource
	private IMachineryEquipmentStateInfoManager equipmentStateInfoManager;
	/**
	 *删除设备的状态信息
	 *@param achineryEquipmentInfo 设备状态的信息
	 */
	@Override
	public void deleteMachineryEquipmentInfo(MachineryEquipmentStateInfo achineryEquipmentInfo) {
		equipmentStateInfoManager.delete(achineryEquipmentInfo);
	}

}
