package cn.zectec.contraceptive.management.system.manager;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.Area.Level;

public interface IAreaManager extends IBaseManager<Area, Long>{
	public List<Area> getCityByLevel(Level city);
	public Page<Area> getAreaByLevel(int page,int pageSize,Level level);
	public List<Area> getAreaByLevel(long id,Level country);
	public boolean addArea(Area area);
	public boolean deleArea(Long id);
	public boolean updateArea(Area area);
	public Page<Area> getAreaByLevel(long pid,Level townshipStreet,int page,int pageSize);
	public boolean addAreaTownshipStreet(Area area); 
	public boolean updateTown(Area towns);
	public Area getAreaById(long id);
	
}
