package cn.zectec.contraceptive.management.system.service;

import java.util.Date;
import java.util.List;

import cn.zectec.contraceptive.management.system.model.ContraceptiveStatistical;

public interface IContraceptiveStatisticalService {
	/**
	 * @method 根据各级地点id以及统计的时间来查询出药具统计结果
	 * @param page当前页
	 * @param pageSize每页显示的数据条数
	 * @param cityId、countryId、townshipStreetId、commouityId 各级地点的ID
	 * @param startTime 查询开始的时间,endTime 查询结束的时间
	 * @return 返回所有的符合条件的药具统计结果
	 */
	public List<ContraceptiveStatistical> getSpecifiedContraceptiveStatistical(String cityId,String countryId,String townshipStreetId,String communityId,Date startTime,Date endTime);
}
