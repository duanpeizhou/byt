package cn.zectec.contraceptive.management.system.manager;

import java.util.Date;
import java.util.List;

import cn.zectec.contraceptive.management.system.model.ContraceptiveStatistical;

public interface IContraceptiveStatisticalManager extends IBaseManager<ContraceptiveStatistical, Long>{
	public List<ContraceptiveStatistical> getSpecifiedContraceptiveStatistical(String cityId,String countryId,String townshipStreetId,String communityId,Date startTime,Date endTime);
}
