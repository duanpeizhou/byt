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

import cn.zectec.contraceptive.management.system.model.Aisle;
import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.Contraceptive;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.service.IAisleService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;
import cn.zectec.contraceptive.management.system.utils.ZIPUtil;
import cn.zectec.contraceptive.management.system.utils.export.ExcelExport;
import cn.zectec.contraceptive.management.system.web.Constant;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilter;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilters;
import cn.zectec.contraceptive.management.system.web.util.ExportUtil;

@Controller
public class AisleController {
	@Resource
	private IAisleService aisleService;
	/**
	 * 跳转到缺货设备监控列表
	 * @return
	 */
	@RequestMapping(value="/stockoutAisles")
	public String getStockoutAisles(){
		return "indexDatagrid/StockoutAisles";
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/stockoutAislesSearchArea/export")
	public void stockoutAisleExport(
			@RequestParam(value="order",defaultValue="-1")final String direStr,
			@RequestParam(value="sort",defaultValue="-1")final String sortName,
			@RequestParam(value="cityId",defaultValue="-1")final String cityId,
			@RequestParam(value="countryId",defaultValue="-1")final String countryId,
			@RequestParam(value="townshipStreetId",defaultValue="-1")final String townshipStreetId,
			@RequestParam(value="communityId",defaultValue="-1")final String communityId,
			@RequestParam(value="distributionPoints",defaultValue="-1")final String distributionPoints,
			HttpServletResponse response
			)
	{
		try {
			JqueryUiDatagardPageModel<Aisle> datagrid = (JqueryUiDatagardPageModel<Aisle>)getStockoutAisles(1,1,direStr,sortName,cityId,countryId,townshipStreetId,communityId,distributionPoints);
			int count = (int) (datagrid.getTotal()/Constant.MAX_ITEM);
			List<FutureTask<byte[]>> futureTasks = new ArrayList<FutureTask<byte[]>>();
			for(int i=0;i<=count;i++){
				final int page = i+1;
				FutureTask<byte[]> f = new FutureTask<>(new Callable<byte[]>() {
					@Override
					public byte[] call() throws Exception {
						ExcelExport<Aisle> export = ExportUtil.exportstockoutAisle();
						JqueryUiDatagardPageModel<Aisle> datagrid1 = (JqueryUiDatagardPageModel<Aisle>)getStockoutAisles(page,Constant.MAX_ITEM,direStr,sortName,cityId,countryId,townshipStreetId,communityId,distributionPoints);
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
				map.put("缺货设备监控"+(i++)+".xls", data);
			}
			byte[] datas = ZIPUtil.packageFiles(map);
			String fileName = "缺货设备监控.zip";;
		
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
	 * 缺货设备条件查询
	 */
	@JsonFilters({
		@JsonFilter(pojo=Aisle.class,allow={"stockoutDate","machineryEquipment","contraceptive"}),
		@JsonFilter(pojo=MachineryEquipment.class,allow={"area","deviceNo","terminalType","distributionPoints"}),
		@JsonFilter(pojo=Contraceptive.class,allow={"name"}),
		@JsonFilter(pojo=Area.class,allow={"name","parentArea"})
		
	})
	@RequestMapping(value="/stockoutAislesSearchArea")
	public Object getStockoutAisles(@RequestParam(value="page",defaultValue="1")int page,
									@RequestParam(value="rows",defaultValue="20")int pageSize,
									@RequestParam(value="order",defaultValue="-1")String direStr,
									@RequestParam(value="sort",defaultValue="-1")String sortName,
									@RequestParam(value="cityId",defaultValue="-1")String cityId,
									@RequestParam(value="countryId",defaultValue="-1")String countryId,
									@RequestParam(value="townshipStreetId",defaultValue="-1")String townshipStreetId,
									@RequestParam(value="communityId",defaultValue="-1")String communityId,
									@RequestParam(value="distributionPoints",defaultValue="-1")String distributionPoints){
		Direction direction=null;
		if(direStr.equals("-1")){
			sortName="stockoutDate";
			direction=Direction.DESC;
		}else if("asc".equals(direStr)){
			direction=Direction.ASC;
		}else{
			direction=Direction.DESC;
		}
		Page<Aisle> pageAisle=aisleService.getSpecifiedAisles(page-1, pageSize,direction,sortName, "stockout", true, cityId, countryId, townshipStreetId,communityId,distributionPoints);
		JqueryUiDatagardPageModel<Aisle> datagridAisle=new  JqueryUiDatagardPageModel<Aisle>(pageAisle.getTotalElements(), pageAisle.getContent());
		datagridAisle.setRows(pageAisle.getContent());
 		return datagridAisle;
	}
	/**
	 * 跳转到货道故障监控列表
	 * @return
	 */
	@RequestMapping(value="/aisleFailureAisles")
	public String getAisleFailureAisles(){
		return "indexDatagrid/AisleFailureAisles";
	}
	/**
	 * 货道故障设备条件查询
	 */
	@JsonFilters({
		@JsonFilter(pojo=Aisle.class,allow={"num","aisleFailureDate","machineryEquipment"}),
		@JsonFilter(pojo=MachineryEquipment.class,allow={"area","distributionPoints","deviceNo"}),
		@JsonFilter(pojo=Area.class,allow={"name","parentArea"})
	})
	@RequestMapping(value="/failureAislesSearchArea")
	public Object getAisleFailureAisles(@RequestParam(value="page",defaultValue="1")int page,
										@RequestParam(value="rows")int pageSize,
										@RequestParam(value="order",defaultValue="-1")String direStr,
										@RequestParam(value="sort",defaultValue="-1")String sortName,
										@RequestParam(value="cityId",defaultValue="-1")String cityId,
										@RequestParam(value="countryId",defaultValue="-1")String countryId,
										@RequestParam(value="townshipStreetId",defaultValue="-1")String townshipStreetId,
										@RequestParam(value="communityId",defaultValue="-1")String communityId,
										@RequestParam(value="distributionPoints",defaultValue="-1")String distributionPoints){
		Direction direction=null;
		if(direStr.equals("-1")){
			sortName="stockoutDate";
			direction=Direction.DESC;
		}else if("asc".equals(direStr)){
			direction=Direction.ASC;
		}else{
			direction=Direction.DESC;
		}
		Page<Aisle> pageAisle=aisleService.getSpecifiedAisles(page-1, pageSize,direction,sortName, "aisleFailure", true, cityId, countryId, townshipStreetId,communityId,distributionPoints);
		JqueryUiDatagardPageModel<Aisle> datagridAisle=new  JqueryUiDatagardPageModel<>(pageAisle.getTotalElements(), pageAisle.getContent());
		datagridAisle.setRows(pageAisle.getContent());
		return datagridAisle;
	}
}
