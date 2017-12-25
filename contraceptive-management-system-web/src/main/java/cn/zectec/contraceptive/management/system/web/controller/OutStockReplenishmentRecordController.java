package cn.zectec.contraceptive.management.system.web.controller;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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

import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.Contraceptive;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.OutStockReplenishmentRecord;
import cn.zectec.contraceptive.management.system.service.IOutStockReplenishmentRecordService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;
import cn.zectec.contraceptive.management.system.utils.ZIPUtil;
import cn.zectec.contraceptive.management.system.utils.export.ExcelExport;
import cn.zectec.contraceptive.management.system.web.Constant;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilter;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilters;
import cn.zectec.contraceptive.management.system.web.util.ExportUtil;

@Controller
public class OutStockReplenishmentRecordController {
	@Resource
	private IOutStockReplenishmentRecordService outStockReplenishmentRecordService;

	@RequestMapping(value="/getOutStockReplenishmentRecord")
	public String getOutStockReplenishmentRecords(){
		return "indexDatagrid/OutStockReplenishmentRecord";
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getOutStockReplenishmentRecordsDatagrid/export")
	public void exportOutStockReplenishmentRecord(
			@RequestParam(value="order",defaultValue="-1")final String direStr,
			@RequestParam(value="sort",defaultValue="-1")final String sortName,
			@RequestParam(value="cityId",defaultValue="-1")final String cityId,
			@RequestParam(value="countryId",defaultValue="-1")final String countryId,
			@RequestParam(value="townshipStreetId",defaultValue="-1")final String townshipStreetId,
			@RequestParam(value="communityId",defaultValue="-1")final String communityId,
			@RequestParam(value="distributionPoints",defaultValue="-1")final String distributionPoints,
			@RequestParam(value="startTime",defaultValue="-1")final String startTime,
			@RequestParam(value="endTime",defaultValue="-1")final String endTime,
			HttpServletResponse response)
	{
		try {
			JqueryUiDatagardPageModel<OutStockReplenishmentRecord> datagrid = (JqueryUiDatagardPageModel<OutStockReplenishmentRecord>)getOutStockReplenishmentRecord(1,1,direStr,sortName,cityId,countryId,townshipStreetId,communityId,distributionPoints,startTime,endTime);

			int count = (int) (datagrid.getTotal()/Constant.MAX_ITEM);
			List<FutureTask<byte[]>> futureTasks = new ArrayList<FutureTask<byte[]>>();
			for(int i=0;i<=count;i++){
				final int page = i+1;
				FutureTask<byte[]> f = new FutureTask<>(new Callable<byte[]>() {
					@Override
					public byte[] call() throws Exception {
						ExcelExport<OutStockReplenishmentRecord> export = ExportUtil.exportOutStockReplenishmentRecord();
						JqueryUiDatagardPageModel<OutStockReplenishmentRecord> datagrid1 = (JqueryUiDatagardPageModel<OutStockReplenishmentRecord>)getOutStockReplenishmentRecord(page,Constant.MAX_ITEM,direStr,sortName,cityId,countryId,townshipStreetId,communityId,distributionPoints,startTime,endTime);
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
				map.put("缺货补货记录"+(i++)+".xls", data);
			}
			byte[] datas = ZIPUtil.packageFiles(map);
			
			String fileName = "缺货补货记录.zip";;
		
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
	 * 
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@JsonFilters({
		@JsonFilter(pojo=OutStockReplenishmentRecord.class,allow={"outStockDate","replenishmentDate","machineryEquipment","contraceptive"}),
		@JsonFilter(pojo=MachineryEquipment.class,allow={"deviceNo","distributionPoints","terminalType","area"}),
		@JsonFilter(pojo=Contraceptive.class,allow={"name"}),
		@JsonFilter(pojo=Area.class,allow={"name","parentArea"})
	})
	@RequestMapping(value="/getOutStockReplenishmentRecordsDatagrid")
	public Object getOutStockReplenishmentRecord(@RequestParam(value="page",defaultValue="1")int page,
			@RequestParam(value="rows",defaultValue="25")int pageSize,
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
			sortName="outStockDate";
			direction=Direction.DESC;
		}else if("asc".equals(direStr)){
			direction=Direction.ASC;
		}else{
			direction=Direction.DESC;
		}
		Page<OutStockReplenishmentRecord> pageOOR=outStockReplenishmentRecordService.getOutStockReplenishmentRecord(page-1, pageSize,direction,sortName, cityId, countryId,
				townshipStreetId,communityId,distributionPoints,startTime,endTime);
		JqueryUiDatagardPageModel<OutStockReplenishmentRecord> datagrid=new JqueryUiDatagardPageModel<OutStockReplenishmentRecord>(pageOOR.getTotalElements(), pageOOR.getContent());
		datagrid.setRows(pageOOR.getContent());
		return datagrid;
	}
	
}
