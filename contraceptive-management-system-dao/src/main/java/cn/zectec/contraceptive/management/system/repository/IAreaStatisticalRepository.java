package cn.zectec.contraceptive.management.system.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.zectec.contraceptive.management.system.model.AreaStatistical;
import cn.zectec.contraceptive.management.system.repository.util.IBaseRepository;

public interface IAreaStatisticalRepository extends IBaseRepository<AreaStatistical>{
	@Query("select new cn.zectec.contraceptive.management.system.model.AreaStatistical(SUM(c.total), SUM(c.manTotal),SUM(c.womanTotal)"
			+ ",SUM(c.countyOfCity),SUM(c.countyOutCity), SUM(c.provinceOutCity), SUM(c.otherProvinces),c.area)"
			+ "from AreaStatistical c where c.area.parentArea.id=?1 and statisticalDate between ?2 and ?3 group by c.area")
	public List<AreaStatistical> statistByParentArea(long id,Date beginTime,Date endTime);
	
	@Query("select new cn.zectec.contraceptive.management.system.model.AreaStatistical(SUM(c.total), SUM(c.manTotal),SUM(c.womanTotal)"
			+ ",SUM(c.countyOfCity),SUM(c.countyOutCity), SUM(c.provinceOutCity), SUM(c.otherProvinces),c.area)"
			+ "from AreaStatistical c where c.area.id=?1 and statisticalDate between ?2 and ?3 group by c.area")
	public List<AreaStatistical> statistByArea(long id,Date beginTime,Date endTime);
	
	@Modifying
	@Query("delete from AreaStatistical a where a.area.id=?1")
	public void deleteByAreaId(Long id);
}
