package cn.zectec.contraceptive.management.system.manager.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.zectec.contraceptive.management.system.manager.IAgeStatisticalManager;
import cn.zectec.contraceptive.management.system.model.AgeStatistical;
import cn.zectec.contraceptive.management.system.model.AgeStatistical.AgeGroup;
import cn.zectec.contraceptive.management.system.repository.IAgeStatisticalRepository;
public class AgeStatisticalManagerImpl extends SimpleBaseManagerImpl<AgeStatistical> implements IAgeStatisticalManager{

	private IAgeStatisticalRepository ageStatisticalRepository;
	private long baseCityId = 1101;
	@Autowired
	public AgeStatisticalManagerImpl(IAgeStatisticalRepository baseRepository) {
		super(baseRepository);
		this.ageStatisticalRepository=baseRepository;
	}
	
	
	public long getBaseCityId() {
		return baseCityId;
	}


	public void setBaseCityId(long baseCityId) {
		this.baseCityId = baseCityId;
	}


	@Override
	public List<AgeStatistical> getSpecifiedAgeStatistical( String cityId, String countryId,
			String townshipStreetId, String communityId, Date startTime,
			Date endTime) {
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
		List<AgeStatistical> result = null;
		if(!("-1").equals(communityId)){
			result = this.ageStatisticalRepository.statistBytArea(id, startTime, endTime);
		}else{
			result = this.ageStatisticalRepository.statistByParentArea(id, startTime, endTime);
		}
		List<AgeGroup> list = new ArrayList<AgeGroup>();
		for(AgeGroup ageGroup : AgeGroup.values()){
			list.add(ageGroup);
		}
		List<AgeGroup> remove = new ArrayList<AgeGroup>();
		for(AgeStatistical ageStatistical : result){
			remove.add(ageStatistical.getAgeGroup());
		}
		list.removeAll(remove);
		for(AgeGroup ageGroup : list){
			result.add(new AgeStatistical(0, 0, 0, 0, 0, 0, 0, ageGroup));
		}
		Collections.sort(result, new Comparator<AgeStatistical>() {
			@Override
			public int compare(AgeStatistical o1, AgeStatistical o2) {
				return o1.getAgeGroup().ordinal()-o2.getAgeGroup().ordinal();
			}
		});
		return result;
	}
	


}
