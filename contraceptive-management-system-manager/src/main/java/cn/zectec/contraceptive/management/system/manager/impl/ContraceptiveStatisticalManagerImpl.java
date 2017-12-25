package cn.zectec.contraceptive.management.system.manager.impl;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.zectec.contraceptive.management.system.manager.IContraceptiveManager;
import cn.zectec.contraceptive.management.system.manager.IContraceptiveStatisticalManager;
import cn.zectec.contraceptive.management.system.model.Contraceptive;
import cn.zectec.contraceptive.management.system.model.ContraceptiveStatistical;
import cn.zectec.contraceptive.management.system.repository.IContraceptiveStatisticalRepository;
public class ContraceptiveStatisticalManagerImpl extends SimpleBaseManagerImpl<ContraceptiveStatistical> implements IContraceptiveStatisticalManager{
	private IContraceptiveStatisticalRepository contraceptiveStatisticalRepository;
	@Autowired
	private IContraceptiveManager contraceptiveManager;
	private long baseCityId = 1101;
	@Autowired
	public ContraceptiveStatisticalManagerImpl(
			IContraceptiveStatisticalRepository baseRepository) {
		super(baseRepository);
		this.contraceptiveStatisticalRepository=baseRepository;
	}
	@Override
	public List<ContraceptiveStatistical> getSpecifiedContraceptiveStatistical(String cityId, String countryId,
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
		List<ContraceptiveStatistical> result = null;
		if(!("-1").equals(communityId)){
			result = this.contraceptiveStatisticalRepository.statistByArea(id, startTime, endTime);
		}else{
			result = this.contraceptiveStatisticalRepository.statistByParentArea(id, startTime, endTime);
		}
		List<Contraceptive> all = contraceptiveManager.findAll();
		List<Contraceptive> remove = new ArrayList<Contraceptive>();
		for(ContraceptiveStatistical c : result){
			remove.add(c.getContraceptive());
		}
		all.removeAll(remove);
		for(Contraceptive contraceptive : all){
			result.add(new ContraceptiveStatistical(0, 0, 0, 0, 0, 0, 0, contraceptive));
		}
		Collections.sort(result, new Comparator<ContraceptiveStatistical>(){

			@Override
			public int compare(ContraceptiveStatistical o1,
					ContraceptiveStatistical o2) {
				return (int) (o1.getContraceptive().getId()-o2.getContraceptive().getId());
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
