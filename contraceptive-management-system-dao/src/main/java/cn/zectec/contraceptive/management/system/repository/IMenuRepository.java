package cn.zectec.contraceptive.management.system.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.zectec.contraceptive.management.system.model.Menu;
import cn.zectec.contraceptive.management.system.repository.util.IBaseRepository;

public interface IMenuRepository extends IBaseRepository<Menu>{

	@Modifying
	@Query("delete from Menu m where m.id=?1")
	public void deleteMenu(long id);

}
