package cn.zectec.contraceptive.management.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import cn.zectec.contraceptive.management.system.model.Aisle;
import cn.zectec.contraceptive.management.system.model.AisleFailureRecord;
import cn.zectec.contraceptive.management.system.repository.util.IBaseRepository;

public interface IAisleFailureRecordRepository extends IBaseRepository<AisleFailureRecord>{

	@Query("select osrc from AisleFailureRecord osrc where osrc.aisle=?1 and osrc.recoveryDate is null order by osrc.failureDate desc")
	public Page<AisleFailureRecord> findCurrentoveraisleFailureRecord(Aisle aisle,Pageable page);

}
