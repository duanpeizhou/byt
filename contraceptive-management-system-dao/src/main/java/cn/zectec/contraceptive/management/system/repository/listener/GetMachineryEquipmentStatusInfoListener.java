package cn.zectec.contraceptive.management.system.repository.listener;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateChangeRecord;

public class GetMachineryEquipmentStatusInfoListener {
	private static List<DataChangeHandler<MachineryEquipmentStateChangeRecord>> handlers = new ArrayList<DataChangeHandler<MachineryEquipmentStateChangeRecord>>();
	private static Logger logger = Logger.getLogger(GetMedicineRecordListener.class);

	public static void setHandlers(List<DataChangeHandler<MachineryEquipmentStateChangeRecord>> handers) {
		GetMachineryEquipmentStatusInfoListener.handlers = handers;
	}
	
	public static void registeHandler(DataChangeHandler<MachineryEquipmentStateChangeRecord> handler){
		if(handlers == null){
			handlers =  new ArrayList<DataChangeHandler<MachineryEquipmentStateChangeRecord>>();
		}
		handlers.add(handler);
	}
	
	
	public void postSave(MachineryEquipmentStateChangeRecord getMedicineRecord){
		if(handlers == null){
			return;
		}
		for(DataChangeHandler<MachineryEquipmentStateChangeRecord> handler : handlers){
			try {
				handler.doAfterSave(getMedicineRecord);
			} catch (Exception e) {
				logger.error("GetMachineryEquipmentStatusInfoListener.postSave() 出错");
			}
		}
	}
}
