package cn.zectec.contraceptive.management.system.web.controller;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zectec.contraceptive.management.system.model.Aisle;
import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.Contraceptive;
import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.service.IGetMedicineRecordService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;
import cn.zectec.contraceptive.management.system.utils.ZIPUtil;
import cn.zectec.contraceptive.management.system.utils.export.ExcelExport;
import cn.zectec.contraceptive.management.system.web.Constant;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilter;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilters;
import cn.zectec.contraceptive.management.system.web.util.ExportUtil;

@Controller
public class GetMedicineRecordController {
	
	@Resource
	private IGetMedicineRecordService getMedicineRecordService;

	@RequestMapping(value="/stockoutmonitoringequipmentui")
	public String stockoutMonitoringEquipment()
	{
		return "indexDatagrid/stockoutMonitoringEquipment";
	}
	
	@RequestMapping(value="/getMedicineRecords")
	public String getEstablishingRecipientsRecords(){
		return "indexDatagrid/GetMedicineRecord";
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getMedicineRecordSearchArea/export")
	public void exportMedicineRecord(
			@RequestParam(value="order",defaultValue="-1")final String direStr,
			@RequestParam(value="sort",defaultValue="-1")final String sortName,
			@RequestParam(value="cityId",defaultValue="-1")final String cityId,
			@RequestParam(value="countryId",defaultValue="-1")final String countryId,
			@RequestParam(value="townshipStreetId",defaultValue="-1")final String townshipStreetId,
			@RequestParam(value="communityId",defaultValue="-1")final String communityId,
			@RequestParam(value="distributionPoints",defaultValue="-1")final String distributionPoints,
			@RequestParam(value="startTime",defaultValue="-1")final String startTime,
			@RequestParam(value="endTime",defaultValue="-1")final String endTime,
			HttpServletResponse response
			)
	{
		try {
			JqueryUiDatagardPageModel<GetMedicineRecord> datagrid = (JqueryUiDatagardPageModel<GetMedicineRecord>)getMedicineRecordSearchArea(1,1,direStr,sortName,cityId,countryId,townshipStreetId,communityId,distributionPoints,startTime,endTime);
			int count = (int) (datagrid.getTotal()/Constant.MAX_ITEM);
			List<FutureTask<byte[]>> futureTasks = new ArrayList<FutureTask<byte[]>>();
			for(int i=0;i<=count;i++){
				final int page = i+1;
				FutureTask<byte[]> f = new FutureTask<>(new Callable<byte[]>() {
					@Override
					public byte[] call() throws Exception {
						ExcelExport<GetMedicineRecord> export = ExportUtil.exportGetMedicineRecord();
						JqueryUiDatagardPageModel<GetMedicineRecord> datagrid1 = (JqueryUiDatagardPageModel<GetMedicineRecord>)getMedicineRecordSearchArea(page,Constant.MAX_ITEM,direStr,sortName,cityId,countryId,townshipStreetId,communityId,distributionPoints,startTime,endTime);
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
				map.put("药具领用记录"+(i++)+".xls", data);
			}
			byte[] datas = ZIPUtil.packageFiles(map);
			
			String fileName = "药具领用记录.zip";;
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
	
	@JsonFilters({
		@JsonFilter(pojo=MachineryEquipment.class,allow={"deviceNo","distributionPoints","area","aisles"}),
		@JsonFilter(pojo=Aisle.class,allow={"contraceptive"}),
		@JsonFilter(pojo=Contraceptive.class,allow={"name"}),
		@JsonFilter(pojo=Area.class,allow={"name","parentArea"})
	})
	@RequestMapping(value="/getMedicineRecordSearchArea")
	public Object getMedicineRecordSearchArea(@RequestParam(value="page",defaultValue="1")int page,
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
			sortName="addDate";
			direction=Direction.DESC;
		}else if("asc".equals(direStr)){
			direction=Direction.ASC;
		}else{
			direction=Direction.DESC;
		}
		SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate=null;
		Date endDate=null;
		if(startTime.length() == 19 ){
			try {
				startDate=simple.parse(startTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if(startTime.length() == 19 ){
			try {
				endDate=simple.parse(endTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Page<GetMedicineRecord> pageOOR=getMedicineRecordService.getMedicineRecords(page-1, pageSize,direction,sortName, startDate, endDate, cityId, countryId, townshipStreetId,communityId,distributionPoints);
		JqueryUiDatagardPageModel<GetMedicineRecord> datagrid=new JqueryUiDatagardPageModel<GetMedicineRecord>(pageOOR.getTotalElements(), pageOOR.getContent());
		datagrid.setRows(pageOOR.getContent());
		return datagrid;
	}
	
	@RequestMapping(value="/getMedicine")
	public String Medicine(){
		return "indexDatagrid/dynamicMonitoring/getMedicine";
	}
	@JsonFilters({
		@JsonFilter(pojo=MachineryEquipment.class,allow={"deviceNo","distributionPoints","area","aisles"}),
		@JsonFilter(pojo=Aisle.class,allow={"contraceptive"}),
		@JsonFilter(pojo=Contraceptive.class,allow={"name"}),
		@JsonFilter(pojo=Area.class,allow={"name","parentArea"})
	})
	@RequestMapping(value="/getMedicineSearchArea")
	public Object getMedicine(@RequestParam(value="page",defaultValue="1")int page,
			@RequestParam(value="rows",defaultValue="10")int pageSize,
			@RequestParam(value="order",defaultValue="-1")String direStr,
			@RequestParam(value="sort",defaultValue="-1")String sortName,
			@RequestParam(value="cityId",defaultValue="-1")String cityId,
			@RequestParam(value="countryId",defaultValue="-1")String countryId,
			@RequestParam(value="townshipStreetId",defaultValue="-1")String townshipStreetId,
			@RequestParam(value="communityId",defaultValue="-1")String communityId,
			@RequestParam(value="distributionPoints",defaultValue="-1")String distributionPoints){
		Date startTime=new Date();
		Date endTime=new Date();
		long time=endTime.getTime()-7*24*3600*1000;
		startTime.setTime(time);
		Direction direction=null;
		if(direStr.equals("-1")){
			sortName="addDate";
			direction=Direction.DESC;
		}else if("asc".equals(direStr)){
			direction=Direction.ASC;
		}else{
			direction=Direction.DESC;
		}
		Page<GetMedicineRecord> pageOOR=getMedicineRecordService.getMedicineRecords(page-1, pageSize,direction,sortName, startTime, endTime, cityId, countryId, townshipStreetId,communityId,distributionPoints);
		JqueryUiDatagardPageModel<GetMedicineRecord> datagrid=new JqueryUiDatagardPageModel<GetMedicineRecord>(pageOOR.getTotalElements(), pageOOR.getContent());
		datagrid.setRows(pageOOR.getContent());
		return datagrid;
	}
	@ResponseBody
	@RequestMapping("/testsave")
	public Object test(){
		//Nation n=new Nation("name",(byte)i);
		GetMedicineRecord g=new GetMedicineRecord();
		g.setAddDate(new Date());
		g.setAddress("qwer");
		g.setAge(45);
		g.setAmount(100);
		g.setSex("男");
		g.setBeginDate(new Date());
		g.setBillNumber(123456789l);
		g.setBirthDay(new Date());
		g.setCargoId("cargoid");
		g.setCargoRoadNo("cargoRoadNo");
		g.setCurrentConnectionState(true);
		g.setEndDate(new Date());
		g.setName("name");
		//g.setNation(n);
		g.setStationName("stationName");
		g.setIdNumber("idNumber");
		g.setGetMedicineDate(new Date());
		getMedicineRecordService.saveGetMedicineRecord(g);
		return "holle";
	}
}
