package cn.zectec.contraceptive.management.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import cn.zectec.contraceptive.management.system.manager.IAisleManager;
import cn.zectec.contraceptive.management.system.model.Aisle;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.BooleanOperator;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.Operator;
import cn.zectec.contraceptive.management.system.service.IAisleService;
@Service
public class AisleServiceImpl implements IAisleService {
	@Resource
	private IAisleManager aisleManager;
	/**
	 * @method 根据各项参数来分页查询货道
	 * @param page当前页,
	 * @param pageSize每页显示的数据条数,
	 * @param direction 排序的方式，
	 * @param sortName 排序的字段, 
	* @param cityId  市区id
	 * @param countryId  乡镇id
	 * @param townshipStreetId  城镇街道id
	 * @param communityId  社区id
	 * @param distributionPoints 发放点,
	 * @param startTime 查询开始的时间,
	 * @param endTime 查询结束的时间
	 * @return 符合条件的货道记录
	 */
	@Override
	public Page<Aisle> getSpecifiedAisles(int page, int pageSize,Direction directionName,String orderName,
			String stateName, boolean state, String cityId, String countryId,
			String townshipStreetId,String communityId,String distributionPoints) {
		
		List<SearchFilter> filters=new ArrayList<SearchFilter>();
		if(!"-1".equals(distributionPoints)){
			SearchFilter filterdis=new SearchFilter("machineryEquipment.no",Operator.LIKE,distributionPoints);
			SearchFilter filter6=new SearchFilter("machineryEquipment.distributionPoints",Operator.LIKE,distributionPoints,BooleanOperator.OR);
			filters.add(filterdis);
			filters.add(filter6);
		}
		if(!("-1").equals(cityId)){
			 SearchFilter filter=new SearchFilter("machineryEquipment.area.parentArea.parentArea.parentArea.id",cityId);
			 filters.add(filter);
		}
		if(!("-1").equals(countryId)){
			 SearchFilter filter2=new SearchFilter("machineryEquipment.area.parentArea.parentArea.id",countryId);
			 filters.add(filter2);
		}
		if(!("-1").equals(townshipStreetId)){
			 SearchFilter filter3=new SearchFilter("machineryEquipment.area.parentArea.id",townshipStreetId);
			 filters.add(filter3);
		}
		if(!("-1").equals(communityId)){
			 SearchFilter filter5=new SearchFilter("machineryEquipment.area.id",communityId);
			 filters.add(filter5);
		}
		
		SearchFilter filter4=new SearchFilter(stateName,state);
		filters.add(filter4);
		Order order=new Order(directionName, orderName);
		Sort sort=new Sort(order);
		return aisleManager.findBySearchFilters(filters, page, pageSize,sort);
		
	}

	
}
