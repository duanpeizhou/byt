package cn.zectec.contraceptive.management.system.manager;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.model.Menu;
import cn.zectec.contraceptive.management.system.model.Role;

public interface IRoleManager extends IBaseManager<Role, Long>{

	Page<Role> getRole(int page, int pageSize2);
	Page<Role> getRoleByName(int page , int pageSize , String name);
	Page<Manager> getUserByRoleId(int page, int pageSize, long roleId);
	public void addPermission(Role role, List<Menu> menus);

}
