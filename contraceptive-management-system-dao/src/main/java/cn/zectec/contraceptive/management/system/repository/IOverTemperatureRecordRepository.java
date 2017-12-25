package cn.zectec.contraceptive.management.system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.OverTemperatureRecord;
import cn.zectec.contraceptive.management.system.repository.util.IBaseRepository;

public interface IOverTemperatureRecordRepository extends IBaseRepository<OverTemperatureRecord> {
	
	@Query("select osrc from OverTemperatureRecord osrc where osrc.machineryEquipment=?1 and osrc.recoveryDate is null order by osrc.overTemperatureDate desc")
	public Page<OverTemperatureRecord> findCurrentoverTemperatureRecord(MachineryEquipment me,Pageable page);

}
