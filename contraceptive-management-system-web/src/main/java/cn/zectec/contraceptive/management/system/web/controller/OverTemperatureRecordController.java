package cn.zectec.contraceptive.management.system.web.controller;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.OverTemperatureRecord;
import cn.zectec.contraceptive.management.system.service.IOverTemperatureRecordService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilter;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilters;

@Controller
public class OverTemperatureRecordController {
	@Resource
	private IOverTemperatureRecordService overTemperatureRecordService;
	@RequestMapping(value="/overTemperatureRecord")
	public String getOverTemperatureRecord(){
		return "indexDatagrid/OverTemperatureRecord";
	}	
	
	@JsonFilters({
		@JsonFilter(pojo=OverTemperatureRecord.class,allow={"machineryEquipment","overTemperatureDate","overTemperature","recoveryDate","recoveryTemperature"}),
		@JsonFilter(pojo=MachineryEquipment.class,allow={"deviceNo","distributionPoints","terminalType","area"}),
		@JsonFilter(pojo=Area.class,allow={"name","parentArea"})
	})
	@RequestMapping(value="/getOverTemperatureRecordSearchArea")
	public Object getOverTemperatureRecordSearchArea(@RequestParam(value="page",defaultValue="1")int page,
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
			sortName="overTemperatureDate";
			direction=Direction.DESC;
		}else if("asc".equals(direStr)){
			direction=Direction.ASC;
		}else{
			direction=Direction.DESC;
		}
		Page<OverTemperatureRecord> pageOOR=overTemperatureRecordService.getOverTemperatureRecord(page-1, pageSize,direction,sortName,  cityId, countryId, townshipStreetId,communityId,distributionPoints,startTime,endTime);
		JqueryUiDatagardPageModel<OverTemperatureRecord> datagrid=new JqueryUiDatagardPageModel<OverTemperatureRecord>(pageOOR.getTotalElements(), pageOOR.getContent());
		datagrid.setRows(pageOOR.getContent());
		return datagrid;
	}
	
	
}
