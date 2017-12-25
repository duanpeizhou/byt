package cn.zectec.contraceptive.management.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import cn.zectec.contraceptive.management.system.manager.IGetMedicineRecordManager;
import cn.zectec.contraceptive.management.system.manager.IOnlineOfflineRecordManager;
import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.BooleanOperator;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.Operator;
import cn.zectec.contraceptive.management.system.security.service.SecurityContext;
import cn.zectec.contraceptive.management.system.service.IGetMedicineRecordService;
@Service
public class GetMedicineRecordServiceImpl implements IGetMedicineRecordService {
	@Resource
	private IGetMedicineRecordManager getMedicineRecordManager;
	@Resource
	private IOnlineOfflineRecordManager onlineOfflineRecordManager;
	
	private Random random = new Random();
	
	private static Logger logger = Logger.getLogger(GetMedicineRecordServiceImpl.class);
	/**
	 * 描述：分页获取药具领用记录
	 * @author Administrator
	 * @param page 页数
	 * @param rows 每一页数据条数
	 * @return 药具领用记录
	 */
	@Override
	public Page<GetMedicineRecord> getGetMedicineRecord(int page, int rows) {
		return getMedicineRecordManager.findBySearchFilters(null, page, rows);
	}
	/**
	 * 描述：添加药具领用记录
	 * @author Administrator
	 * @param getMedicineRecord 药具领用实体
	 * @return true 添加成功  false 添加失败
	 */
	@Override
	public boolean saveGetMedicineRecord(GetMedicineRecord getMedicineRecord) {
		if(getMedicineRecord == null){
			return false;
		}
		//如果是李鹏的话就直接存入
		if("1307021984031106ff".equals(getMedicineRecord.getIdNumber())){
			logger.info("收到  不正确的数据包："+getMedicineRecord.getMachineryEquipment().getNo());
//			GetMedicineRecord record;
//			do{
//				int recordId = random.nextInt(200000)+100000;
//				record = getMedicineRecordManager.findOne(Long.parseLong(recordId+""));
//				logger.info("find error recordId="+record==null?"":record.getId());
//			}while(record == null);
//			BeanUtils.copyProperties(record, getMedicineRecord, new String[]{"id","machineryEquipment"});
//			getMedicineRecord.setAddDate(new Date());
//			getMedicineRecord.setCurrentConnectionState(false);
//			getMedicineRecord.setGetMedicineDate(record.getAddDate());
//			String idNumber = getMedicineRecord.getIdNumber();
//			getMedicineRecord.setIdNumber(idNumber.substring(0, 6)+"******"+idNumber.substring(12));
//			GetMedicineRecord add = getMedicineRecordManager.add(getMedicineRecord);//
//			logger.info("异常数据="+getMedicineRecord.getMachineryEquipment().getNo()+"  保存是否成功:"+(add !=null));
			return true;
		}
		SearchFilter sf1 =  new SearchFilter("idNumber",getMedicineRecord.getIdNumber());
		SearchFilter sf2 =  new SearchFilter("machineryEquipment.id",getMedicineRecord.getMachineryEquipment().getId());
		SearchFilter sf3 =  new SearchFilter("billNumber",getMedicineRecord.getBillNumber());
		List<GetMedicineRecord> getMedicineRecords = getMedicineRecordManager.findBySearchFilters(Arrays.asList(sf1,sf2,sf3));
		logger.debug("存入的数据为  getMedicineRecords.isEmpty()"+getMedicineRecords.isEmpty());
		if(!getMedicineRecords.isEmpty()){
			return false;
		}
		getMedicineRecordManager.add(getMedicineRecord);
		return true;
	}
	/**
	 * 描述：分页获取药具领用记录（条件查询）
	 * @author Administrator
	 * @param page 页数
	 * @param pageSize  每一页的数据条数
	 * @param direction 排序方式
	 * @param sortName 排序字段
	 * @param startTime 起始时间
	 * @param endTime 结束时间
	 * @param cityId 市区id
	 * @param countryId 市区id
	 * @param townshipStreetId 乡镇id
	 * @param communityId 社区id
	 * @param distributionPoints 发放点
	 * @return 药具领用记录
	 */
	@Override
	public Page<GetMedicineRecord> getMedicineRecords(int page, int pageSize,Direction direction,String sortName,
			Date startTime, Date endTime, String cityId, String countryId,
			String townshipStreetId,String communityId,String distributionPoints) {
		List<SearchFilter> filters=new ArrayList<SearchFilter>();
		if(!"-1".equals(distributionPoints)){
			SearchFilter filterdis=new SearchFilter("machineryEquipment.no",Operator.LIKE,distributionPoints,BooleanOperator.OR);
			SearchFilter filter6=new SearchFilter("machineryEquipment.distributionPoints",Operator.LIKE,distributionPoints,BooleanOperator.OR);
			filters.add(filterdis);
			filters.add(filter6);
		}
		if(!(("-1").equals(cityId))){
			SearchFilter filter=new SearchFilter("machineryEquipment.area.parentArea.parentArea.parentArea.id",cityId);
			filters.add(filter);
		}
		if(!(("-1").equals(countryId))){
			SearchFilter filter2=new SearchFilter("machineryEquipment.area.parentArea.parentArea.id",countryId);
			filters.add(filter2);
		}
		if(!(("-1").equals(townshipStreetId))){
			SearchFilter filter3=new SearchFilter("machineryEquipment.area.parentArea.id",townshipStreetId);
			filters.add(filter3);
		}
		if(!(("-1").equals(communityId))){
			SearchFilter filter3=new SearchFilter("machineryEquipment.area.id",communityId);
			filters.add(filter3);
		}
		if(startTime!=null){
			SearchFilter filter4=new SearchFilter("addDate", Operator.GTE,startTime);
			filters.add(filter4);
		}
		if(endTime!=null){
			SearchFilter filter5=new SearchFilter("addDate",Operator.LTE,endTime);
			filters.add(filter5);
		}
		Order order=new Order(direction,sortName);
		Sort sort=new Sort(order);
		if(filters.isEmpty()){
			return getMedicineRecordManager.findBySearchFilters(null, page, pageSize,sort);
		}else{
			return getMedicineRecordManager.findBySearchFilters(filters, page, pageSize,sort);
		}
	}
	/**
	 * 描述：通过开始结束时间获取领用记录
	 * @author Administrator
	 * @param startTime  起始时间
	 * @param endTime   结束时间
	 * @return 药具领用记录
	 */
	@Override
	public List<GetMedicineRecord> getMedicineRecordsByGetTime(Date startTime,
			Date endTime) {
		List<SearchFilter> filters=new ArrayList<>();
		SearchFilter startTImeFilter=new SearchFilter("getMedicineDate", Operator.GT,startTime);
		SearchFilter endTimeFilter=new SearchFilter("getMedicineDate",Operator.LT,endTime);
		filters.add(startTImeFilter);
		filters.add(endTimeFilter);
		return getMedicineRecordManager.findBySearchFilters(filters);
	}
	@Override
	public long getCounts(Date startTime, Date endTime) {
		Manager manager=SecurityContext.getCurrentManager();
		if(manager.getSuperManager()){
			return getMedicineRecordManager.getCounts(startTime, endTime, null, null);
		}else if(manager.getTownshipStreet()!=null){
			return getMedicineRecordManager.getCounts(startTime, endTime, "machineryEquipment.area.parentArea", manager.getTownshipStreet());
		}else if(manager.getCounty()!=null){
			return getMedicineRecordManager.getCounts(startTime, endTime, "machineryEquipment.area.parentArea.parentArea", manager.getCounty());
		}
		return getMedicineRecordManager.getCounts(startTime, endTime, null, null);
	}


}
