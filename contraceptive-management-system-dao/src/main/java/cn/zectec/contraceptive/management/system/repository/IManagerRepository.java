package cn.zectec.contraceptive.management.system.repository;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.model.Role;
import cn.zectec.contraceptive.management.system.repository.util.IBaseRepository;

public interface IManagerRepository extends IBaseRepository<Manager> {
	
	@Lock(LockModeType.OPTIMISTIC)
	@Query("select m from Manager m where m.id=?1")
	public Manager findOneOnLock(long id);
	@Query("select m from Manager m,IN(m.role) r where r=?1")
	public Page<Manager> findManagerByRole(Role role,Pageable pageable);

}
