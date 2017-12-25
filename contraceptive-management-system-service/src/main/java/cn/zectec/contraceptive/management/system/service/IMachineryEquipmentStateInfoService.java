package cn.zectec.contraceptive.management.system.service;

import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateInfo;

public interface IMachineryEquipmentStateInfoService {
	/**
	 *删除设备的状态信息
	 *@param 设备状态的信息
	 */
	public void deleteMachineryEquipmentInfo(MachineryEquipmentStateInfo achineryEquipmentInfo);
}
