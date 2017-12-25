package cn.zectec.contraceptive.management.system.model;


/**
 * 记录类型
 * @author wxy
 *
 */
@SuppressWarnings("serial")
public class Record extends Base{
	/**
	 * 发放的设备
	 */
	private MachineryEquipment machineryEquipment;

	public MachineryEquipment getMachineryEquipment() {
		return machineryEquipment;
	}

	public void setMachineryEquipment(MachineryEquipment machineryEquipment) {
		this.machineryEquipment = machineryEquipment;
	}
	
	
}
