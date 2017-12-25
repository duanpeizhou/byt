package cn.zectec.contraceptive.management.system.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;

public interface IGetMedicineRecordService {
	/**
	 * 描述：分页获取药具领用记录
	 * @author Administrator
	 * @param page
	 * @param rows
	 * @return 药具领用记录
	 */
	public Page<GetMedicineRecord> getGetMedicineRecord(int page,int rows);
	/**
	 * 描述：添加药具领用记录
	 * @author Administrator
	 * @param getMedicineRecord
	 * @return true 添加成功  false 添加失败
	 */
	public boolean saveGetMedicineRecord(GetMedicineRecord getMedicineRecord);
	/**
	 * 描述：分页获取药具领用记录（条件查询）
	 * @author Administrator
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param sortName
	 * @param startTime
	 * @param endTime
	 * @param cityId
	 * @param countryId
	 * @param townshipStreetId
	 * @param communityId
	 * @param distributionPoints
	 * @return 药具领用记录
	 */
	public Page<GetMedicineRecord> getMedicineRecords(int page,int pageSize,
			Direction direction,String sortName,Date startTime,Date endTime,
			String cityId,String countryId,String townshipStreetId,String communityId,String distributionPoints);
	/**
	 * 描述：通过开始结束时间获取领用记录
	 * @author Administrator
	 * @param startTime
	 * @param endTime
	 * @return 药具领用记录
	 */
	public List<GetMedicineRecord> getMedicineRecordsByGetTime(Date startTime,Date endTime);
	
	public long getCounts(Date startTime, Date endTime);

}
