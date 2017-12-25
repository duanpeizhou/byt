package cn.zectec.contraceptive.management.system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import cn.zectec.contraceptive.management.system.model.AisleFailureRecord;

public interface IAisleFailureRecordService {
	/**
	 * @method 根据各项参数来分页查询货道故障的记录
	 * @param page当前页,pageSize每页显示的数据条数,direction 排序的方式，sortName 排序的字段, cityId、countryId、townshipStreetId、commouityId 各级地点的ID
	 * distributionPoints 发放点,startTime 查询开始的时间,endTime 查询结束的时间
	 * @return 符合条件的货道记录
	 */
	public Page<AisleFailureRecord> getSpecifiedFailureRecord(int page, int pageSize,Direction direction,String sortName,
			String cityId, String countryId, String townshipStreetId,String communityId,String distributionPoints,String startTime,String endTime);
	
}
