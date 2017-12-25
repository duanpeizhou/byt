package cn.zectec.contraceptive.management.system.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.Department;
import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.model.Role;
import cn.zectec.contraceptive.management.system.service.IManagerService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilter;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilters;


@Controller
public class ManagerController {
	@Autowired
	private IManagerService managerService;
	
	
	@RequestMapping("/manager")
	public String getIndex() {
		return "indexDatagrid/systemManager/manager";
	}
	@JsonFilters({
		@JsonFilter(pojo=Manager.class,allow={"county","townshipStreet","id","superManager","enable","username","name","ip","lastLoginTime","LastModifyPasswordTime","loginTimes","role","password"}),
		@JsonFilter(pojo=Area.class,allow={"id","name"}),
		@JsonFilter(pojo=Role.class,allow={"id"}),
		@JsonFilter(pojo=Department.class,allow="name")
	})
	@RequestMapping("/managerList")
	public Object  getDatagridManager(@RequestParam(value="page",defaultValue="1")int page,@RequestParam(value="rows",defaultValue="10")int pageSize){
		Page<Manager> pageStrategy=managerService.getManager(page-1, pageSize);
		JqueryUiDatagardPageModel<Manager> datagrid=new JqueryUiDatagardPageModel<Manager>(pageStrategy.getTotalElements(), pageStrategy.getContent());
		datagrid.setRows(pageStrategy.getContent());
		return datagrid;
	}
	@JsonFilters({
		@JsonFilter(pojo=Manager.class,allow={"id","enable","username","name","ip","lastLoginTime","LastModifyPasswordTime","loginTimes"})
	})
	@RequestMapping("/getManagerByDep")
	public Object  getManagerByDep(@RequestParam int id,@RequestParam(value="page",defaultValue="1")int page,@RequestParam(value="rows",defaultValue="10")int pageSize){
		Page<Manager> pageStrategy=managerService.getManagerByDep(page-1, pageSize,id);
		JqueryUiDatagardPageModel<Manager> datagrid=new JqueryUiDatagardPageModel<Manager>(pageStrategy.getTotalElements(), pageStrategy.getContent());
		datagrid.setRows(pageStrategy.getContent());
		return datagrid;
	}
	/**
	 * 添加用户
	 * @param machineryEquipment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/managerAdd")
	public boolean managerAdd(Manager manager){
		try {
			managerService.addManager(manager);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * 改变状态
	 * @param machineryEquipment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateEnable")
	public boolean updateEnable(int id){
		try {
			Manager manager = managerService.getManagerById(id);
			manager.setEnable(!manager.getEnable());
			managerService.update(manager);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 更新用户
	 * @param machineryEquipment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/managerUpdate")
	public boolean managerUpdate(Manager manager){
		try {
			if(manager.getSuperManager()){
				manager.setCounty(null);
				manager.setTownshipStreet(null);
			}
			if(manager.getTownshipStreet()!=null &&manager.getTownshipStreet().getId()==-1){
				manager.setTownshipStreet(null);
			}else if(manager.getCounty()!=null &&manager.getCounty().getId() == -1){
				manager.setCounty(null);
			}
			managerService.update(manager);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	
	}
	
	/**
	 * 删除用户
	 * @param machineryEquipment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteManager")
	public Map<String, Object> deleteManager(@RequestParam(value = "id") long id){
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("success", managerService.delete(id));
		return map;
	}
	
	
	/**
	 * 查找用户
	 * @param machineryEquipment
	 * @return
	 */
	@JsonFilters({
		@JsonFilter(pojo=Manager.class,allow={"county","townshipStreet","id","superManager","enable","username","name","ip","lastLoginTime","LastModifyPasswordTime","loginTimes","role","password"}),
		@JsonFilter(pojo=Area.class,allow={"id","name"}),
		@JsonFilter(pojo=Role.class,allow={"id"}),
		@JsonFilter(pojo=Department.class,allow="name")
	})
	@RequestMapping(value="/searchManager")
	public Object searchManager(
							@RequestParam(value = "username") String username,
							@RequestParam(value="page",defaultValue="1")int page,
							@RequestParam(value="rows",defaultValue="10")int pageSize)
	{
		
		System.out.println("username:"+username);
		Page<Manager> pageStrategy=managerService.getManager(page-1, pageSize,username);
		JqueryUiDatagardPageModel<Manager> datagrid=new JqueryUiDatagardPageModel<Manager>(pageStrategy.getTotalElements(), pageStrategy.getContent());
		return datagrid;
		
	}
	
	
	/**
	 * 更新用户
	 * @param machineryEquipment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateState")
	public boolean updateState(Long id,int roleId){
		try {
			
			managerService.updateState(id,roleId);
			return true;
		} catch (Exception e) {
			return false;
		}
	
	}
	/**
	 * 修改管理员密码
	 * @param id
	 * @param newPassword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updatePassword")
	public boolean updatePassword(@RequestParam(value="id",required=true)long id,
									@RequestParam(value="newPassword",required=true)String newPassword){
		return managerService.updatePassword(id, newPassword);
	}
	/**
	 * 
	 * @param id
	 * @param newPassword
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateManagerRole")
	public boolean updateManagerPermission(@RequestParam(value="managerId",required=true)long managerId,
									@RequestParam(value="roleId",required=true)long roleId){
		return managerService.updateManagerPermission(managerId, roleId);
	}
	@JsonFilters({
		@JsonFilter(pojo=Manager.class,allow="role"),
		@JsonFilter(pojo=Role.class,allow="id")
	})
	@RequestMapping(value="/getManagerById")
	public Object getManagerById(@RequestParam(value="id",required=true)long id){
		return managerService.getManagerById(id);
	}
	@JsonFilters({
		@JsonFilter(pojo=Manager.class,allow={"username","name","id","department"}),
		@JsonFilter(pojo=Department.class,allow="name")
	})
	@RequestMapping(value="/managerlistofrole")
	public Object gtManagerByRoleId(@RequestParam(value="page",defaultValue="1")int page,
									@RequestParam(value="rows",defaultValue="25")int pageSize,
									@RequestParam(value="id",required=true) long roleId){
		Page<Manager> pageModel=managerService.getManagersByRoleId(page-1, pageSize, roleId);
		JqueryUiDatagardPageModel<Manager> datagrid=new JqueryUiDatagardPageModel<Manager>(pageModel.getTotalElements(), pageModel.getContent());
		return datagrid;
	}
}
