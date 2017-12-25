package cn.zectec.contraceptive.management.system.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.Area.Level;

public interface IAreaService {
	/**
	 * @描述： 获取所有城市通过 level等级
	 * @param level
	 * @return
	 */
	public List<Area> getCityByLevel(Level level);
	/**
	 * @描述：  获取所有城市通过 level等级一级上级ID
	 * @param id
	 * @param country
	 * @return
	 */
	public List<Area> getAreaByLevel(long id, Level country);
	/**
	 * 描述: 获取所有城市通过country 一级所属省市id
	 * @param parentId
	 * @return
	 */
	public List<Area> getAreasByParentAreaId(long parentId);
	/**
	 * @描述：获取所有城市通过 level等级（带分页查询）
	 * @param page
	 * @param pageSize
	 * @param level
	 * @return
	 */
	public Page<Area> getAreaByLevel(int page, int pageSize, Level level);
	/**
	 * @描述：添加 省市
	 * @param area
	 * @return
	 */
	public boolean addArea(Area area);
	/**
	 * @描述：删除省市对象
	 * @param area
	 */
	public void deleteArea(Area area);
	/**
	 * @描述：删除省市（通过获取id）
	 * @param area
	 */
	public boolean deleArea(Long id);
	/**
	 * @描述：修改省市对象
	 * @param area
	 */
	public boolean updateArea(Area area);
	/**
	 * @描述：获取所有城市通过 level等级（带分页查询）
	 * @param page
	 * @param pageSize
	 * @param level
	 * @return
	 */
	public Page<Area> getAreaByLevel(long pid, Level TownshipStreet, int page,
			int pageSize);
	/**
	 * @描述：添加乡镇
	 * @param area
	 * @return
	 */
	public boolean addAreaTownshipStreet(Area area);
	/**
	 * @描述：修改乡镇
	 * @param area
	 * @return
	 */
	public boolean updateTown(Area towns);
	/**
	 * @描述：获取省市 （通过上级ID）
	 * @param area
	 * @return
	 */
	public Area getAreaById(long id);

}
