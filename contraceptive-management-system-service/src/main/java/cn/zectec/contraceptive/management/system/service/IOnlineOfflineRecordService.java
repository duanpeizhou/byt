package cn.zectec.contraceptive.management.system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import cn.zectec.contraceptive.management.system.model.OnlineOfflineRecord;

public interface IOnlineOfflineRecordService {
	/**
	 * 描述：获得在线离线记录
	 * @author Administrator
	 * @param page 页数
	 * @param pageSize  每一页的数据条数
	 * @param direction 排序的方式
	 * @param sortName 排序的字段
	 * @param startTime 起始时间
	 * @param endTime 结束时间
	 * @param cityId 市区id
	 * @param countryId 市区id
	 * @param townshipStreetId 乡镇id
	 * @param communityId 社区id
	 * @param distributionPoints 发放点
	 * @return 在线离线记录
	 */
	public Page<OnlineOfflineRecord> getOnlineOfflineRecord(int i, int pageSize,Direction direction,String sortName,
			String cityId, String countryId, String townshipStreetId,String communityId,String distributionPoints,String startTime,String endTime);



}
