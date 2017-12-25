package cn.zectec.contraceptive.management.system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import cn.zectec.contraceptive.management.system.model.Aisle;

public interface IAisleService {
	/**
	 * 条件查询
	 * @param page
	 * @param pageSize
	 * @param stateName
	 * @param state
	 * @param cityId
	 * @param countryId
	 * @param townshipStreetId
	 * @return
	 */
	public Page<Aisle> getSpecifiedAisles(int page,int pageSize,Direction directionName,String orderName,String stateName,boolean state,
			String cityId,String countryId,
			String townshipStreetId,
			String communityId,String distributionPoints);
}
