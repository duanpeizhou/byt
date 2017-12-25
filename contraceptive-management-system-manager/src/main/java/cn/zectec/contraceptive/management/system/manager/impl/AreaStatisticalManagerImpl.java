package cn.zectec.contraceptive.management.system.manager.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.zectec.contraceptive.management.system.manager.IAreaManager;
import cn.zectec.contraceptive.management.system.manager.IAreaStatisticalManager;
import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.AreaStatistical;
import cn.zectec.contraceptive.management.system.repository.IAreaStatisticalRepository;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
public class AreaStatisticalManagerImpl extends SimpleBaseManagerImpl<AreaStatistical> implements IAreaStatisticalManager{
	
	private IAreaStatisticalRepository areaStatisticalRepository;
	private long baseCityId =1101;
	@Autowired
	private IAreaManager areaManager;
	@Autowired
	public AreaStatisticalManagerImpl(
			IAreaStatisticalRepository baseRepository) {
		super(baseRepository);
		this.areaStatisticalRepository=baseRepository;
	}



	
	@Override
	public List<AreaStatistical> getSpecifiedAreaStatistical(String cityId, String countryId,
			String townshipStreetId,String communityId, Date startTime, Date endTime) {
		long id = 0;
		if(!("-1").equals(communityId)){
			id = Long.parseLong(communityId);
		}else if(!("-1").equals(townshipStreetId)){
			id = Long.parseLong(townshipStreetId);
		}else if(!("-1").equals(countryId)){
			id = Long.parseLong(countryId);
		}else if(!("-1").equals(cityId)){
			id = Long.parseLong(cityId);
		}else{
			id = baseCityId;
		}
		if(startTime == null){
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.add(Calendar.YEAR, -100);
			startTime = calendar.getTime();
		}
		if(endTime == null){
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.add(Calendar.YEAR, 100);
			endTime = calendar.getTime();
		}
		List<AreaStatistical> result = null;
		List<Area> list = null;
		if(!("-1").equals(communityId)){
			result = this.areaStatisticalRepository.statistByArea(id, startTime, endTime);
			SearchFilter searchFilter = new SearchFilter("id",id);
			list = areaManager.findBySearchFilters(Arrays.asList(searchFilter));
		}else{
			result = this.areaStatisticalRepository.statistByParentArea(id, startTime, endTime);
			SearchFilter searchFilter = new SearchFilter("parentArea.id",id);
			list = areaManager.findBySearchFilters(Arrays.asList(searchFilter));
		}
		List<Area> add = new ArrayList<Area>();
		for(AreaStatistical areaStatistical : result){
			add.add(areaStatistical.getArea());
		}
		list.removeAll(add);
		for(Area area : list){
			result.add(new AreaStatistical(0, 0, 0, 0, 0, 0, 0, area));
		}
		Collections.sort(result,new Comparator<AreaStatistical>() {

			@Override
			public int compare(AreaStatistical o1, AreaStatistical o2) {
				return (int) (o1.getArea().getId()-o2.getArea().getId());
			}
		});
		return result;
		
	}




	public long getBaseCityId() {
		return baseCityId;
	}




	public void setBaseCityId(long baseCityId) {
		this.baseCityId = baseCityId;
	}




}
