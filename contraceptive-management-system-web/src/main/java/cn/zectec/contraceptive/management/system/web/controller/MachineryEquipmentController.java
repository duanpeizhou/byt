package cn.zectec.contraceptive.management.system.web.controller;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zectec.contraceptive.management.system.model.Aisle;
import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateInfo;
import cn.zectec.contraceptive.management.system.service.IGetMedicineRecordService;
import cn.zectec.contraceptive.management.system.service.IMachineryEquipmentService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilter;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilters;
/**
 * 发放机设备
 * @author Administrator
 *
 */
@Controller
public class MachineryEquipmentController {

	@Autowired
	private IMachineryEquipmentService machineryEquipmentService;
	@Autowired
	private IGetMedicineRecordService getMedicineRecordService;
	@RequestMapping(value="/machineryEquipmentui")
	public String machineryEquipmentUI()
	{
		return "indexDatagrid/machineryEquipmentList";
	}
	

	@RequestMapping(value="/machineryEquipmenttoAdd")
	public String machineryEquipmenttoAdd(){
		
		return "indexDatagrid/MachineryEquipmentAdd";
	}
	/**
	 * 添加设备
	 * @param machineryEquipment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/machineryEquipmentAdd")
	public boolean machineryEquipmentAdd(MachineryEquipment machineryEquipment){
		try {
			machineryEquipmentService.add(machineryEquipment);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	@ResponseBody
	@RequestMapping(value="/updateMachineryEquipment")
	public boolean  updateMachineryEquipment(MachineryEquipment machineryEquipment){
		return machineryEquipmentService.updateMachineryEquipment(machineryEquipment);
	}
	
	

	
	/**
	 * 分页查新所有的设备
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/allmachineryequipment")
	@JsonFilters({
		@JsonFilter(pojo=MachineryEquipment.class,ignore={"records"}),
		@JsonFilter(pojo=MachineryEquipmentStateInfo.class,ignore={"machineryEquipment"}),
		@JsonFilter(pojo=Area.class,ignore={"childAreas"}),
		@JsonFilter(pojo=Aisle.class,ignore={"machineryEquipment"})
	})
	public Object getAllMachineryEquipment(@RequestParam(value = "page", defaultValue = "1") int page,@RequestParam(value = "rows", defaultValue = "20") int pageSize)
	{
		Page<MachineryEquipment> machineryEquipments = machineryEquipmentService.getAllMachineryEquipment(page-1, pageSize);
		JqueryUiDatagardPageModel<MachineryEquipment> maDatagard = new JqueryUiDatagardPageModel<>(machineryEquipments.getTotalElements(), machineryEquipments.getContent());
		return maDatagard;
	}
	/**
	 * 分页查新所有的设备
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/specifiedMachineryequipment")
	@JsonFilters({
		@JsonFilter(pojo=MachineryEquipment.class,ignore={"records"}),
		@JsonFilter(pojo=MachineryEquipmentStateInfo.class,ignore={"machineryEquipment"}),
		@JsonFilter(pojo=Area.class,ignore={"childAreas"}),
		@JsonFilter(pojo=Aisle.class,ignore={"machineryEquipment"})
	})
	public Object specifiedMachineryequipment(@RequestParam(value = "page", defaultValue = "1") int page,@RequestParam(value = "rows", defaultValue = "20") int pageSize,
			@RequestParam("countryId")String countryId,@RequestParam("townshipStreetId")String townshipStreetId,
			@RequestParam("communityId")String communityId,@RequestParam(value = "distributionPoints",defaultValue = "-1")String distributionPoints,
			@RequestParam(value="order",defaultValue="-1")String direStr,
			@RequestParam(value="sort",defaultValue="-1")String sortName)
	{
		Direction direction=null;
		if(direStr.equals("-1")){
			sortName="distributionPoints";
			direction=Direction.DESC;
		}else if("asc".equals(direStr)){
			direction=Direction.ASC;
		}else{
			direction=Direction.DESC;
		}
		
		Page<MachineryEquipment> machineryEquipments =
				machineryEquipmentService.getSpecifiedMachineryEquipments(page-1, pageSize,direction,sortName, countryId, townshipStreetId, communityId, distributionPoints);
		JqueryUiDatagardPageModel<MachineryEquipment> maDatagard = new JqueryUiDatagardPageModel<>(machineryEquipments.getTotalElements(), machineryEquipments.getContent());
		return maDatagard;
	}
	
	@RequestMapping(value="/deletemachineryequipment")
	@ResponseBody
	public boolean deleteMachineryEquipment(@RequestParam(value = "id") long id)
	{
		try {
			machineryEquipmentService.deleteMachineryEquipment(id);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 设备统计
	 */
	@ResponseBody
	@RequestMapping(value="/chartData")
	public String chartData(){
		long totalMEs=machineryEquipmentService.getTatolMachineryEquipments();
		long onlineMEs=machineryEquipmentService.getOnlineTatolMachineryEquipment();
		long stockOutMEs=machineryEquipmentService.getStockOutMachineryEquipment();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DATE, 1);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		long todayAmount=getMedicineRecordService.getCounts(c.getTime(), new Date());
		long curMonthAmount=getMedicineRecordService.getCounts(date.getTime(), new Date());
//		List<GetMedicineRecord> records= getMedicineRecordService.getMedicineRecordsByGetTime(d, new Date());
//		List<GetMedicineRecord> records1= getMedicineRecordService.getMedicineRecordsByGetTime(date, d);
//		for(GetMedicineRecord r:records){
//			todayAmount+=r.getAmount();
//		}
//		for(GetMedicineRecord r:records1){
//			curMonthAmount+=r.getAmount();
//		}
//		curMonthAmount+=todayAmount;
//		
		return totalMEs+";"+onlineMEs+";"+(totalMEs-onlineMEs)+";"+(totalMEs-stockOutMEs)+";"+stockOutMEs+";"+todayAmount+";"+curMonthAmount;
	}
	@RequestMapping(value="/alarmInfo")
	public Object AlarmInfo(){
		return "";
	}
	
	@JsonFilters({
		@JsonFilter(pojo=MachineryEquipment.class,ignore={"records"}),
		@JsonFilter(pojo=MachineryEquipmentStateInfo.class,ignore={"machineryEquipment"}),
		@JsonFilter(pojo=Area.class,ignore={"childAreas"}),
		@JsonFilter(pojo=Aisle.class,ignore={"machineryEquipment"})
	})
	@RequestMapping("/allstockout")
	public Object getStockout5Search(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "20") int pageSize,
			@RequestParam(value="sort",defaultValue="deviceNo")String sort,
			@RequestParam(value="order",defaultValue="DESC")String order
			)
	{
		Direction direction=null;
		if(order.equals("-1")){
			order="deviceNo";
			direction=Direction.DESC;
		}else if("asc".equals(order)){
			direction=Direction.ASC;
		}else{
			direction=Direction.DESC;
		}
		Page<MachineryEquipment> machineryEquipments =machineryEquipmentService.getStockout5Search(page-1, pageSize,direction,sort);
		JqueryUiDatagardPageModel<MachineryEquipment> maDatagard = new JqueryUiDatagardPageModel<MachineryEquipment>(machineryEquipments.getTotalElements(), machineryEquipments.getContent());
		return maDatagard;
	}
	
}
