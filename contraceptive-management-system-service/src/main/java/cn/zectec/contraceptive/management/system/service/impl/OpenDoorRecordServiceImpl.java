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

import cn.zectec.contraceptive.management.system.manager.IOpenDoorRecordManager;
import cn.zectec.contraceptive.management.system.model.OpenDoorRecord;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.BooleanOperator;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.Operator;
import cn.zectec.contraceptive.management.system.service.IOpenDoorRecordService;
@Service
public class OpenDoorRecordServiceImpl implements IOpenDoorRecordService{
	@Resource
	private IOpenDoorRecordManager openDoorRecordManager;
	/**
	 * 描述：获得开门记录
	 * @author Administrator
	 * @param page 页数
	 * @param pageSize  每一页的数据条数
	 * @param direction 排序的方式
	 * @param sortName 排序的字段
	 * @param startTime 起始时间
	 * @param endTime 结束时间
	 * @param cityId 市区id
	 * @param countryId 市区id
	 * @param townshipStreetId 乡镇id
	 * @param communityId 社区id
	 * @param distributionPoints 发放点
	 * @return 开门记录
	 */
	@Override
	public Page<OpenDoorRecord> getOpenDoorRecord(int page, int pageSize,Direction direction,String sortName,
			String cityId, String countryId, String townshipStreetId,String communityId,String distributionPoints,String startTime,String endTime) {
		List<SearchFilter> seracherFilters=new ArrayList<SearchFilter>();
		if(!"-1".equals(distributionPoints)){
			SearchFilter filterdis=new SearchFilter("machineryEquipment.no",Operator.LIKE,distributionPoints);
			SearchFilter filter6=new SearchFilter("machineryEquipment.distributionPoints",Operator.LIKE,distributionPoints,BooleanOperator.OR);
			seracherFilters.add(filterdis);
			seracherFilters.add(filter6);
			
		}
		if(!("-1").equals(cityId)){
			SearchFilter searchFilter2=new SearchFilter("machineryEquipment.area.parentArea.parentArea.parentArea.id",cityId);
			seracherFilters.add(searchFilter2);
		}
		if(!("-1").equals(countryId)){
			SearchFilter searchFilter3=new SearchFilter("machineryEquipment.area.parentArea.parentArea.id",countryId);
			seracherFilters.add(searchFilter3);
		}
		if(!("-1").equals(townshipStreetId)){
			SearchFilter searchFilter4=new SearchFilter("machineryEquipment.area.parentArea.id",townshipStreetId);
			seracherFilters.add(searchFilter4);
		}
		if(!("-1").equals(communityId)){
			SearchFilter searchFilter7=new SearchFilter("machineryEquipment.area.id",communityId);
			seracherFilters.add(searchFilter7);
		}
		
		SimpleDateFormat simple=new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!"-1".equals(startTime)){
			try {
				SearchFilter startTimeFilter=new SearchFilter("openDoorDate",Operator.GT,simple.parse(startTime));
				seracherFilters.add(startTimeFilter);
			} catch (ParseException e) {
			}
		}
		if(!"-1".equals(endTime)){
			try {
				SearchFilter startTimeFilter=new SearchFilter("openDoorDate",Operator.LT,simple.parse(endTime));
				seracherFilters.add(startTimeFilter);
			} catch (ParseException e) {
			}
		}
		
		Order order=new Order(direction,sortName);
		Sort sort=new Sort(order);
		if(seracherFilters.isEmpty()){
			return openDoorRecordManager.findBySearchFilters(null, page, pageSize,sort);
		}else{
			return openDoorRecordManager.findBySearchFilters(seracherFilters, page, pageSize,sort);
		}
	}

}
