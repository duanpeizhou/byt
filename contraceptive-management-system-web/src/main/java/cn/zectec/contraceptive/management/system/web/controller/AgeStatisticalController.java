package cn.zectec.contraceptive.management.system.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.zectec.contraceptive.management.system.model.AgeStatistical;
import cn.zectec.contraceptive.management.system.service.IAgeStatisticalService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilter;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilters;

@Controller
public class AgeStatisticalController {
	@Resource
	private IAgeStatisticalService ageStatisticalService;
	@RequestMapping(value="/ageStatistical")
	public String get(){
		return "indexDatagrid/statisticalDistribution/ageStatistical";
	}
	@JsonFilters({
		@JsonFilter(pojo=AgeStatistical.class,ignore={"area"}),
	})
	@RequestMapping(value="/ageStatisticalDatagird")
	public Object getAgeStatistical(
			@RequestParam(value="page",defaultValue="1")int page,
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
		List<AgeStatistical> pageModle=ageStatisticalService.getSpecifiedAgeStatistical( cityId, countryId, townshipStreetId, communityId, start, end);
		JqueryUiDatagardPageModel<AgeStatistical> datagrid=new JqueryUiDatagardPageModel<>(pageModle.size(), pageModle);
		return datagrid;
	}
}
