package cn.zectec.contraceptive.management.system.manager.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.IAreaManager;
import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.Area.Level;
import cn.zectec.contraceptive.management.system.repository.IAgeStatisticalRepository;
import cn.zectec.contraceptive.management.system.repository.IAreaRepository;
import cn.zectec.contraceptive.management.system.repository.IAreaStatisticalRepository;
import cn.zectec.contraceptive.management.system.repository.IContraceptiveStatisticalRepository;
import cn.zectec.contraceptive.management.system.repository.util.DynamicSpecifications;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;

@Component
public class AreaManagerImpl extends SimpleBaseManagerImpl<Area> implements
		IAreaManager {
	private IAreaRepository areaRepository;
	
	@Autowired
	private IAgeStatisticalRepository ageStatisticalRepository;
	@Autowired
	private IAreaStatisticalRepository areaStatisticalRepository;
	@Autowired
	private IContraceptiveStatisticalRepository contraceptiveStatisticalRepository;
	

	@Autowired
	public AreaManagerImpl(IAreaRepository baseRepository) {
		super(baseRepository);
		this.areaRepository = baseRepository;
	}

	public List<Area> getCityByLevel(Level level) {
		SearchFilter filter = new SearchFilter("level", level);
		Specification<Area> spec = DynamicSpecifications.bySearchFilter(filter);
		return areaRepository.findAll(spec);
	}

	@Override
	public List<Area> getAreaByLevel(long id, Level country) {
		List<SearchFilter> filters = new ArrayList<SearchFilter>();
		SearchFilter filter = new SearchFilter("parentArea",
				areaRepository.findOne(id));
		SearchFilter filter2 = new SearchFilter("level", country);
		filters.add(filter2);
		filters.add(filter);
		Specification<Area> spec = DynamicSpecifications
				.bySearchFilters(filters);
		return areaRepository.findAll(spec);
	}

	@Override
	public Page<Area> getAreaByLevel(int page, int pageSize, Level level) {
		SearchFilter filter = new SearchFilter("level", level);
		return this.findBySearchFilters(Arrays.asList(filter), page, pageSize);
	}

	@Override
	public  boolean addArea(Area area) {
		try {
			Long parentId = area.getParentArea().getId();
			Area parentArea = this.findOne(parentId);
			if(parentArea == null){
				return false;
			}
			area.setParentArea(parentArea);
			area = areaRepository.save(area);
			area.setNo(area.getId());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleArea(Long id) {
		try {
			ageStatisticalRepository.deleteByAreaId(id);
			areaStatisticalRepository.deleteByAreaId(id);
			contraceptiveStatisticalRepository.deleteByAreaId(id);
			areaRepository.delete(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateArea(Area area) {
		try {
			areaRepository.save(area);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// @Override
	// public List<Area> getCounty(Level county) {
	// SearchFilter searchFilter3=new SearchFilter("level","County");
	// return areaRepository.findAll
	// }
	@Override
	public Page<Area> getAreaByLevel(long pid, Level TownshipStreet, int page,
			int pageSize) {
		List<SearchFilter> filters = new ArrayList<SearchFilter>();
		SearchFilter filter = new SearchFilter("parentArea",
				areaRepository.findOne(pid));
		SearchFilter filter2 = new SearchFilter("level", TownshipStreet);
		filters.add(filter2);
		filters.add(filter);
		Specification<Area> spec = DynamicSpecifications
				.bySearchFilters(filters);
		return this.areaRepository.findAll(spec,
				new PageRequest(page, pageSize));
	}

	@Override
	public boolean addAreaTownshipStreet(Area area) {
		try {
			areaRepository.save(area);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean updateTown(Area towns) {
		areaRepository.save(towns);
		return true;
	}

	@Override
	public Area getAreaById(long id) {
		return this.findOne(id);
	}
}
