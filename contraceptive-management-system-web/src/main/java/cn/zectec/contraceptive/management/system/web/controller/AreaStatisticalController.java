package cn.zectec.contraceptive.management.system.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.AreaStatistical;
import cn.zectec.contraceptive.management.system.service.IAreaStatisticalService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilter;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilters;
@Controller
public class AreaStatisticalController {
	@Resource
	private IAreaStatisticalService areaStatisticalService;
	/**
	 * 跳转到区县统计页面
	 * @return
	 */
	@RequestMapping("/areaStatistical")
	public String get(){
		return "indexDatagrid/statisticalDistribution/areaStatistical";
	}
	/**
	 * 区县列表
	 * @return
	 */
	@JsonFilters({
		@JsonFilter(pojo=Area.class,allow="name")
	})
	@RequestMapping("/areaStatisticalList")
	public Object getAreaStatistical(@RequestParam(value="page",defaultValue="1")int page,
									@RequestParam(value="rows",defaultValue="25")int pageSize,
									@RequestParam(value="cityId",defaultValue="-1")String cityId,
									@RequestParam(value="countryId",defaultValue="-1")String countryId,
									@RequestParam(value="townshipStreetId",defaultValue="-1")String townshipStreetId,
									@RequestParam(value="communityId",defaultValue="-1")String communityId,
									@RequestParam(value="startTime",defaultValue="-1")String startTime,
									@RequestParam(value="endTime",defaultValue="-1")String endTime){
		Date end=null;
		Date start=null;
		if(!("-1").equals(startTime)){
			SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				end=simple.parse(endTime);
				start=simple.parse(startTime);
			} catch (ParseException e) {
			}
			
		}
		List<AreaStatistical> pageModle=areaStatisticalService.getSpecifiedAreaStatistical(cityId, countryId, townshipStreetId,communityId, start, end);
		JqueryUiDatagardPageModel<AreaStatistical> datagrid=new JqueryUiDatagardPageModel<>(pageModle.size(),pageModle);
		return datagrid;
	}
	/**
	 * 区县统计图
	 * @return
	 */
	@JsonFilters({
		@JsonFilter(pojo=AreaStatistical.class,allow={"total","area"}),
		@JsonFilter(pojo=Area.class,allow="name")
	})
	@RequestMapping("/areaStatisticalColumn3D")
	public Object getAreaStatistical(@RequestParam(value="page",defaultValue="1")int page,
									@RequestParam(value="rows",defaultValue="300")int pageSize
									){
		List<AreaStatistical> pageModle=areaStatisticalService.getSpecifiedAreaStatistical( "-1", "-1", "-1","-1", null, null);
		JqueryUiDatagardPageModel<AreaStatistical> datagrid=new JqueryUiDatagardPageModel<>(pageModle.size(),pageModle);
		return datagrid;
	}
}
