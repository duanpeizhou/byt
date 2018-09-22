package cn.zectec.contraceptive.management.system.service.impl;

import cn.zectec.contraceptive.management.system.manager.IAreaManager;
import cn.zectec.contraceptive.management.system.manager.IManagerManager;
import cn.zectec.contraceptive.management.system.manager.IRoleManager;
import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.model.Role;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
import cn.zectec.contraceptive.management.system.security.service.SecurityContext;
import cn.zectec.contraceptive.management.system.service.IManagerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;


@Service
public class ManagerServiceImpl implements IManagerService{
	@Autowired
	private IManagerManager managerManager;
	@Autowired
	private IAreaManager areaManager;
	@Autowired
	private IRoleManager roleManager;
	/**
	 * 通过 id查询管理员
	 * @param id 要查询管理员的id
	 * @return 符合条件的管理员
	 */
	@Override
	public Manager getManagerById(long id) {
		return managerManager.findOne(id);
	}
	/**
	 * 记录当前登录管理员的信息
	 * @param username 当前要登录的管理员的用户名
	 * @param password 当前要登录的管理员的密码
	 * @return 登录的管理员
	 */
	@Override
	public Manager login(String username, String password) {
		SearchFilter searchFilter1 = new SearchFilter("username",username);
		SearchFilter searchFilter2 = new SearchFilter("password",password);
		SearchFilter searchFilter3 = new SearchFilter("enable",true);
		Manager manager = managerManager.findBySearchFilters(searchFilter1,searchFilter2,searchFilter3);
		try {
			if(manager != null){
				manager = managerManager.findOneOnLock(manager.getId());
				manager.setLastLoginTime(new Date());
				manager.setIp((String)SecurityContext.attr(SecurityContext.IP));
				manager.setLoginTimes(manager.getLoginTimes()+1);
				managerManager.update(manager);
			}
		} catch (Exception e) {
		}
		SecurityContext.registerManager(manager);
		return manager;
	}
	/**
	 * 分页查询管理员
	 * @param page 当前的页码
	 * @param pageSize 当前页码要显示的条数
	 * @return 符合条件的管理员
	 */
	@Override
	public Page<Manager> getManager(int page, int pageSize) {
		return managerManager.getManager(page,pageSize);
	}
	/**
	 * 添加管理员
	 * @param manager 添加管理员信息
	 */
	@Override
	public void addManager(Manager manager) {
		if (manager.getUsername() != null && manager.getUsername().length() > 0) {
			String md5DigestAsHex = DigestUtils.md5DigestAsHex(manager.getUsername().getBytes());
			manager.setUsername(md5DigestAsHex);
            String password = DigestUtils.md5DigestAsHex(manager.getPassword().getBytes());
            manager.setPassword(password);
        }
		if(manager.getSuperManager()){
			manager.setCounty(null);
			manager.setTownshipStreet(null);
			manager.setDepartment(null);
			managerManager.addMananger(manager);
		}else if(manager.getTownshipStreet().getId()==-1){
			Area countryArea=areaManager.findOne(manager.getCounty().getId());
			manager.setCounty(countryArea);
			manager.setDepartment(null);
			manager.setTownshipStreet(null);
			managerManager.add(manager);
		}else{
			Area countryArea=areaManager.findOne(manager.getCounty().getId());
			Area townArea=areaManager.findOne(manager.getTownshipStreet().getId());
			manager.setCounty(countryArea);
			manager.setTownshipStreet(townArea);
			managerManager.addMananger(manager);
		}
	}
	/**
	 * 更新管理信息
	 * @param manager 更新管理员的信息
	 */
	@Override
	public void update(Manager manager) {
		Manager manager1 =managerManager.findOne(manager.getId());
		if (manager1.getPassword().equals(manager.getPassword())) {
			manager.setLastModifyPasswordTime(new Date());
			BeanUtils.copyProperties(manager1, manager,new String[]{"username","password","name","superManager","enable","townshipStreet","county","remark","lastModifyPasswordTime"});
		}else{
			BeanUtils.copyProperties(manager1, manager,new String[]{"username","password","name","superManager","enable","townshipStreet","county","remark"});
		}
		managerManager.update(manager);
	}
	/**
	 * 通过id删除管理员
	 * @param id
	 * @return 是否成功删除
	 */
	@Override
	public boolean delete(long id) {
		try {
			managerManager.delete(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 通过管理的用户名查询管理员
	 * @param username 管理员的用户名
	 * @return 符合条件的管理员
	 */
	@Override
	public Manager getManagerByUsername(String username) {
		SearchFilter searchFilter = new SearchFilter("username",username);
		return managerManager.findBySearchFilters(searchFilter);
	}
	/**
	 * 通过部门的id获取此部门的管理员
	 * @param page 当前要显示的页码
	 * @param pageSize 当前每页显示的条数
	 * @param id 部门id
	 * @return 符合条件的管理员
	 */
	@Override
	public Page<Manager> getManagerByDep(int page, int pageSize, int id) {
	
		return managerManager.getManager(page,pageSize,id);
	}
	/**
	 * 通过管理员的用户名来模糊分页查询管理员
	 * @param page 当前显示的页码
	 * @param pageSize 当前每页显示的条数
	 * @param username 用户
	 * @return 符合条件的管理员
	 */
	@Override
	public Page<Manager> getManager(int page, int pageSize, String username) {
		return managerManager.getManager(page,pageSize,username);
	}
	/**
	 * 更新管理员的状态
	 * @param id 管理员的id
	 * @param roleId
	 */
	@Override
	public void updateState(Long id, int roleId) {
		Manager manager = managerManager.findOne(id);
		boolean b =false;
		Set<Role> roles =manager.getRole();
		Role role=null;
		for (Iterator iterator = roles.iterator(); iterator.hasNext();) {
			role = (Role) iterator.next();
			if (role.getId()==id) {
				b=true;
				break;
			}
		}
		if (b) {
			roles.remove(role);
		}else {
			role.setId(id);
			roles.add(role);
			manager.setRole(roles);
		}
		managerManager.update(manager);
	}
	/**
	 * 更改管理员的密码
	 * @param id 管理员的id
	 * @param newPassword 新密码
	 * @return 是否修改成功
	 */
	@Override
	public boolean updatePassword(long id, String newPassword) {
		return managerManager.updatePassword(id, newPassword);
	}
	/**
	 * 修改管理员的所拥有的角色
	 * @param managerId 管理的id 
	 * @param roleId 角色id
	 * @return 是否修改成功
	 */
	@Override
	public boolean updateManagerPermission(long managerId, long roleId) {
		System.out.println(managerId+";;;;;"+roleId);
		Manager m=managerManager.findOne(managerId);
		Role r=roleManager.findOne(roleId);
		if(m.getRole().contains(r)){
			m.getRole().remove(r);
		}else{
			m.getRole().add(r);
		}
		return managerManager.udpateManagerPermission(m);
	}
	/**
	 * 通过角色id分页查询此角色所拥有的管理员
	 * @param page 当前显示的页码
	 * @param pageSize 当前每页显示的条数
	 * @param roleId 角色id
	 * @return 符合条件的管理员
	 */

	@Override
	public Page<Manager> getManagersByRoleId(int page, int pageSize, long roleId) {
		Role role=roleManager.findOne(roleId);
		return managerManager.getManagersByRoleId(page, pageSize, role);
	}

}
