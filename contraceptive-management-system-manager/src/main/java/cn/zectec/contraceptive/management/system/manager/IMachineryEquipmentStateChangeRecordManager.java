package cn.zectec.contraceptive.management.system.manager;



import cn.zectec.contraceptive.management.system.model.Aisle;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateChangeRecord;

public interface IMachineryEquipmentStateChangeRecordManager extends IBaseManager<MachineryEquipmentStateChangeRecord, Long>{

	public void addStockout(Aisle aisle, int number, boolean stockout);

	public void addReplenishment(Aisle aisle, int i, boolean stockout);

	public void addOnlineOfflineRecord(MachineryEquipment me, boolean b);

	public void addDoorStateChangeRecord(MachineryEquipment me, boolean doorState);

	public void addOverTemporatureRecord(MachineryEquipment me, int temporature,boolean state);

	public void addAisleFailureRecord(Aisle aisle, boolean b);

	public void addCardReaderFailureRecord(MachineryEquipment me, boolean cardReader);
}
