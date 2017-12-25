package cn.zectec.contraceptive.management.system.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.repository.util.IBaseRepository;

public interface IMachineryEquipmentRepository extends IBaseRepository<MachineryEquipment> {
	@Query("select count(*) from MachineryEquipment")
	public long getTatolMachineryEquipments();
	@Query("select count(*) from MachineryEquipment m where m.machineryEquipmentState.connectionState=1")
	public long getOnlineTatolMachineryEquipment();
	@Query("select count(*) from MachineryEquipment m where m.machineryEquipmentState.stockOut=1")
	public long getStockOutMachineryEquipment();
	
	@Query("select distinct m from MachineryEquipment m,IN(m.aisles) a where m.machineryEquipmentState.onlineDate is not null and  a.index_<=m.aislesNum and a.num<=?1 ")
	public Page<MachineryEquipment> findAilseAmountLT(int num,Pageable pageable);
	@Query("select distinct m from MachineryEquipment m,IN(m.aisles) a where m.machineryEquipmentState.onlineDate is not null and  a.index_<=m.aislesNum and a.num<=?1 and m.area.parentArea=?2")
	public Page<MachineryEquipment> findAilseAmountLTByTownshipStreet(int num,Area townshipStreet, Pageable pageable);
	@Query("select distinct m from MachineryEquipment m,IN(m.aisles) a where m.machineryEquipmentState.onlineDate is not null and  a.index_<=m.aislesNum and a.num<=?1 and m.area.parentArea.parentArea=?2")
	public Page<MachineryEquipment> findAilseAmountLTByCounty(int num,Area count, Pageable pageable);
	
}
