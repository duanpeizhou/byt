package cn.zectec.contraceptive.management.system.manager;

import org.springframework.data.domain.Page;

import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.model.Role;

public interface IManagerManager extends IBaseManager<Manager, Long>{

	public Manager findOneOnLock(long id);

	public Page<Manager> getManager(int page, int pageSize);

	public Page<Manager> getManager(int page, int pageSize, int id);

	public Page<Manager> getManager(int page, int pageSize, String username);
	
	public Page<Manager> getManagersByRoleId(int page,int pageSize,Role role);
	
	public void addMananger(Manager manager);
	
	public boolean updatePassword(long id,String newPassword);
	
	public boolean udpateManagerPermission(Manager manager);
	

}
