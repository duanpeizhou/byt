package cn.zectec.contraceptive.management.system.web.controller;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.zectec.contraceptive.management.system.model.Aisle;
import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateInfo;
import cn.zectec.contraceptive.management.system.service.IMachineryEquipmentService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;
import cn.zectec.contraceptive.management.system.utils.ZIPUtil;
import cn.zectec.contraceptive.management.system.utils.export.ExcelExport;
import cn.zectec.contraceptive.management.system.web.Constant;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilter;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilters;
import cn.zectec.contraceptive.management.system.web.util.ExportUtil;

@Controller
public class MachineryEquipmentStateController {
	@Autowired
	private IMachineryEquipmentService machineryEquipmentService;
	
	/**
	 * 跳转到在线设备列表
	 * @return
	 */
	@RequestMapping(value="/onlineMachineryEquipment")
	public String getStateME(){
		return "indexDatagrid/OnlineMachineryEquipment";
	}
	/**
	 * 在线设备条件查询
	 */
	@JsonFilters({
		@JsonFilter(pojo=MachineryEquipment.class,allow={"deviceNo","distributionPoints","area","machineryEquipmentState","aisles","aislesNum"}),
		@JsonFilter(pojo=Area.class,allow={"name","parentArea"}),
		@JsonFilter(pojo=Aisle.class,allow={"num","index_"}),
		@JsonFilter(pojo=MachineryEquipmentStateInfo.class,allow={"signalStrength","deviceTemperature","onlineDate"})
	})
	@RequestMapping(value="/onlineSearchArea")
	public Object get(@RequestParam(value="page",defaultValue="1")int page,
			@RequestParam(value="rows",defaultValue="25")int pageSize,
			@RequestParam(value="order",defaultValue="-1")String direStr,
			@RequestParam(value="sort",defaultValue="-1")String sortName,
			@RequestParam(value="cityId",defaultValue="-1")String cityId,
			@RequestParam(value="countryId",defaultValue="-1")String countryId,
			@RequestParam(value="townshipStreetId",defaultValue="-1")String townshipStreetId,
			@RequestParam(value="communityId",defaultValue="-1")String communityId,
			@RequestParam(value="distributionPoints",defaultValue="-1")String distributionPoints){
		Direction direction=null;
		if(direStr.equals("-1")){
			sortName="machineryEquipmentState.onlineDate";
			direction=Direction.DESC;
		}else if("asc".equals(direStr)){
			direction=Direction.ASC;
		}else{
			direction=Direction.DESC;
		}
		Page<MachineryEquipment> pageME=machineryEquipmentService.getSpecifiedMachineryEquipments(page-1, pageSize,direction,sortName, "machineryEquipmentState.connectionState", true, cityId, countryId, townshipStreetId,communityId,distributionPoints);
		JqueryUiDatagardPageModel<MachineryEquipment> datagrid=new JqueryUiDatagardPageModel<MachineryEquipment>(pageME.getTotalElements(), pageME.getContent());
		datagrid.setRows(pageME.getContent());
		return datagrid;
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getOnlinExcel/export")
	public void exportOutStockReplenishmentRecord(
			@RequestParam(value="order",defaultValue="-1")final String direStr,
			@RequestParam(value="sort",defaultValue="-1")final String sortName,
			@RequestParam(value="cityId",defaultValue="-1")final String cityId,
			@RequestParam(value="countryId",defaultValue="-1")final String countryId,
			@RequestParam(value="townshipStreetId",defaultValue="-1")final String townshipStreetId,
			@RequestParam(value="communityId",defaultValue="-1")final String communityId,
			@RequestParam(value="distributionPoints",defaultValue="-1")final String distributionPoints,
			HttpServletResponse response)
	{
		try {
			final JqueryUiDatagardPageModel<MachineryEquipment> datagrid = (JqueryUiDatagardPageModel<MachineryEquipment>) get(1,1,direStr,sortName,cityId,countryId,townshipStreetId,communityId,distributionPoints);
			int count = (int) (datagrid.getTotal()/Constant.MAX_ITEM);
			List<FutureTask<byte[]>> futureTasks = new ArrayList<FutureTask<byte[]>>();
			for(int i=0;i<=count;i++){
				final int page = i+1;
				FutureTask<byte[]> f = new FutureTask<>(new Callable<byte[]>() {
					@Override
					public byte[] call() throws Exception {
						ExcelExport<MachineryEquipment> export = ExportUtil.exportOnlineMachineryEquipment();
						JqueryUiDatagardPageModel<MachineryEquipment> datagrid1 = (JqueryUiDatagardPageModel<MachineryEquipment>)get(page,Constant.MAX_ITEM,direStr,sortName,cityId,countryId,townshipStreetId,communityId,distributionPoints);
						export.setContent(datagrid1.getRows());
						byte [] data = export.export();
						return data;
					}
					
				});
				futureTasks.add(f);
				Constant.THREAD_POLL.execute(f);
			}
			Map<String, byte[]> map = new HashMap<String, byte[]>();
			int i = 1;
			for(FutureTask<byte[]> f : futureTasks){
				byte[] data = f.get();
				map.put("在线设备监控"+(i++)+".xls", data);
			}
			byte[] datas = ZIPUtil.packageFiles(map);
			String fileName = "在线设备监控.zip";
		
			fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
			response.reset();  
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");  
			response.addHeader("Content-Length", "" + datas.length);  
			response.setContentType("application/octet-stream"); 
			response.setCharacterEncoding("GB2312");
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());  
			outputStream.write(datas);  
			outputStream.flush();  
			outputStream.close();  
		} catch (Exception e) {
			throw new RuntimeException(e);
		}  
		
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getOfflineExcel/export")
	public void exportOfflineRecord(
			@RequestParam(value="order",defaultValue="-1")final String direStr,
			@RequestParam(value="sort",defaultValue="-1")final String sortName,
			@RequestParam(value="cityId",defaultValue="-1")final String cityId,
			@RequestParam(value="countryId",defaultValue="-1")final String countryId,
			@RequestParam(value="townshipStreetId",defaultValue="-1")final String townshipStreetId,
			@RequestParam(value="communityId",defaultValue="-1")final String communityId,
			@RequestParam(value="distributionPoints",defaultValue="-1")final String distributionPoints,
			HttpServletResponse response)
	{
		try {
			
			JqueryUiDatagardPageModel<MachineryEquipment> datagrid = (JqueryUiDatagardPageModel<MachineryEquipment>) getOffline(1,1,direStr,sortName,cityId,countryId,townshipStreetId,communityId,distributionPoints);
			int count = (int) (datagrid.getTotal()/Constant.MAX_ITEM);
			List<FutureTask<byte[]>> futureTasks = new ArrayList<FutureTask<byte[]>>();
			for(int i=0;i<=count;i++){
				final int page = i+1;
				FutureTask<byte[]> f = new FutureTask<>(new Callable<byte[]>() {
					@Override
					public byte[] call() throws Exception {
						ExcelExport<MachineryEquipment> export = ExportUtil.exportOfflineMachineryEquipment();
						JqueryUiDatagardPageModel<MachineryEquipment> datagrid1 = (JqueryUiDatagardPageModel<MachineryEquipment>)getOffline(page,Constant.MAX_ITEM,direStr,sortName,cityId,countryId,townshipStreetId,communityId,distributionPoints);
						export.setContent(datagrid1.getRows());
						byte [] data = export.export();
						return data;
					}
					
				});
				futureTasks.add(f);
				Constant.THREAD_POLL.execute(f);
			}
			Map<String, byte[]> map = new HashMap<String, byte[]>();
			int i = 1;
			for(FutureTask<byte[]> f : futureTasks){
				byte[] data = f.get();
				map.put("离线设备监控"+(i++)+".xls", data);
			}
			byte[] datas = ZIPUtil.packageFiles(map);
		
			String fileName = "离线设备监控.zip";;
			fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
			response.reset();  
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");  
			response.addHeader("Content-Length", "" + datas.length);  
			response.setContentType("application/octet-stream"); 
			response.setCharacterEncoding("GB2312");
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());  
			outputStream.write(datas);  
			outputStream.flush();  
			outputStream.close();  
		} catch (Exception e) {
			throw new RuntimeException(e);
		}  
		
	}
	
