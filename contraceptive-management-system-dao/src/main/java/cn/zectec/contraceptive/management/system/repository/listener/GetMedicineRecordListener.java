package cn.zectec.contraceptive.management.system.repository.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;

public class GetMedicineRecordListener {
	private static List<DataChangeHandler<GetMedicineRecord>> handlers = new ArrayList<DataChangeHandler<GetMedicineRecord>>();
	private static Logger logger = Logger.getLogger(GetMedicineRecordListener.class);

	public static void setHandlers(List<DataChangeHandler<GetMedicineRecord>> handers) {
		GetMedicineRecordListener.handlers = handers;
	}
	
	public GetMedicineRecordListener() {
		super();
		logger.debug("初始化GetMedicineRecordListener");
	}

	public static void registeHandler(DataChangeHandler<GetMedicineRecord> handler){
		if(handlers == null){
			handlers =  new ArrayList<DataChangeHandler<GetMedicineRecord>>();
		}
//		handlers.add(handler);
	}
	
	
	public void postSave(GetMedicineRecord getMedicineRecord){
//		logger.debug("当前的Handlers为"+handlers);
//		if(handlers == null){
//			return;
//		}
//		for(DataChangeHandler<GetMedicineRecord> handler : handlers){
//			try {
//				handler.doAfterSave(getMedicineRecord);
//			} catch (Exception e) {
//				logger.error("GetMedicineRecordListener.postSave()");
//			}
//		}
	}
}
