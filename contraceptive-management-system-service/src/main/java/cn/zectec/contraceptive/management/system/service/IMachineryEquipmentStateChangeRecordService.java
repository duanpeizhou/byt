package cn.zectec.contraceptive.management.system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateChangeRecord;

public interface IMachineryEquipmentStateChangeRecordService {
	/**
	 * 分页查询设备状态改变的记录
	 * @param page 当前的页码
	 * @param pageSize 当前每页显示的条数
	 * @param direction 排序的方式
	 * @param sortName 排序的字段名
	 * @return 符合条件的状态改变的记录
	 */
	Page<MachineryEquipmentStateChangeRecord> getAllME(int page,int pageSize,Direction direction,String sortName);
	/**
	 * 分页获取设备状态改变的记录
	 * @param page 当前的页码
	 * @param pageSize 当前每页显示的条数
	 * @param direction 排序的方式
	 * @param sortName 排序的字段名
	 * @param cityId 市级id
	 * @param countryId 县区id
	 * @param townshipStreetId 街道id
	 * @param communityId 社区id
	 * @param distributionPoints 发放点
	 * @return 返回符合条件的设备状态改变记录
	 */
	Page<MachineryEquipmentStateChangeRecord> getAllMachineryEquipmentStateChangeRecord(int page,
			int pageSize,Direction direction,String sortName,String cityId,
			String countryId,String townshipStreetId,
			String communityId,String distributionPoints);
	/**
	 * @method 根据各级地点id以及统计的时间来查询出缺货设备信息
	 * @param page当前页
	 * @param pageSize每页显示的数据条数
	 * @param direction 排序的方式
	 * @param sortName 排序的字段
	 * @param cityId、countryId、townshipStreetId、commouityId 各级地点的ID
	 * @param startTime 查询开始的时间,endTime 查询结束的时间
	 * @param  distributionPoints 发放点
	 * @return 返回所有的符合条件缺货设备信息
	 */
	Page<MachineryEquipmentStateChangeRecord> getStockoutReplenishRecord(int page,int pageSize, Direction direction, String sortName);
}
