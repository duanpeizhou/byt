package cn.zectec.contraceptive.management.system.repository;

import org.springframework.data.jpa.repository.Query;

import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.repository.util.IBaseRepository;

public interface IAreaRepository extends IBaseRepository<Area>{

	@Query("select max(a.id) from Area a where a.parentArea.id=?1")
	public long getMaxIdByParentArea(long id);
}
