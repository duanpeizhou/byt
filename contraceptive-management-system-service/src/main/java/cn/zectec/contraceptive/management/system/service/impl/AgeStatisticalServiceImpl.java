package cn.zectec.contraceptive.management.system.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zectec.contraceptive.management.system.manager.IAgeStatisticalManager;
import cn.zectec.contraceptive.management.system.model.AgeStatistical;
import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.security.service.SecurityContext;
import cn.zectec.contraceptive.management.system.service.IAgeStatisticalService;
/**
 * @author xiao
 *
 */
@Service
public class AgeStatisticalServiceImpl implements IAgeStatisticalService{
	@Resource
	private IAgeStatisticalManager ageStatisticalManager;
	/**
	 * @method 根据各级地点id以及统计的时间来查询出年龄统计结果
	 * @param page当前页
	 * @param pageSize每页显示的数据条数
	 * @param direction 排序的方式
	 * @param sortName 排序的字段
	 * @param cityId  市区id
	 * @param countryId  乡镇id
	 * @param townshipStreetId  城镇街道id
	 * @param communityId  社区id
	 * @param startTime 查询开始的时间,endTime 查询结束的时间
	 * @return 返回所有的符合条件的年龄统计结果
	 */
	@Override
	public List<AgeStatistical> getSpecifiedAgeStatistical(String cityId, String countryId,
			String townshipStreetId, String communityId, Date startTime,
			Date endTime) {
		Manager manager = SecurityContext.getCurrentManager();
		if(!manager.getSuperManager()){
			if(manager.getTownshipStreet()!=null){
				countryId = manager.getTownshipStreet().getParentArea().getId()+"";
				townshipStreetId = manager.getTownshipStreet().getId()+"";
			}else if(manager.getCounty() != null){
				countryId = manager.getCounty().getId()+"";
			}
		}
		return ageStatisticalManager.getSpecifiedAgeStatistical(cityId, countryId, townshipStreetId, communityId, startTime, endTime);
	}
	
}