	/**
	 * 跳转到离线设备列表
	 * @return
	 */
	@RequestMapping(value="/offlineMachineryEquipment")
	public String offline(){
		return "indexDatagrid/OfflineMachineryEquipment";
	}
	/**
	 * 离线设备条件查询
	 */
	@JsonFilters({
		@JsonFilter(pojo=MachineryEquipment.class,allow={"deviceNo","distributionPoints","area","machineryEquipmentState","aisles","aislesNum"}),
		@JsonFilter(pojo=Area.class,allow={"name","parentArea"}),
		@JsonFilter(pojo=Aisle.class,allow={"num","index_"}),
		@JsonFilter(pojo=MachineryEquipmentStateInfo.class,allow={"offlineDate"})
	})
	@RequestMapping(value="/offlineSearchArea")
	public Object getOffline(@RequestParam(value="page",defaultValue="1")int page,
			@RequestParam(value="rows",defaultValue="10")int pageSize,
			@RequestParam(value="order",defaultValue="-1")String direStr,
			@RequestParam(value="sort",defaultValue="-1")String sortName,
			@RequestParam(value="cityId",defaultValue="-1")String cityId,
			@RequestParam(value="countryId",defaultValue="-1")String countryId,
			@RequestParam(value="townshipStreetId",defaultValue="-1")String townshipStreetId,
			@RequestParam(value="communityId",defaultValue="-1")String communityId,
			@RequestParam(value="distributionPoints",defaultValue="-1")String distributionPoints){
		Direction direction=null;
		if(direStr.equals("-1")){
			sortName="machineryEquipmentState.OfflineDate";
			direction=Direction.DESC;
		}else if("asc".equals(direStr)){
			direction=Direction.ASC;
		}else{
			direction=Direction.DESC;
		}
		Page<MachineryEquipment> pageME=machineryEquipmentService.getSpecifiedMachineryEquipments(page-1, pageSize,direction,sortName, "machineryEquipmentState.connectionState", false, cityId, countryId, townshipStreetId,communityId,distributionPoints);
		JqueryUiDatagardPageModel<MachineryEquipment> datagrid=new JqueryUiDatagardPageModel<MachineryEquipment>(pageME.getTotalElements(), pageME.getContent());
		datagrid.setRows(pageME.getContent());
		return datagrid;
	}
	
