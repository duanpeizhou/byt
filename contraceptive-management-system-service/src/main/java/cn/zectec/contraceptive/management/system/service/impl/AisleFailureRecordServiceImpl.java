package cn.zectec.contraceptive.management.system.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import cn.zectec.contraceptive.management.system.manager.IAisleFailureRecordManage;
import cn.zectec.contraceptive.management.system.model.AisleFailureRecord;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.BooleanOperator;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.Operator;
import cn.zectec.contraceptive.management.system.service.IAisleFailureRecordService;
@Service
public class AisleFailureRecordServiceImpl implements IAisleFailureRecordService {
	@Resource
	private IAisleFailureRecordManage aisleFailureRecordManage;
	/**
	 * @method 根据各项参数来分页查询货道故障的记录
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
	public Page<AisleFailureRecord> getSpecifiedFailureRecord(int page,
			int pageSize,Direction direction,String sortName, String cityId, String countryId,
			String townshipStreetId,String communityId,String distributionPoints,String startTime,String endTime) {
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
		SimpleDateFormat simple=new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!"-1".equals(startTime)){
			try {
				SearchFilter startTimeFilter=new SearchFilter("failureDate",simple.parse(startTime));
				filters.add(startTimeFilter);
			} catch (ParseException e) {
			}
		}
		if(!"-1".equals(endTime)){
			try {
				SearchFilter startTimeFilter=new SearchFilter("failureDate",Operator.LT,simple.parse(endTime));
				filters.add(startTimeFilter);
			} catch (ParseException e) {
			}
		}
		
		Order order=new Order(direction,sortName);
		Sort sort=new Sort(order);
		if(filters.isEmpty()){
			return aisleFailureRecordManage.findBySearchFilters(null, page, pageSize,sort);
		}else{
			return aisleFailureRecordManage.findBySearchFilters(filters, page, pageSize,sort);
		}
	}

}
