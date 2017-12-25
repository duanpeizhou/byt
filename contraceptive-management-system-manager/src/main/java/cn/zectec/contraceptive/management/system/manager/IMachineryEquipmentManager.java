package cn.zectec.contraceptive.management.system.manager;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;

public interface IMachineryEquipmentManager extends IBaseManager<MachineryEquipment, Long>{
	
	/**
	 * 更新设备
	 * @param machineryEquipment
	 * @return
	 */
	public boolean updateMachineryEquipment(MachineryEquipment machineryEquipment);
	
	public long getTatolMachineryEquipments();
	public long getOnlineTatolMachineryEquipment();
	public long getStockOutMachineryEquipment();

	public Page<MachineryEquipment> findAilseAmountLT(int num,int page,int pageSize,Direction direction,String sort);

	public long getTatolMachineryEquipmentsByTownshipStreet(Area townshipStreet);

	public long getTatolMachineryEquipmentsByCounty(Area county);

	public long getOnlineTatolMachineryEquipmentByTownshipStreet(
			Area townshipStreet,String stateName,boolean state);

	public long getOnlineTatolMachineryEquipmentByCounty(Area county,String stateName,boolean state);


	public Page<MachineryEquipment> findAilseAmountLTByTownshipStreet(int num,
			int page, int pageSize, Direction direction, String sort,
			Area townshipStreet);

	public Page<MachineryEquipment> findAilseAmountLTByCounty(int num,
			int page, int pageSize, Direction direction, String sort,
			Area count);
}
