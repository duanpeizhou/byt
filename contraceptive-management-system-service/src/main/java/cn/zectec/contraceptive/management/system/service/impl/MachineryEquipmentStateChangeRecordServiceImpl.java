package cn.zectec.contraceptive.management.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import cn.zectec.contraceptive.management.system.manager.IMachineryEquipmentStateChangeRecordManager;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentState;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateChangeRecord;
import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.BooleanOperator;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.Operator;
import cn.zectec.contraceptive.management.system.security.service.SecurityContext;
import cn.zectec.contraceptive.management.system.service.IMachineryEquipmentStateChangeRecordService;

@Service
public class MachineryEquipmentStateChangeRecordServiceImpl implements IMachineryEquipmentStateChangeRecordService{
	@Resource
	private IMachineryEquipmentStateChangeRecordManager machineryEquipmentStateChangeRecordManager;
	/**
	 * 分页查询设备状态改变的记录
	 * @param page 当前的页码
	 * @param pageSize 当前每页显示的条数
	 * @param direction 排序的方式
	 * @param sortName 排序的字段名
	 * @return 符合条件的状态改变的记录
	 */
	@Override
	public Page<MachineryEquipmentStateChangeRecord> getAllME(int page,	int pageSize,Direction direction,String sortName) {
		Order order = new Order(direction,sortName);
		Sort sort = new Sort(order);
		return machineryEquipmentStateChangeRecordManager.findBySearchFilters(null, page, pageSize,sort);
	}
	/**
	 * @method 根据各级地点id以及统计的时间来查询出设备状态改变的信息
	 * @param page当前页
	 * @param pageSize每页显示的数据条数
	 * @param direction 排序的方式
	 * @param sortName 排序的字段
	 * @param cityId  市区id
	 * @param countryId  乡镇id
	 * @param townshipStreetId  城镇街道id
	 * @param communityId  社区id
	 * @param startTime 查询开始的时间
	 * @param endTime 查询结束的时间
	 * @param  distributionPoints 发放点
	 * @return 返回所有的符合条件设备状态改变的记录
	 */
	@Override
	public Page<MachineryEquipmentStateChangeRecord> getAllMachineryEquipmentStateChangeRecord(
			int page, int pageSize,Direction direction,String sortName, String cityId, String countryId,
			String townshipStreetId,String communityId,String distributionPoints) {
		List<SearchFilter> filters=new ArrayList<SearchFilter>();
		if(!"-1".equals(distributionPoints)){
			SearchFilter filterdis=new SearchFilter("machineryEquipment.no",Operator.LIKE,distributionPoints);
			SearchFilter filter6=new SearchFilter("machineryEquipment.distributionPoints",Operator.LIKE,distributionPoints,BooleanOperator.OR);
			filters.add(filterdis);
			filters.add(filter6);
		}
		if(!("-1".equals(cityId))){
			SearchFilter filter=new SearchFilter("machineryEquipment.area.parentArea.parentArea.parentArea.id",cityId);
			filters.add(filter);
		}
		if(!("-1".equals(countryId))){
			SearchFilter searchFilter3=new SearchFilter("machineryEquipment.area.parentArea.parentArea.id",countryId);
			filters.add(searchFilter3);
		}
		if(!("-1".equals(townshipStreetId))){
			SearchFilter searchFilter4=new SearchFilter("machineryEquipment.area.parentArea.id",townshipStreetId);
			filters.add(searchFilter4);
		}
		if(!("-1".equals(communityId))){
			SearchFilter searchFilter4=new SearchFilter("machineryEquipment.area.id",communityId);
			filters.add(searchFilter4);
		}
		Order order=new Order(direction,sortName);
		Sort sort=new Sort(order);
		if(filters.isEmpty()){
			return machineryEquipmentStateChangeRecordManager.findBySearchFilters(null, page, pageSize,sort);
		}else{
			return machineryEquipmentStateChangeRecordManager.findBySearchFilters(filters, page, pageSize,sort);
		}
		
	}
	/**
	 * @method 根据各级地点id以及统计的时间来查询出缺货设备信息
	 * @param page当前页
	 * @param pageSize每页显示的数据条数
	 * @param direction 排序的方式
	 * @param sortName 排序的字段
	 * @param cityId  市区id
	 * @param countryId  乡镇id
	 * @param townshipStreetId  城镇街道id
	 * @param communityId  社区id
	 * @param startTime 查询开始的时间
	 * @param endTime 查询结束的时间
	 * @param  distributionPoints 发放点
	 * @return 返回所有的符合条件缺货设备信息
	 */
	@Override
	public Page<MachineryEquipmentStateChangeRecord> getStockoutReplenishRecord(int page, int pageSize, Direction direction, String sortName) {
		List<SearchFilter> filters = new ArrayList<SearchFilter>();
		filters.add(new SearchFilter("state",Operator.EQ,MachineryEquipmentState.OutStock,BooleanOperator.OR));
		filters.add(new SearchFilter("state",Operator.EQ,MachineryEquipmentState.Replenishment,BooleanOperator.OR));
		filters.add(new SearchFilter("machineryEquipment.machineryEquipmentState.stockOut",true));
		if(sortName != ""){
			if("address".equals(sortName)){
				Order order1 =new Order(direction,"machineryEquipment.area.parentArea.parentArea.parentArea.name");
				Order order2 =new Order(direction,"machineryEquipment.area.parentArea.parentArea.name");
				Order order3 =new Order(direction,"machineryEquipment.area.parentArea.name");
				Order order4 =new Order(direction,"machineryEquipment.area.name");
				Sort sort=new Sort(order1,order2,order3,order4);
				return machineryEquipmentStateChangeRecordManager.findBySearchFilters(filters, page, pageSize,sort);
			}else{
				Order order=new Order(direction,sortName);
				Sort sort=new Sort(order);
				return machineryEquipmentStateChangeRecordManager.findBySearchFilters(filters, page, pageSize,sort);
			}
		}
		return machineryEquipmentStateChangeRecordManager.findBySearchFilters(filters, page, pageSize);
	}

}
