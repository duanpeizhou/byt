package cn.zectec.contraceptive.management.system.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import cn.zectec.contraceptive.management.system.manager.IAreaManager;
import cn.zectec.contraceptive.management.system.manager.IMachineryEquipmentManager;
import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.Area.Level;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
import cn.zectec.contraceptive.management.system.service.IAreaService;
@Service
public class AreaServiceImpl implements IAreaService{
	@Autowired
	private IAreaManager areaManager;
	@Autowired
	private IMachineryEquipmentManager machineryEquipmentManager;

	
	/**
	 * @描述： 获取所有城市通过 level等级
	 * @param level 城市等级
	 * @return 通过 level等级获得的所有的城市
	 */
	@Override
	public List<Area> getCityByLevel(Level level) {
		return areaManager.getCityByLevel(level);
	}
	/**
	 * @描述：  通过 level等级以及上级ID获取所有城市
	 * @param id 市区id
	 * @param country 区县级
	 * @return 城市
	 */
	@Override
	public List<Area> getAreaByLevel(long id, Level country) {
		return areaManager.getAreaByLevel(id, country);
	}
	/**
	 * @描述：删除省市（通过获取id）
	 * @param 城市id
	 * @return true 删除成功   false 删除失败
	 */
	@Override
	public boolean deleArea(Long id) {
		Area area = areaManager.getAreaById(id);
		if(area == null){
			return false;
		}else if(area.getLevel() == Level.Community){
			SearchFilter seracherFilters = new SearchFilter("area.id", area.getId());
			List<MachineryEquipment> ms = machineryEquipmentManager.findBySearchFilters(Arrays.asList(seracherFilters));
			if(ms == null || ms.isEmpty()){
				return areaManager.deleArea(id);
			}
		}else if((area.getLevel() == Level.County) || (area.getLevel() == Level.TownshipStreet)){
			if(area.getChildAreas() == null||area.getChildAreas().isEmpty()){
				return areaManager.deleArea(id);
			}
		}else{
			return false;
		}
		return false;
		
	}
	/**
	 * @描述：  获取所有区县通过 level等级
	 * @param page 页码
	 * @param pageSize 每页显示的数据条数
	 * @param level 城市等级
	 * @return 分页查询到的城市
	 */
	@Override
	public Page<Area> getAreaByLevel(int page, int pageSize, Level level) {
		return areaManager.getAreaByLevel(page, pageSize, level);
	}
	/**
	 * @描述：添加区县对象
	 * @param area 地理区域类
	 * @return true 添加成功   false  添加失败
	 */
	@Override
	public boolean addArea(Area area) {
		return areaManager.addArea(area);
	}
	@Override
	public void deleteArea(Area area) {
		
	}
	/**
	 * @描述：修改省市对象
	 * @param area 地理区域类
	 * @return true 修改成功  false 修改失败
	 */
	@Override
	public boolean updateArea(Area area) {
		
		return areaManager.updateArea(area);
	}
	/**
	 * @描述：获取所有城市通过 level等级（带分页查询）
	 * @param page 页码
	 * @param pageSize 每页显示的数据条数
	 * @param level 城市等级
	 * @return 分页查询到的城市
	 */
	@Override
	public Page<Area> getAreaByLevel(long pid, Level TownshipStreet, int page,
			int pageSize) {
		return areaManager.getAreaByLevel(pid, TownshipStreet, page, pageSize);
	}
	/**
	 * @描述：添加乡镇
	 * @param area 地理区域类
	 * @return true 添加乡镇成功  false 添加乡镇失败
	 */
	@Override
	public boolean addAreaTownshipStreet(Area area) {
		return areaManager.addAreaTownshipStreet(area);
	}
	/**
	 * @描述：修改乡镇
	 * @param towns 地理区域类
	 * @return true 修改乡镇成功  false 修改乡镇失败
	 */
	@Override
	public boolean updateTown(Area towns) {
		return areaManager.updateTown(towns);
	}
	/**
	 * @描述：获取省市 （通过上级ID）
	 * @param id 省市id
	 * @return 省市
	 */ 
	@Override
	public Area getAreaById(long id) {
		return areaManager.getAreaById(id);
	}
	/**
	 * @描述：获取所有的区县
	 * @param parentId 区县上级id
	 * @return 所有的区县
	 */ 
	@Override
	public List<Area> getAreasByParentAreaId(long parentId) {
		SearchFilter filter=new SearchFilter("parentArea.id",parentId);
		return areaManager.findBySearchFilters(Arrays.asList(filter));
	}

}
