package cn.zectec.contraceptive.management.system.manager;

import java.util.Date;
import java.util.List;

import cn.zectec.contraceptive.management.system.model.AgeStatistical;

public interface IAgeStatisticalManager extends IBaseManager<AgeStatistical, Long>{
	public List<AgeStatistical> getSpecifiedAgeStatistical(String cityId,String countryId,String townshipStreetId,String communityId,Date startTime,Date endTime);
}
