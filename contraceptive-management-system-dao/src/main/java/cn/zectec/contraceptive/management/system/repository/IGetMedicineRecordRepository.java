package cn.zectec.contraceptive.management.system.repository;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.repository.util.IBaseRepository;

public interface IGetMedicineRecordRepository extends IBaseRepository<GetMedicineRecord> {	
	
	@Query("select g from GetMedicineRecord g where g.sent = false")	
	public List<GetMedicineRecord> findNotSendRecords();
	
	@Query("select g from GetMedicineRecord g where g.sent = false")	
	public List<GetMedicineRecord> find10NotSendRecords(Pageable pageable);
	
	@Modifying
	@Query("update GetMedicineRecord g set g.sent = ?1 where g.id=?2")
	public void updateSendStatus(boolean send,long id);
	
}
