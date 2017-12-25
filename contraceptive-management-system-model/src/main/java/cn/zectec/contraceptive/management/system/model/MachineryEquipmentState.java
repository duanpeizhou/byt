package cn.zectec.contraceptive.management.system.model;

public enum MachineryEquipmentState {
	/**
	 *  上线 
	 */
	Online,
	/**
	 * 离线
	 */
	OffineLine,
	/**
	 * 开门
	 */
	OpenDoor,
	/**
	 * 关门
	 */
	CloseDoor,
	/**
	 * 超温
	 */
	Overtemperature,
	/**
	 * 温度恢复
	 */
	TemperatureRecovery,
	/**
	 * 缺货
	 */
	OutStock,
	/**
	 * 补货
	 */
	Replenishment,
	/**
	 * 货道故障
	 */
	AisleFailure,
	/**
	 * 货道故障恢复
	 */
	AisleFailureRecovery,
	/**
	 * 读卡器故障
	 */
	CardReaderFailure,
	/**
	 * 读卡器故障恢复
	 */
	CardReaderFailureRecovery
}
