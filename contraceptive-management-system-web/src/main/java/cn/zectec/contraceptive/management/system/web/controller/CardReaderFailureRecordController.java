package cn.zectec.contraceptive.management.system.web.controller;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.CardReaderFailureRecord;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.service.ICardReaderFailureRecordService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilter;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilters;

@Controller
public class CardReaderFailureRecordController {
	@Resource
	private ICardReaderFailureRecordService cardReaderFailureRecordService;
	@RequestMapping(value="/cardReaderFailureRecord")
	public String getCardReaderFailureRecord(){
		return "indexDatagrid/CardReaderFailureRecord";
	}
	@JsonFilters({
		@JsonFilter(pojo=MachineryEquipment.class,allow={"deviceNo","distributionPoints","terminalType","area"}),
		@JsonFilter(pojo=Area.class,allow={"name","parentArea"})
	})
	@RequestMapping(value="/getCardReaderFailureRecordSearchArea")
	public Object getAisleFaultRecord(@RequestParam(value="page",defaultValue="1")int page,
			@RequestParam(value="rows",defaultValue="10")int pageSize,
			@RequestParam(value="order",defaultValue="-1")String direStr,
			@RequestParam(value="sort",defaultValue="-1")String sortName,
			@RequestParam(value="cityId",defaultValue="-1")String cityId,
			@RequestParam(value="countryId",defaultValue="-1")String countryId,
			@RequestParam(value="townshipStreetId",defaultValue="-1")String townshipStreetId,
			@RequestParam(value="communityId",defaultValue="-1")String communityId,
			@RequestParam(value="distributionPoints",defaultValue="-1")String distributionPoints,
			@RequestParam(value="startTime",defaultValue="-1")String startTime,
			@RequestParam(value="endTime",defaultValue="-1")String endTime){
		Direction direction=null;
		if(direStr.equals("-1")){
			sortName="failureDate";
			direction=Direction.DESC;
		}else if("asc".equals(direStr)){
			direction=Direction.ASC;
		}else{
			direction=Direction.DESC;
		}
		Page<CardReaderFailureRecord> pageOOR=cardReaderFailureRecordService.getCardReaderFailureRecord(page-1, pageSize,direction,sortName,  cityId, countryId, townshipStreetId,communityId,distributionPoints,startTime,endTime);
		JqueryUiDatagardPageModel<CardReaderFailureRecord> datagrid=new JqueryUiDatagardPageModel<CardReaderFailureRecord>(pageOOR.getTotalElements(), pageOOR.getContent());
		datagrid.setRows(pageOOR.getContent());
		return datagrid;
	}
}
