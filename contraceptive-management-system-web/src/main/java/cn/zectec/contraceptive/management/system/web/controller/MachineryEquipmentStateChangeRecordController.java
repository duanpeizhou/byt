package cn.zectec.contraceptive.management.system.web.controller;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateChangeRecord;
import cn.zectec.contraceptive.management.system.service.IMachineryEquipmentStateChangeRecordService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilter;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilters;

@Controller
public class MachineryEquipmentStateChangeRecordController {
	@Resource
	private IMachineryEquipmentStateChangeRecordService machineryEquipmentStateChangeRecordService;
	/**
	 * 跳转到状态变化监控列表
	 * @return
	 */
	@RequestMapping(value="/machineryEquipmentStateChangeRecord")
	public String getMachineryEquipmentStateChangeRecord(){
		return "indexDatagrid/dynamicMonitoring/machineryEquipmentStateChangeRecord";
	}
	@JsonFilters({
		@JsonFilter(pojo=MachineryEquipmentStateChangeRecord.class,ignore="contraceptive"),
		@JsonFilter(pojo=MachineryEquipment.class,allow={"deviceNo","area","distributionPoints"}),
		@JsonFilter(pojo=Area.class,allow={"name","parentArea"})
	})
	@RequestMapping(value="/stateChangerRecordSearchArea")
	public Object getStateChangeRecord(@RequestParam(value="page",defaultValue="1")int page,
										@RequestParam(value="row",defaultValue="50")int pageSize,
										@RequestParam(value="order",defaultValue="-1")String direStr,
										@RequestParam(value="sort",defaultValue="-1")String sortName,
										@RequestParam(value="cityId",defaultValue="-1")String cityId,
										@RequestParam(value="countryId",defaultValue="-1")String countryId,
										@RequestParam(value="townshipStreetId",defaultValue="-1")String townshipStreetId,
										@RequestParam(value="communityId",defaultValue="-1")String communityId,
										@RequestParam(value="distributionPoints",defaultValue="-1")String distributionPoints){
		Direction direction=null;
		if(direStr.equals("-1")){
			sortName="hanpenDate";
			direction=Direction.DESC;
		}else if("asc".equals(direStr)){
			direction=Direction.ASC;
		}else{
			direction=Direction.DESC;
		}
		Page<MachineryEquipmentStateChangeRecord> pageME=machineryEquipmentStateChangeRecordService.getAllMachineryEquipmentStateChangeRecord(page-1, pageSize,direction,sortName, cityId, countryId, townshipStreetId,communityId,distributionPoints);
		JqueryUiDatagardPageModel<MachineryEquipmentStateChangeRecord> datagrid=new JqueryUiDatagardPageModel<>(pageME.getTotalElements(), pageME.getContent());
		datagrid.setRows(pageME.getContent());
		return datagrid;
	}
	
	@RequestMapping("/stockoutReplenishMonitoringUI")
	public String stockoutReplenishMonitoringUI()
	{
		return "indexDatagrid/stockoutReplenishMonitoring";
	}
	
	@JsonFilters({
		@JsonFilter(pojo=MachineryEquipmentStateChangeRecord.class,ignore="contraceptive"),
		@JsonFilter(pojo=MachineryEquipment.class,allow={"deviceNo","area","distributionPoints"}),
		@JsonFilter(pojo=Area.class,allow={"name","parentArea"})
	})
	@RequestMapping(value="/stockoutReplenishMonitoring")
	public Object getStockoutReplenishMonitoring(@RequestParam(value="page",defaultValue="1")int page,
			@RequestParam(value="rows",defaultValue="25")int pageSize,
			@RequestParam(value="order",defaultValue="-1")String direStr,
			@RequestParam(value="sort",defaultValue="-1")String sortName){
		Direction direction=null;
		if(direStr.equals("-1")){
			sortName="hanpenDate";
			direction=Direction.DESC;
		}else if("asc".equals(direStr)){
			direction=Direction.ASC;
		}else{
			direction=Direction.DESC;
		}
		Page<MachineryEquipmentStateChangeRecord> outStockReplenishpage=machineryEquipmentStateChangeRecordService.getStockoutReplenishRecord(page-1, pageSize,direction,sortName);
		JqueryUiDatagardPageModel<MachineryEquipmentStateChangeRecord> datagrid=new JqueryUiDatagardPageModel<MachineryEquipmentStateChangeRecord>(outStockReplenishpage.getTotalElements(), outStockReplenishpage.getContent());
		return datagrid;
	}
	
	
}
