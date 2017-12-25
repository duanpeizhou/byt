package cn.zectec.contraceptive.management.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import cn.zectec.contraceptive.management.system.model.Aisle;
import cn.zectec.contraceptive.management.system.model.OutStockReplenishmentRecord;
import cn.zectec.contraceptive.management.system.repository.util.IBaseRepository;

public interface IOutStockReplenishmentRecordRepository extends IBaseRepository<OutStockReplenishmentRecord> {

	@Query("select osrc from OutStockReplenishmentRecord osrc where osrc.aisle=?1 and osrc.replenishmentDate is null order by osrc.outStockDate desc")
	public Page<OutStockReplenishmentRecord> findCurrentOutStockRecored(Aisle aisle,Pageable page);

}
