package cn.zectec.contraceptive.management.system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import cn.zectec.contraceptive.management.system.model.CardReaderFailureRecord;

public interface ICardReaderFailureRecordService {
	/**
	 * 描述：获取读卡器失败记录
	 * @author Administrator
	 * @param page  页数
	 * @param pageSize  每一页的数据条数
	 * @param direction 
	 * @param sortName 
	 * @param cityId  市区id
	 * @param countryId  乡镇id
	 * @param townshipStreetId  城镇街道id
	 * @param communityId  社区id
	 * @param distributionPoints  发放点 
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return 读卡器失败记录
	 */
	public Page<CardReaderFailureRecord> getCardReaderFailureRecord(int page,
			int pageSize,Direction direction,String sortName, String cityId, String countryId,
			String townshipStreetId,String communityId,String distributionPoints,String startTime,String endTime);
}
