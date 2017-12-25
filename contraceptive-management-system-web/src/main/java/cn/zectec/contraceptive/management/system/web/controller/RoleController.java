package cn.zectec.contraceptive.management.system.web.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zectec.contraceptive.management.system.model.Menu;
import cn.zectec.contraceptive.management.system.model.Role;
import cn.zectec.contraceptive.management.system.service.IMenuService;
import cn.zectec.contraceptive.management.system.service.IRoleService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilter;

@Controller
public class RoleController {
	
	@Autowired
	private IRoleService roleService ;
	
	@Autowired
	private IMenuService menuService;

	
	@RequestMapping("/rolemanagerui")
	public String getRoleUi()
	{
		return "indexDatagrid/roleManager";
	}
	@JsonFilter(pojo=Role.class,allow={"id","name","state","description"})
	@RequestMapping("/roleList")
	public Object  getRole(@RequestParam(value="page",defaultValue="1")int page,@RequestParam(value="rows",defaultValue="20")int pageSize){
		Page<Role> pageStrategy=roleService.getRole(page-1, pageSize);
		JqueryUiDatagardPageModel<Role> datagrid=new JqueryUiDatagardPageModel<Role>(pageStrategy.getTotalElements(), pageStrategy.getContent());
		datagrid.setRows(pageStrategy.getContent());
		return datagrid;

	}
	/*@JsonFilters({
		@JsonFilter(pojo=Manager.class,allow={"username","name","id","department"}),
		@JsonFilter(pojo=Department.class,allow="name")
	})
	@RequestMapping("/managerlistofrole")
	public Object getUserByRole(@RequestParam(value="page",defaultValue="1")int page,@RequestParam(value="rows",defaultValue="25")int pageSize,@RequestParam(value="id") long roleId)
	{
		Page<Manager> managers = roleService.getUserByRole(page-1, pageSize,roleId);
		JqueryUiDatagardPageModel<Manager> datagrid=new JqueryUiDatagardPageModel<Manager>(managers.getTotalElements(), managers.getContent());
		datagrid.setRows(managers.getContent());
		return datagrid;
	}*/
	@RequestMapping("/addrole")
	@ResponseBody
	public Map<String, Object> roleAdd(Role role)
	{
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("success", roleService.addRole(role));
		return map;
	}
	@RequestMapping("/updaterole")
	@ResponseBody
	public Map<String, Object> roleUpdate(Role role)
	{
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("success", roleService.updateRole(role));
		return map;
	}
	@RequestMapping("/deleterole")
	@ResponseBody
	public Map<String, Object> roleDelete(@RequestParam(value = "id") long id)
	{
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("success", roleService.deleteRole(id));
		return map;
	}

	@RequestMapping("/searchbyname")
	@JsonFilter(pojo=Role.class,allow={"id","name","state","description"})
	public Object getRoleByName(@RequestParam(value="page",defaultValue="1")int page,
			@RequestParam(value="rows",defaultValue="20")int pageSize,@RequestParam(value = "name") String name)
	{
		Page<Role> roles = roleService.getRoleByName(page-1,pageSize,name);
		JqueryUiDatagardPageModel<Role> roleDategard = new JqueryUiDatagardPageModel<Role>(roles.getTotalElements(), roles.getContent());
		return roleDategard;
	}
	
	@RequestMapping("/updaterolestate")
	@ResponseBody
	public Map<String, Object> updateState(long id)
	{
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("success", roleService.updateState(id));
		return map;
	}
	
	@RequestMapping("/loadmenus")
	@JsonFilter(pojo=Menu.class,allow={"id","name","childMenu"})
	public Object loadMenu(){
		List<Menu> menus = menuService.getMenuByLevel(0);
		return menus.get(0);
	}
	
	@RequestMapping("/savepermission")
	@ResponseBody
	public Map<String, Object> savePermission(@RequestParam("id")long roleId,@RequestParam("menus[]")long[] menusid){
		Map<String, Object> result = new HashMap<String, Object>();
		List<Menu> menus = menuService.findMenusById(menusid);
		Role role = roleService.getRoleById(roleId);
		if(role==null){
			result.put("success", false);
			return result;
		}
		try {
			roleService.addPermission(role,menus);
			result.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
		}
		return result;
	}
	
	@RequestMapping("/loadpermission")
	@JsonFilter(pojo=Menu.class,allow={"id","name"})
	public Object loadPermission(@RequestParam("id") long roleId){
		Set<Menu> list = roleService.getBelongMenus(roleId);
		return list;
	}
	
}
