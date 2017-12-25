package cn.zectec.contraceptive.management.system.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.zectec.contraceptive.management.system.model.Contraceptive;
import cn.zectec.contraceptive.management.system.model.ContraceptiveStatistical;
import cn.zectec.contraceptive.management.system.service.IContraceptiveStatisticalService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilter;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilters;

@Controller
public class ContraceptiveStatisticalController {
	@Resource
	private IContraceptiveStatisticalService contraceptiveStatisticalService;
	@RequestMapping(value="/contraceptiveStatistical")
	public String get(){
		return "indexDatagrid/statisticalDistribution/contraceptiveStatistical";
	}
	@JsonFilters({
		@JsonFilter(pojo=ContraceptiveStatistical.class,ignore="area"),
		@JsonFilter(pojo=Contraceptive.class,allow="name")
	})
	@RequestMapping(value="/contraceptiveStatisticalDatagrid")
	public Object getContraceptive(@RequestParam(value="page",defaultValue="1")int page,
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
		List<ContraceptiveStatistical> pageModel=contraceptiveStatisticalService.getSpecifiedContraceptiveStatistical(cityId, countryId, townshipStreetId, communityId, start, end);
		JqueryUiDatagardPageModel<ContraceptiveStatistical> datagrid=new JqueryUiDatagardPageModel<>(pageModel.size(), pageModel);
		return datagrid;
	}
}
