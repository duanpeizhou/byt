package cn.zectec.contraceptive.management.system.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.IMachineryEquipmentManager;
import cn.zectec.contraceptive.management.system.model.Aisle;
import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.repository.IContraceptiveRepository;
import cn.zectec.contraceptive.management.system.repository.IMachineryEquipmentRepository;
import cn.zectec.contraceptive.management.system.repository.IRecordRepository;
import cn.zectec.contraceptive.management.system.repository.util.DynamicSpecifications;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;

@Component
public class MachineryEquipmentManagerImpl extends SimpleBaseManagerImpl<MachineryEquipment> implements
		IMachineryEquipmentManager {
	private IMachineryEquipmentRepository machineryEquipmentRepository;
	@Resource
	private IRecordRepository recordRepository;
	@Resource
	private IContraceptiveRepository contraceptiveRepository;
	@Autowired
	public MachineryEquipmentManagerImpl(IMachineryEquipmentRepository baseRepository) {
		super(baseRepository);
		this.machineryEquipmentRepository = baseRepository;
	}
	
	

	@Override
	public boolean updateMachineryEquipment(MachineryEquipment machineryEquipment) {
		MachineryEquipment m = null;
		try{
			m=this.findOne(machineryEquipment.getId());
			m.setAdminPassword(machineryEquipment.getAdminPassword());
			m.setAislesNum(machineryEquipment.getAislesNum());
			List<Aisle> a=machineryEquipment.getAisles();
			for(int i=0;i<a.size();i++){
				m.getAisles().get(i).setContraceptive(contraceptiveRepository.findOne(a.get(i).getContraceptive().getId()));
			}
			m.setLatitude(machineryEquipment.getLatitude());
			m.setLongitude(machineryEquipment.getLongitude());
			m.setAlias(machineryEquipment.getAlias());
			m.setArea(machineryEquipment.getArea());
			m.setBuiltinPhone(machineryEquipment.getBuiltinPhone());
			m.setDeviceNo(machineryEquipment.getDeviceNo());
			m.setDistributionPoints(machineryEquipment.getDistributionPoints());
			m.setRemark(machineryEquipment.getRemark());
			m.setTerminalType(machineryEquipment.getTerminalType());
			machineryEquipmentRepository.save(m);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}



	@Override
	public long getTatolMachineryEquipments() {
		return machineryEquipmentRepository.getTatolMachineryEquipments();
	}



	@Override
	public long getOnlineTatolMachineryEquipment() {
		return machineryEquipmentRepository.getOnlineTatolMachineryEquipment();
	}



	@Override
	public long getStockOutMachineryEquipment() {
		return machineryEquipmentRepository.getStockOutMachineryEquipment();
	}



	@Override
	public Page<MachineryEquipment> findAilseAmountLT(int num,int page,int pageSize,Direction direction,String sort) {
		Sort sort_ = null;
		if(sort != null)
		{
			if("address".equals(sort))
			{
				Order order1 =new Order(direction,"area.parentArea.parentArea.parentArea.name");
				Order order2 =new Order(direction,"area.parentArea.parentArea.name");
				Order order3 =new Order(direction,"area.parentArea.name");
				Order order4 =new Order(direction,"area.name");
				sort_=new Sort(order1,order2,order3,order4);
			}
			else
			{
				Order order=new Order(direction,sort);
				sort_=new Sort(order);
			}
		}
		return machineryEquipmentRepository.findAilseAmountLT(num,new PageRequest(page, pageSize,sort_));
	}



	@Override
	public long getTatolMachineryEquipmentsByTownshipStreet(Area townshipStreet) {
		SearchFilter s = new SearchFilter("area.parentArea",townshipStreet );
		Specification<MachineryEquipment> spec = DynamicSpecifications.bySearchFilter(s);
		return machineryEquipmentRepository.count(spec);
	}



	@Override
	public long getTatolMachineryEquipmentsByCounty(Area county) {
		SearchFilter s = new SearchFilter("area.parentArea.parentArea",county);
		Specification<MachineryEquipment> spec = DynamicSpecifications.bySearchFilter(s);
		return machineryEquipmentRepository.count(spec);
	}



	@Override
	public long getOnlineTatolMachineryEquipmentByTownshipStreet(
			Area townshipStreet,String stateName,boolean state) {
		List<SearchFilter> filters=new ArrayList<>();
		SearchFilter s = new SearchFilter("area.parentArea",townshipStreet );
		SearchFilter filter=new SearchFilter(stateName,state);
		filters.add(filter);
		filters.add(s);
		Specification<MachineryEquipment> spec = DynamicSpecifications.bySearchFilters(filters);
		return machineryEquipmentRepository.count(spec);
	}



	@Override
	public long getOnlineTatolMachineryEquipmentByCounty(Area county,String stateName,boolean state) {
		List<SearchFilter> filters=new ArrayList<>();
		SearchFilter s = new SearchFilter("area.parentArea.parentArea",county);
		SearchFilter filter=new SearchFilter(stateName,state);
		filters.add(filter);
		filters.add(s);
		Specification<MachineryEquipment> spec = DynamicSpecifications.bySearchFilters(filters);
		return machineryEquipmentRepository.count(spec);
	}



	@Override
	public Page<MachineryEquipment> findAilseAmountLTByTownshipStreet(int num,
			int page, int pageSize, Direction direction, String sort,
			Area townshipStreet) {
		Sort sort_ = null;
		if(sort != null)
		{
			if("address".equals(sort))
			{
				Order order1 =new Order(direction,"area.parentArea.parentArea.parentArea.name");
				Order order2 =new Order(direction,"area.parentArea.parentArea.name");
				Order order3 =new Order(direction,"area.parentArea.name");
				Order order4 =new Order(direction,"area.name");
				sort_=new Sort(order1,order2,order3,order4);
			}
			else
			{
				Order order=new Order(direction,sort);
				sort_=new Sort(order);
			}
		}
		return machineryEquipmentRepository.findAilseAmountLTByTownshipStreet(num,townshipStreet,new PageRequest(page, pageSize,sort_));
	}



	@Override
	public Page<MachineryEquipment> findAilseAmountLTByCounty(int num,
			int page, int pageSize, Direction direction, String sort,
			Area count) {
		Sort sort_ = null;
		if(sort != null)
		{
			if("address".equals(sort))
			{
				Order order1 =new Order(direction,"area.parentArea.parentArea.parentArea.name");
				Order order2 =new Order(direction,"area.parentArea.parentArea.name");
				Order order3 =new Order(direction,"area.parentArea.name");
				Order order4 =new Order(direction,"area.name");
				sort_=new Sort(order1,order2,order3,order4);
			}
			else
			{
				Order order=new Order(direction,sort);
				sort_=new Sort(order);
			}
		}
		return machineryEquipmentRepository.findAilseAmountLTByCounty(num,count,new PageRequest(page, pageSize,sort_));
	}


}
