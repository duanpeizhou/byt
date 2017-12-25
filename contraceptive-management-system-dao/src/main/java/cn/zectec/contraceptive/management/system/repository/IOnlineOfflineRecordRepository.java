package cn.zectec.contraceptive.management.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.OnlineOfflineRecord;
import cn.zectec.contraceptive.management.system.repository.util.IBaseRepository;

public interface IOnlineOfflineRecordRepository extends IBaseRepository<OnlineOfflineRecord> {

	@Query("select osrc from OnlineOfflineRecord osrc where osrc.machineryEquipment=?1 and osrc.offlineDate is null order by osrc.onlineDate desc")
	public Page<OnlineOfflineRecord> findCurrentOnlineOffline(MachineryEquipment me,Pageable page);

	

}
