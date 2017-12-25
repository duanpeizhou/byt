package cn.zectec.contraceptive.management.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import cn.zectec.contraceptive.management.system.manager.IMachineryEquipmentManager;
import cn.zectec.contraceptive.management.system.manager.IMachineryEquipmentStateChangeRecordManager;
import cn.zectec.contraceptive.management.system.manager.IOnlineOfflineRecordManager;
import cn.zectec.contraceptive.management.system.manager.IOutStockReplenishmentRecordManager;
import cn.zectec.contraceptive.management.system.model.Aisle;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateInfo;
import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.model.Strategy;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.BooleanOperator;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.Operator;
import cn.zectec.contraceptive.management.system.security.service.SecurityContext;
import cn.zectec.contraceptive.management.system.service.IAreaService;
import cn.zectec.contraceptive.management.system.service.IMachineryEquipmentService;
import cn.zectec.contraceptive.management.system.service.IMachineryEquipmentStateInfoService;
import cn.zectec.contraceptive.management.system.service.IStrategyService;

@Service
public class MachineryEquipmentServiceImpl implements IMachineryEquipmentService{
	
	@Resource
	private IMachineryEquipmentManager machineryEquipmentManager;
	@Resource
	private IOnlineOfflineRecordManager onlineOfflineRecordManager;
	@Resource
	private IStrategyService strategyService;
	@Resource
	private IMachineryEquipmentStateChangeRecordManager machineryEquipmentStateChangeRecordManager;
	@Resource
	private IOutStockReplenishmentRecordManager outStockReplenishmentRecordManager;
	
	@Autowired
	private IMachineryEquipmentStateInfoService equipmentStateInfoService;
	@Autowired
	private IAreaService areaService;
	
	private static Logger logger =Logger.getLogger(MachineryEquipmentServiceImpl.class);
	

	/**
	 * 分页查询设备信息
	 * @param page 当前页
	 * @param size 每页数据条数
	 * @return 设备信息
	 */
	@Override
	public Page<MachineryEquipment> getAllMachineryEquipment(int page, int size) {
		
		return machineryEquipmentManager.findBySearchFilters(null, page, size);
	}
	/**
	 * 通过deviceNo查找MachineryEquipment
	 * @param deviceNo 设备编号
	 * @return 通过deviceNo查找到的设备
	 */
	@Override
	public MachineryEquipment getMachineryEquipmentByDeviceNo(long deviceNo) {
		SearchFilter searchFilter = new SearchFilter("deviceNo", deviceNo);
		return machineryEquipmentManager.findBySearchFilters(searchFilter);
	}
	/**
	 * 更新设备的连接信息
	 * @param id 设备id
	 * @param connect 连接状态
	 * @param date 时间
	 */
	@Override
	public void updateEquipmentConnectionState(long id, boolean connect,Date date) {
		MachineryEquipment me = machineryEquipmentManager.findOne(id);
		MachineryEquipmentStateInfo  state = me.getMachineryEquipmentState();
		if(connect && (!state.isConnectionState())){
			state.setOnlineDate(date);
			machineryEquipmentStateChangeRecordManager.addOnlineOfflineRecord(me,true);
		}else if((!connect) && (state.isConnectionState())){
			state.setOfflineDate(date);
			machineryEquipmentStateChangeRecordManager.addOnlineOfflineRecord(me,false);
		}
		state.setConnectionState(connect);
		machineryEquipmentManager.update(me);
	}
	/**
	 * 检查设备是否取货
	 * @param id 设备id
	 * @param cargoLeft 每个货道的数量
	 */
	@Override
	public void checkStock(long id, int[] cargoLeft) {
		MachineryEquipment me = machineryEquipmentManager.findOne(id);
		Strategy strategy = strategyService.currentStrategy();
		int stockNumber = strategy.getAlarmOutStockAmount();
		List<Aisle> aisles = me.getAisles();
		boolean falg = false;
		for (int i = 0; i < me.getAislesNum(); i++) {
			Aisle aisle = aisles.get(i);
			logger.debug(me.getDeviceNo()+",第"+i+"货道数量"+cargoLeft[i]);
			if(aisle.getStockout() && cargoLeft[i] > stockNumber){
				//补货
				aisle.setStockout(false);
				machineryEquipmentStateChangeRecordManager.addReplenishment(aisle,cargoLeft[i],true);
			}else if((!aisle.getStockout()) && cargoLeft[i] <= stockNumber){
				//缺货
				falg = true;
				aisle.setStockout(true);
				aisle.setStockoutDate(new Date());
				machineryEquipmentStateChangeRecordManager.addStockout(aisle,cargoLeft[i],false);
			}else if(aisle.getStockout() && aisle.getNum() > cargoLeft[i]){
				
				falg = true;
				aisle.setStockout(true);
				machineryEquipmentStateChangeRecordManager.addStockout(aisle,cargoLeft[i],true);
			}else if((!aisle.getStockout()) && aisle.getNum() < cargoLeft[i]){
				
				machineryEquipmentStateChangeRecordManager.addReplenishment(aisle,cargoLeft[i],false);
				aisle.setStockout(false);
			}
			aisle.setNum(cargoLeft[i]);
		}
		if((!me.getMachineryEquipmentState().isStockOut()) &&falg){
			me.getMachineryEquipmentState().setStockOut(true);
		}else if(!falg){
			me.getMachineryEquipmentState().setStockOut(false);
		}
		machineryEquipmentManager.update(me);
		
		
	}
	/**
	 * 检测设备的开门关门状态
	 * @param id 设备id
	 * @param doorState 开门状态
	 */
	@Override
	public void checkDoorState(long id, boolean doorState) {
		MachineryEquipment me = machineryEquipmentManager.findOne(id);
		MachineryEquipmentStateInfo  state = me.getMachineryEquipmentState();
		if(doorState && (!state.isDoorState())){
			//开门
			machineryEquipmentStateChangeRecordManager.addDoorStateChangeRecord(me,doorState);
			state.setDoorDate(new Date());
		}else if((!doorState) && state.isDoorState()){
			//关门
			machineryEquipmentStateChangeRecordManager.addDoorStateChangeRecord(me,doorState);
			state.setCloseDoorDate(new Date());
		}
		state.setDoorState(doorState);
		machineryEquipmentManager.update(me);
	}


