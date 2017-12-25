package cn.zectec.contraceptive.management.system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import cn.zectec.contraceptive.management.system.model.OverTemperatureRecord;

public interface IOverTemperatureRecordService {
	/**
	 * 描述：获得温度超限记录
	 * @author Administrator
	 * @param page 页数
	 * @param pageSize  每一页的数据条数
	 * @param direction 
	 * @param sortName 查询条件
	 * @param startTime 起始时间
	 * @param endTime 结束时间
	 * @param cityId 市区id
	 * @param countryId 市区id
	 * @param townshipStreetId 乡镇id
	 * @param communityId 社区id
	 * @param distributionPoints 发放点
	 * @return 温度超限记录
	 */
	public Page<OverTemperatureRecord> getOverTemperatureRecord(int page,
			int pageSize,Direction direction,String sortName, String cityId, String countryId,
			String townshipStreetId,String communityId,String distributionPoints,String startTime,String endTime);
}