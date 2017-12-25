package cn.zectec.contraceptive.management.system.manager;

import java.util.Date;
import java.util.List;

import cn.zectec.contraceptive.management.system.model.AreaStatistical;

public interface IAreaStatisticalManager extends IBaseManager<AreaStatistical, Long>{
	public List<AreaStatistical> getSpecifiedAreaStatistical(String cityId,String countryId,String townshipStreetId,String communityId,Date startTime,Date endTime);

}