	/**
	 * 检测设备的温度是否正常
	 * @param id 设备id
	 * @param temporature 温度
	 */
	@Override
	public void checkTemporature(long id, int temporature) {
		Strategy strategy = strategyService.currentStrategy();
		MachineryEquipment me = machineryEquipmentManager.findOne(id);
		MachineryEquipmentStateInfo  state = me.getMachineryEquipmentState();
		int max = strategy.getAlarmMaxTemperature();
		int min = strategy.getAlarmMinTemperature();
		boolean flag = temporature>max || temporature<min;
		if((!state.isOverTemperature())&& flag){
			machineryEquipmentStateChangeRecordManager.addOverTemporatureRecord(me,temporature,true);
			state.setOverTemperature(true);
			state.setOverTemperatureDate(new Date());
		}else if(state.isOverTemperature() && (!flag)){
			machineryEquipmentStateChangeRecordManager.addOverTemporatureRecord(me,temporature,false);
			state.setOverTemperature(false);
		}
		state.setDeviceTemperature(temporature);
		machineryEquipmentManager.update(me);
	}

	/**
	 * 设置信号的强度
	 * @param id 设备的id
	 * @param signalStrength 信号强度
	 */
	@Override
	public void updateSignalStrength(long id, int signalStrength) {
		MachineryEquipment me = machineryEquipmentManager.findOne(id);
		MachineryEquipmentStateInfo  state = me.getMachineryEquipmentState();
		state.setSignalStrength(signalStrength);
		machineryEquipmentManager.update(me);
	}

	/**
	 * 检查货道是否缺货
	 * @param id 设备的id
	 * @param aisleFailure 货道
	 */
	@Override
	public void checkAisleFailure(long id,boolean[] aisleFailure) {
		MachineryEquipment me = machineryEquipmentManager.findOne(id);
		List<Aisle> aisles = me.getAisles();
		for (int i = 0; i < me.getAislesNum(); i++) {
			Aisle aisle = aisles.get(i);
			if(aisle.getAisleFailure() && (!aisleFailure[i])){
				//恢复
				machineryEquipmentStateChangeRecordManager.addAisleFailureRecord(aisle,false);
			}else if((!aisle.getAisleFailure()) && aisleFailure[i]){
				//故障
				aisle.setAisleFailureDate(new Date());
				machineryEquipmentStateChangeRecordManager.addAisleFailureRecord(aisle,true);
			}
			aisle.setAisleFailure(aisleFailure[i]);
		}
		machineryEquipmentManager.update(me);
		
	}

