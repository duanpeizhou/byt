package cn.zectec.contraceptive.management.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.OpenDoorRecord;
import cn.zectec.contraceptive.management.system.repository.util.IBaseRepository;

public interface IOpenDoorRecordRepository extends IBaseRepository<OpenDoorRecord> {
	
	@Query("select osrc from OpenDoorRecord osrc where osrc.machineryEquipment=?1 and osrc.closeDoorDate is null order by osrc.openDoorDate desc")
	public Page<OpenDoorRecord> findCurrentOopenDoorRecord(MachineryEquipment me,Pageable page);

}
