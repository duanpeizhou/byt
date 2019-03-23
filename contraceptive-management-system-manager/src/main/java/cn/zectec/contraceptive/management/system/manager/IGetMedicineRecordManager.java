package cn.zectec.contraceptive.management.system.manager;


import java.util.Date;
import java.util.List;

import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;

public interface IGetMedicineRecordManager extends IBaseManager<GetMedicineRecord, Long> {
	public long getCounts(Date startTime ,Date endTime,String areaName,Area area);

	public void updateSendStatus(boolean b, long id);
	
	public List<GetMedicineRecord> findNotSendRecords();
	public List<GetMedicineRecord> find10NotSendRecords();
}
