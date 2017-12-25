package cn.zectec.contraceptive.management.system.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.zectec.contraceptive.management.system.model.ContraceptiveStatistical;
import cn.zectec.contraceptive.management.system.repository.util.IBaseRepository;

public interface IContraceptiveStatisticalRepository extends IBaseRepository<ContraceptiveStatistical>{
	
	
	@Query("select new cn.zectec.contraceptive.management.system.model.ContraceptiveStatistical(SUM(c.total), SUM(c.manTotal),SUM(c.womanTotal)"
			+ ",SUM(c.countyOfCity),SUM(c.countyOutCity), SUM(c.provinceOutCity), SUM(c.otherProvinces),c.contraceptive)"
			+ "from ContraceptiveStatistical c where c.area.parentArea.id=?1 and statisticalDate between ?2 and ?3 group by c.contraceptive")
	public List<ContraceptiveStatistical> statistByParentArea(long id,Date beginTime,Date endTime);
	
	@Query("select new cn.zectec.contraceptive.management.system.model.ContraceptiveStatistical(SUM(c.total), SUM(c.manTotal),SUM(c.womanTotal)"
			+ ",SUM(c.countyOfCity),SUM(c.countyOutCity), SUM(c.provinceOutCity), SUM(c.otherProvinces),c.contraceptive)"
			+ "from ContraceptiveStatistical c where c.area.id=?1 and statisticalDate between ?2 and ?3 group by c.contraceptive")
	public List<ContraceptiveStatistical> statistByArea(long id,Date beginTime,Date endTime);
	
	@Modifying
	@Query("delete from ContraceptiveStatistical a where a.area.id=?1")
	public void deleteByAreaId(Long id);
}