	/**
	 * 检查读卡器是否故障
	 * @param id 设备id
	 * @param cardReader 是否故障
	 */
	@Override
	public void checkCardReaderFailure(long id, boolean cardReader) {
		MachineryEquipment me = machineryEquipmentManager.findOne(id);
		MachineryEquipmentStateInfo  state = me.getMachineryEquipmentState();
		if((!state.isCardReaderFailure()) && cardReader){
			//故障
			state.setCardReaderFailureDate(new Date());
			machineryEquipmentStateChangeRecordManager.addCardReaderFailureRecord(me,cardReader);
		}else if(state.isCardReaderFailure() && (!cardReader)){
			//恢复
			machineryEquipmentStateChangeRecordManager.addCardReaderFailureRecord(me,cardReader);
		}
		state.setCardReaderFailure(cardReader);
		machineryEquipmentManager.update(me);
	}


	/**
	 * 删除设备
	 * @param id 设备的id
	 * @return 是否成功删除
	 */
	@Override
	public boolean deleteMachineryEquipment(long id) {
		try{
			MachineryEquipment machineryEquipment = machineryEquipmentManager.findOne(id);
			machineryEquipmentManager.delete(machineryEquipment);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 通过id查询设备
	 * @param id 设备的id
	 * @return 符合条件的设备
	 */
	@Override
	public MachineryEquipment getMachineryEquipmentById(long id) {
		return machineryEquipmentManager.findOne(id);
	}

	/**
	 * 添加设备
	 * @param machineryEquipment 设备信息
	 */
	@Override
	public void add(MachineryEquipment machineryEquipment) {
		MachineryEquipmentStateInfo ma = new MachineryEquipmentStateInfo();
		ma.setMachineryEquipment(machineryEquipment);
		machineryEquipment.setMachineryEquipmentState(ma);
		int i = 1;
		for(Aisle aisle :machineryEquipment.getAisles()){
			aisle.setIndex_(i++);
			aisle.setMachineryEquipment(machineryEquipment);
		}
		machineryEquipmentManager.add(machineryEquipment);

		
	}

	/**
	 * 描述：更新设备
	 * @param machineryEquipment 设备类
	 * @return true 更新成功   false 更新失败
	 */

	@Override
	public boolean updateMachineryEquipment(
			MachineryEquipment machineryEquipment) {
		try {
			return machineryEquipmentManager.updateMachineryEquipment(machineryEquipment);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 条件查询
	 * @param page  页数
	 * @param pageSize  每一页的数据条数
	 * @param direction  排序方式
	 * @param sortName  排序字段
	 * @param state  状态
	 * @param cityId  市区id
	 * @param countryId  乡镇id
	 * @param townshipStreetId  城镇街道id
	 * @param communityId  社区id
	 * @param distributionPoints  发放点 
	 * @return 条件查询得到的设备信息
	 */

	@Override
	public Page<MachineryEquipment> getSpecifiedMachineryEquipments(int page,
			int pageSize,Direction directionName,String sortName, String stateName, boolean state, String cityId,
			String countryId, String townshipStreetId,String communityId,String distributionPoints) {
		List<SearchFilter> filters=new ArrayList<SearchFilter>();
		if(!"-1".equals(distributionPoints)){
			SearchFilter filterdis=new SearchFilter("no",Operator.LIKE,distributionPoints,BooleanOperator.OR);
			SearchFilter filter6=new SearchFilter("distributionPoints",Operator.LIKE,distributionPoints,BooleanOperator.OR);
			filters.add(filterdis);
			filters.add(filter6);
		}
		if(!("-1".equals(cityId))){
			SearchFilter searchFilter2=new SearchFilter("area.parentArea.parentArea.parentArea.id",cityId);
			filters.add(searchFilter2);
		}
		if(!("-1".equals(countryId))){
			SearchFilter searchFilter3=new SearchFilter("area.parentArea.parentArea.id",countryId);
			filters.add(searchFilter3);
		}
		if(!("-1".equals(townshipStreetId))){
			SearchFilter searchFilter4=new SearchFilter("area.parentArea.id",townshipStreetId);
			filters.add(searchFilter4);
		}
		if(!("-1".equals(communityId))){
			SearchFilter searchFilter5=new SearchFilter("area.id",communityId);
			filters.add(searchFilter5);
		}
		SearchFilter stateFilter=new SearchFilter(stateName,state);
		filters.add(stateFilter);
		Order order = new Order(directionName,sortName);
		Sort sort = new Sort(order);
		return machineryEquipmentManager.findBySearchFilters(filters, page, pageSize,sort);		
	}

	/**
	 * 获取当前在线的设备
	 * 
	 * @return 获取当前在线的设备
	 */
	@Override
	public List<MachineryEquipment> getAllOnlineMachineryEquipment() {
		SearchFilter searchFilter = new SearchFilter("machineryEquipmentState.connectionState",true);
		return machineryEquipmentManager.findBySearchFilters(Arrays.asList(searchFilter));
	}


	/**
	 * 获取所用的设备
	 * @return 
	 */
	@Override
	public long getTatolMachineryEquipments() {
		Manager manager = SecurityContext.getCurrentManager();
		if(manager.getSuperManager()){
			return machineryEquipmentManager.getTatolMachineryEquipments();
		}else{
			if(manager.getTownshipStreet() != null){
				return machineryEquipmentManager.getTatolMachineryEquipmentsByTownshipStreet(manager.getTownshipStreet());
			}else if(manager.getCounty() != null){
				return machineryEquipmentManager.getTatolMachineryEquipmentsByCounty(manager.getCounty());
			}else{
				return machineryEquipmentManager.getTatolMachineryEquipments();
			}
			
		}
	}
	/**
	 * 获取所用的在线设备
	 * @return
	 */
	@Override
	public long getOnlineTatolMachineryEquipment() {
		Manager manager=SecurityContext.getCurrentManager();
		if(manager.getSuperManager()){
				return machineryEquipmentManager.getOnlineTatolMachineryEquipment();
		}else if(manager.getTownshipStreet()!=null){
			return  machineryEquipmentManager.getOnlineTatolMachineryEquipmentByTownshipStreet(manager.getTownshipStreet(),"machineryEquipmentState.connectionState",true);
		}else if(manager.getCounty()!=null){
			return machineryEquipmentManager.getOnlineTatolMachineryEquipmentByCounty(manager.getCounty(),"machineryEquipmentState.connectionState",true);
		}else{
			return machineryEquipmentManager.getOnlineTatolMachineryEquipment();
		}
	}
	/**
	 * 获取所用的缺货的设备
	 * @return
	 */
	@Override
	public long getStockOutMachineryEquipment() {
		Manager manager=SecurityContext.getCurrentManager();
		if(manager.getSuperManager()){
			return machineryEquipmentManager.getStockOutMachineryEquipment();
		}else if(manager.getTownshipStreet()!=null){
			return machineryEquipmentManager.getOnlineTatolMachineryEquipmentByTownshipStreet(manager.getTownshipStreet(),"machineryEquipmentState.stockOut",true);
		}else if(manager.getCounty()!=null){
			return machineryEquipmentManager.getOnlineTatolMachineryEquipmentByCounty(manager.getCounty(),"machineryEquipmentState.stockOut",true);
		}else{
			return machineryEquipmentManager.getStockOutMachineryEquipment();
		}
	}
	/**
	 * 离线三天设备
	 * @param page 当前的页码
	 * @param pageSize 当前每页显示的条数
	 * @param direction 排序的方式
	 * @param sortName 排序的字段
	 * @param stateName 改变状态的字段
	 * @param state 是否
	 * @param cityId 市id
	 * @param countryId 县id
	 * @param townshipStreetId 街道id
	 * @param communityId 社区id
	 * @param calendar 日期
	 * @return 符合条件的设备
	 */
	@Override
	public Page<MachineryEquipment> getOfflin3MachineryEquipments(int page,int pageSize, Direction direction, String sortName, String stateName,boolean state, String cityId, String countryId,String townshipStreetId, String communityId,String distributionPoints, Date calendar) {
		List<SearchFilter> filters=new ArrayList<SearchFilter>();
		if(!"-1".equals(distributionPoints)){
			SearchFilter filterdis=new SearchFilter("no",Operator.LIKE,distributionPoints);
			SearchFilter filter6=new SearchFilter("distributionPoints",Operator.LIKE,distributionPoints,BooleanOperator.OR);
			filters.add(filterdis);
			filters.add(filter6);
		}
		if(!("-1".equals(cityId))){
			SearchFilter searchFilter2=new SearchFilter("area.parentArea.parentArea.parentArea.id",cityId);
			filters.add(searchFilter2);
		}
		if(!("-1".equals(countryId))){
			SearchFilter searchFilter3=new SearchFilter("area.parentArea.parentArea.id",countryId);
			filters.add(searchFilter3);
		}
		if(!("-1".equals(townshipStreetId))){
			SearchFilter searchFilter4=new SearchFilter("area.parentArea.id",townshipStreetId);
			filters.add(searchFilter4);
		}
		if(!("-1".equals(communityId))){
			SearchFilter searchFilter5=new SearchFilter("area.id",communityId);
			filters.add(searchFilter5);
		}
		if(calendar!=null)
		{
			SearchFilter timeFilter_ = new SearchFilter("machineryEquipmentState.offlineDate",Operator.LTE,calendar);
			filters.add(timeFilter_);
		}
		SearchFilter stateFilter=new SearchFilter(stateName,state);
		filters.add(stateFilter);
		Order order = new Order(direction,sortName);
		Sort sort = new Sort(order);
		return machineryEquipmentManager.findBySearchFilters(filters, page, pageSize , sort);
	}
	/**
	 * 分页查询设备
	 * @param page 当前的页码
	 * @param pageSize 当前每页显示的条数
	 * @param direction 排序的方式
	 * @param sort 排序的字段名
	 * @return 符合条件的设备
	 */
	@Override
	public Page<MachineryEquipment> getSpecifiedMachineryEquipments(int page,
			int pageSize, Direction directionName, String sortName,
			String countryId, String townshipStreetId, String communityId,
			String distributionPoints) {
		List<SearchFilter> filters=new ArrayList<SearchFilter>();
		if(!("-1".equals(distributionPoints))){
			SearchFilter filterdis=new SearchFilter("no",Operator.LIKE,distributionPoints,BooleanOperator.OR);
			SearchFilter filter6=new SearchFilter("distributionPoints",Operator.LIKE,distributionPoints,BooleanOperator.OR);
			filters.add(filterdis);
			filters.add(filter6);		
		}
		if(!("-1".equals(countryId))){
			SearchFilter searchFilter3=new SearchFilter("area.parentArea.parentArea.id",countryId);
			filters.add(searchFilter3);
		}
		if(!("-1".equals(townshipStreetId))){
			SearchFilter searchFilter4=new SearchFilter("area.parentArea.id",townshipStreetId);
			filters.add(searchFilter4);
		}
		if(!("-1".equals(communityId))){
			SearchFilter searchFilter5=new SearchFilter("area.id",communityId);
			filters.add(searchFilter5);
		}
		Order order = new Order(directionName,sortName);
		Sort sort = new Sort(order);
		return  machineryEquipmentManager.findBySearchFilters(filters, page, pageSize,sort);	
	}
	/**
	 * 分页查询设备
	 * @param page 当前的页码
	 * @param pageSize 当前每页显示的条数
	 * @param direction 排序的方式
	 * @param sort 排序的字段名
	 * @return 符合条件的设备
	 */
	@Override
	public Page<MachineryEquipment> getStockout5Search(int page, int pageSize , Direction direction,String sort) {
		int num = strategyService.currentStrategy().getAlarmOutStockAmount();
		Manager manager = SecurityContext.getCurrentManager();
		Page<MachineryEquipment> machineryEquipments = null;
		if(manager.getSuperManager() || (manager.getTownshipStreet()==null && manager.getCounty() == null)){
			machineryEquipments = machineryEquipmentManager.findAilseAmountLT(num,page,pageSize,direction,sort);
		}else if(manager.getTownshipStreet()!=null){
			machineryEquipments = machineryEquipmentManager.findAilseAmountLTByTownshipStreet(num,page,pageSize,direction,sort,manager.getTownshipStreet());
		}else if(manager.getCounty() != null){
			machineryEquipments = machineryEquipmentManager.findAilseAmountLTByCounty(num,page,pageSize,direction,sort,manager.getCounty());
		}
		return machineryEquipments;
	}

}
