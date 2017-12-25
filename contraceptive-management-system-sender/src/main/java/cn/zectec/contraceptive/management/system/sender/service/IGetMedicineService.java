package cn.zectec.contraceptive.management.system.sender.service;

import java.util.List;

import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;

public interface IGetMedicineService {
	
	public List<GetMedicineRecord> findNotSentRecords();
	
	public List<GetMedicineRecord> find10NotSentRecords();
	
	public boolean saveGetMedicineRecords(List<GetMedicineRecord> records);

}
