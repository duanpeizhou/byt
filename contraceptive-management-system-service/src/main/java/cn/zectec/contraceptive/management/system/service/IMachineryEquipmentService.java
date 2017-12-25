package cn.zectec.contraceptive.management.system.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import cn.zectec.contraceptive.management.system.model.MachineryEquipment;

public interface IMachineryEquipmentService {

	/**
	 * 分页查询设备信息
	 */
	public Page<MachineryEquipment> getAllMachineryEquipment(int page,int size);
	
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
	public Page<MachineryEquipment> getSpecifiedMachineryEquipments(int page,int pageSize,
			Direction directionName,String sortName,String stateName,boolean state,String cityId,String countryId,String townshipStreetId,String communityId,String distributionPoints);
	public Page<MachineryEquipment> getSpecifiedMachineryEquipments(int page,int pageSize,
			Direction directionName,String sortName,String countryId,String townshipStreetId,String communityId,String distributionPoints);
	
	
	/**
	 * 通过deviceNo查找MachineryEquipment
	 * @return
	 */
	public MachineryEquipment getMachineryEquipmentByDeviceNo(long deviceNo);
	/**
	 * 更新设备的连接信息
	 * @param id 设备id
	 * @param connect 连接状态
	 * @param date 时间
	 */
	public void updateEquipmentConnectionState(long id, boolean connect, Date date);
	/**
	 * 检查设备是否取货
	 * @param id 设备id
	 * @param cargoLeft 每个货道的数量
	 */
	public void checkStock(long id, int[] cargoLeft);
	/**
	 * 检测设备的开门关门状态
	 * @param id 设备id
	 * @param doorState 开门状态
	 */
	public void checkDoorState(long id, boolean doorState);
	/**
	 * 检测设备的温度是否正常
	 * @param id 设备id
	 * @param temporature 温度
	 */
	public void checkTemporature(long id, int temporature);
	/**
	 * 设置信号的强度
	 * @param id 设备的id
	 * @param signalStrength 信号强度
	 */
	public void updateSignalStrength(long id, int signalStrength);
	/**
	 * 检查货道是否缺货
	 * @param id 设备的id
	 * @param aisleFailure 货道
	 */
	public void checkAisleFailure(long id,boolean[] aisleFailure);
	/**
	 * 检查读卡器是否故障
	 * @param id 设备id
	 * @param cardReader 是否故障
	 */
	public void checkCardReaderFailure(long id, boolean cardReader);
	/**
	 * 删除设备
	 * @param id 设备的id
	 * @return 是否成功删除
	 */
	public boolean deleteMachineryEquipment(long id);
	/**
	 * 通过id查询设备
	 * @param id 设备的id
	 * @return 符合条件的设备
	 */
	public MachineryEquipment getMachineryEquipmentById(long id);
	/**
	 * 添加设备
	 * @param machineryEquipment 设备信息
	 */
	public void add(MachineryEquipment machineryEquipment);
	/**
	 * 
	 * @param machineryEquipment
	 * @return
	 */
	public boolean updateMachineryEquipment(MachineryEquipment machineryEquipment);

	/**
	 * 获取当前在线的设备
	 * @return
	 */
	public List<MachineryEquipment> getAllOnlineMachineryEquipment();
	/**
	 * 获取所用的设备
	 * @return
	 */
	public long getTatolMachineryEquipments();
	/**
	 * 获取所用的在线设备
	 * @return
	 */
	public long getOnlineTatolMachineryEquipment();
	/**
	 * 获取所用的缺货的设备
	 * @return
	 */
	public long getStockOutMachineryEquipment();
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
	public Page<MachineryEquipment> getOfflin3MachineryEquipments(int page,int pageSize, Direction direction, String sortName, String stateName,boolean state, String cityId, String countryId,String townshipStreetId, String communityId, String distributionPoints,Date calendar);
	/**
	 * 分页查询设备
	 * @param page 当前的页码
	 * @param pageSize 当前每页显示的条数
	 * @param direction 排序的方式
	 * @param sort 排序的字段名
	 * @return 符合条件的设备
	 */
	public Page<MachineryEquipment> getStockout5Search(int page, int pageSize,Direction direction,String sort);

	
}