	/**
	 * 跳转到开门设备
	 * @return
	 */
	@RequestMapping("/openMachineryEquipment")
	public String getOpenMachineryEquipment(){
		return "indexDatagrid/dynamicMonitoring/openMachineryEquipment";
	}
	/**
	 * 查询开门指定地点的设备
	 * @return
	 */
	@JsonFilters({
		@JsonFilter(pojo=MachineryEquipment.class,allow={"deviceNo","distributionPoints","area","machineryEquipmentState"}),
		@JsonFilter(pojo=Area.class,allow={"name","parentArea"}),
		@JsonFilter(pojo=MachineryEquipmentStateInfo.class,allow={"doorDate"})
	})
	@RequestMapping(value="/openSearchArea")
	public Object getOpen(@RequestParam(value="page",defaultValue="1")int page,
			@RequestParam(value="rows",defaultValue="25")int pageSize,
			@RequestParam(value="order",defaultValue="-1")String direStr,
			@RequestParam(value="sort",defaultValue="-1")String sortName,
			@RequestParam(value="cityId",defaultValue="-1")String cityId,
			@RequestParam(value="countryId",defaultValue="-1")String countryId,
			@RequestParam(value="townshipStreetId",defaultValue="-1")String townshipStreetId,
			@RequestParam(value="communityId",defaultValue="-1")String communityId,
			@RequestParam(value="distributionPoints",defaultValue="-1")String distributionPoints){
		Direction direction=null;
		if(direStr.equals("-1")){
			sortName="machineryEquipmentState.doorDate";
			direction=Direction.DESC;
		}else if("asc".equals(direStr)){
			direction=Direction.ASC;
		}else{
			direction=Direction.DESC;
		}
		Page<MachineryEquipment> pageME=machineryEquipmentService.getSpecifiedMachineryEquipments(page-1, pageSize,direction,sortName, "machineryEquipmentState.doorState", true, cityId, countryId, townshipStreetId,communityId,distributionPoints);
		JqueryUiDatagardPageModel<MachineryEquipment> datagrid=new JqueryUiDatagardPageModel<MachineryEquipment>(pageME.getTotalElements(), pageME.getContent());
		datagrid.setRows(pageME.getContent());
		return datagrid;
	}
	/**
	 * 跳转到超温设备列表
	 * @return
	 */
	@RequestMapping(value="/overTemperatureMachineryEquipment")
	public String getOverMachineryEquipment(){
		return "indexDatagrid/dynamicMonitoring/overTemperatureMachineryEquipment";
	}
	@JsonFilters({
		@JsonFilter(pojo=MachineryEquipment.class,allow={"deviceNo","distributionPoints","area","machineryEquipmentState"}),
		@JsonFilter(pojo=Area.class,allow={"name","parentArea"}),
		@JsonFilter(pojo=MachineryEquipmentStateInfo.class,allow={"overTemperatureDate","deviceTemperature"})
	})
	@RequestMapping(value="/overTemperatureSearchArea")
	public Object getOverTemperature(@RequestParam(value="page",defaultValue="1")int page,
			@RequestParam(value="rows",defaultValue="25")int pageSize,
			@RequestParam(value="order",defaultValue="-1")String direStr,
			@RequestParam(value="sort",defaultValue="-1")String sortName,
			@RequestParam(value="cityId",defaultValue="-1")String cityId,
			@RequestParam(value="countryId",defaultValue="-1")String countryId,
			@RequestParam(value="townshipStreetId",defaultValue="-1")String townshipStreetId,
			@RequestParam(value="communityId",defaultValue="-1")String communityId,
			@RequestParam(value="distributionPoints",defaultValue="-1")String distributionPoints){
		Direction direction=null;
		if(direStr.equals("-1")){
			sortName="machineryEquipmentState.overTemperatureDate";
			direction=Direction.DESC;
		}else if("asc".equals(direStr)){
			direction=Direction.ASC;
		}else{
			direction=Direction.DESC;
		}
		Page<MachineryEquipment> pageME=machineryEquipmentService.getSpecifiedMachineryEquipments(page-1, pageSize,direction,sortName, "machineryEquipmentState.overTemperature", true, cityId, countryId, townshipStreetId,communityId,distributionPoints);
		JqueryUiDatagardPageModel<MachineryEquipment> datagrid=new JqueryUiDatagardPageModel<MachineryEquipment>(pageME.getTotalElements(), pageME.getContent());
		datagrid.setRows(pageME.getContent());
		return datagrid;
	}
	/**
	 * 跳转到读卡器设备列表
	 * @return
	 */
	@RequestMapping(value="/cardReaderFailureMachineryEquipment")
	public String getcardReaderFailure(){
		return "indexDatagrid/dynamicMonitoring/cardReaderFailureMachineryEquipment";
	}
	@JsonFilters({
		@JsonFilter(pojo=MachineryEquipment.class,allow={"deviceNo","distributionPoints","area","machineryEquipmentState"}),
		@JsonFilter(pojo=Area.class,allow={"name","parentArea"}),
		@JsonFilter(pojo=MachineryEquipmentStateInfo.class,allow={"cardReaderFailureDate"})
	})
	@RequestMapping(value="/cardReaderFailureSearchArea")
	public Object getcardReaderFailure(@RequestParam(value="page",defaultValue="1")int page,
			@RequestParam(value="rows",defaultValue="25")int pageSize,
			@RequestParam(value="order",defaultValue="-1")String direStr,
			@RequestParam(value="sort",defaultValue="-1")String sortName,
			@RequestParam(value="cityId",defaultValue="-1")String cityId,
			@RequestParam(value="countryId",defaultValue="-1")String countryId,
			@RequestParam(value="townshipStreetId",defaultValue="-1")String townshipStreetId,
			@RequestParam(value="communityId",defaultValue="-1")String communityId,
			@RequestParam(value="distributionPoints",defaultValue="-1")String distributionPoints){
		Direction direction=null;
		if(direStr.equals("-1")){
			sortName="machineryEquipmentState.cardReaderFailureDate";
			direction=Direction.DESC;
		}else if("asc".equals(direStr)){
			direction=Direction.ASC;
		}else{
			direction=Direction.DESC;
		}
		Page<MachineryEquipment> pageME=machineryEquipmentService.getSpecifiedMachineryEquipments(page-1, pageSize,direction,sortName, "machineryEquipmentState.cardReaderFailure", true, cityId, countryId, townshipStreetId,communityId,distributionPoints);
		JqueryUiDatagardPageModel<MachineryEquipment> datagrid=new JqueryUiDatagardPageModel<MachineryEquipment>(pageME.getTotalElements(), pageME.getContent());
		datagrid.setRows(pageME.getContent());
		return datagrid;
	}
	/**
	 * 跳转到离线3天设备列表
	 * @return
	 */
	@RequestMapping(value="/offline3MachineryEquipment")
	public String offline3(){
		return "indexDatagrid/offline3MachineryEquipment";
	}
	/**
	 * 离线3天设备条件查询
	 */
	@JsonFilters({
		@JsonFilter(pojo=MachineryEquipment.class,allow={"deviceNo","distributionPoints","area","machineryEquipmentState","aisles","aislesNum"}),
		@JsonFilter(pojo=Area.class,allow={"name","parentArea"}),
		@JsonFilter(pojo=Aisle.class,allow={"num","index_"}),
		@JsonFilter(pojo=MachineryEquipmentStateInfo.class,allow={"offlineDate"})
	})
	@RequestMapping(value="/offline3SearchArea")
	public Object getOffline3(@RequestParam(value="page",defaultValue="1")int page,
			@RequestParam(value="rows",defaultValue="10")int pageSize,
			@RequestParam(value="order",defaultValue="-1")String direStr,
			@RequestParam(value="sort",defaultValue="-1")String sortName,
			@RequestParam(value="cityId",defaultValue="-1")String cityId,
			@RequestParam(value="countryId",defaultValue="-1")String countryId,
			@RequestParam(value="townshipStreetId",defaultValue="-1")String townshipStreetId,
			@RequestParam(value="distributionPoints",defaultValue="-1")String distributionPoints,
			@RequestParam(value="communityId",defaultValue="-1")String communityId){
		Direction direction=null;
		if(direStr.equals("-1")){
			sortName="machineryEquipmentState.OfflineDate";
			direction=Direction.DESC;
		}else if("asc".equals(direStr)){
			direction=Direction.ASC;
		}else{
			direction=Direction.DESC;
		}
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -3);
		Page<MachineryEquipment> pageME=machineryEquipmentService.getOfflin3MachineryEquipments(page-1, pageSize,direction,sortName, "machineryEquipmentState.connectionState", false, cityId, countryId, townshipStreetId,communityId,distributionPoints,calendar.getTime());
		JqueryUiDatagardPageModel<MachineryEquipment> datagrid=new JqueryUiDatagardPageModel<MachineryEquipment>(pageME.getTotalElements(), pageME.getContent());
		datagrid.setRows(pageME.getContent());
		return datagrid;
	}
}
