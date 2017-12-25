package cn.zectec.contraceptive.management.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import cn.zectec.contraceptive.management.system.model.CardReaderFailureRecord;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.repository.util.IBaseRepository;

public interface ICardReaderFailureRecordRepository extends IBaseRepository<CardReaderFailureRecord>{
	
	@Query("select osrc from CardReaderFailureRecord osrc where osrc.machineryEquipment=?1 and osrc.recoveryDate is null order by osrc.failureDate desc")
	public Page<CardReaderFailureRecord> findCurrentcardReaderFailureRecord(MachineryEquipment me,Pageable page);

}
