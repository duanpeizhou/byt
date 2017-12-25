package cn.zectec.contraceptive.management.system.web.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateChangeRecord;
import cn.zectec.contraceptive.management.system.model.Menu;
import cn.zectec.contraceptive.management.system.service.IMachineryEquipmentStateChangeRecordService;
import cn.zectec.contraceptive.management.system.service.IMenuService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilter;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilters;

@Controller
public class MenuController {
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IMachineryEquipmentStateChangeRecordService achineryEquipmentStateChangeRecordService;
	private static Logger logger = Logger.getLogger(MenuController.class);

	@RequestMapping("/index")
	public String getIndex() {
		return "Index";
	}

	@JsonFilter(pojo = Menu.class, allow = { "name", "url", "childMenu","id" })
	@RequestMapping("/menus")
	public Object getMenus(HttpSession session) {
		Menu menu = (Menu)session.getAttribute("manager_menu");
		logger.debug("当前用户的menu为"+menu);
		return menu;
	}
	@JsonFilter(pojo = Menu.class, allow = { "name", "url", "childMenu","id" })
	@RequestMapping("/menuTree")
	public Object getMenus() {
		return menuService.getMenuByLevel(0).get(0);
	}
	/**
	 * 跳转到菜单管理列表
	 * @return
	 */
	@RequestMapping(value="/menuManager")
	public String menuManager(){
		return "indexDatagrid/menuManager";
	}
	/**
	 * 加载菜单列表数据
	 * @return
	 */
	@JsonFilter(pojo=Menu.class,ignore="parentMenu")
	@RequestMapping(value="/menuManagerDatagrid")
	public Object getMenuManager(@RequestParam(value="page",defaultValue="1")int page,
			@RequestParam(value="rows",defaultValue="25")int pageSize,
			@RequestParam(value="id",defaultValue="-1")String id){
		Page<Menu> pageModel=menuService.getSpecifiedMenu(page-1, pageSize, id);
		JqueryUiDatagardPageModel<Menu> datagrid=new JqueryUiDatagardPageModel<Menu>(pageModel.getTotalElements(), pageModel.getContent());
		datagrid.setRows(pageModel.getContent());
		return datagrid;
	}

	/**
	 * 更改菜单
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/udpateMenu")
	public boolean updateMenu(Menu menu){
		return menuService.updateMenu(menu);
	}
	/**
	 * 删除菜单
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteMenu")
	public Object deleteMenu(long id){
		return menuService.deleteMenu(id);
	}
	
	@RequestMapping(value = "/currentTimeHome")
	public String currentTimeHome() {
		return "indexDatagrid/CurrentTimeHome";
	}

	@JsonFilters({
			@JsonFilter(pojo = MachineryEquipmentStateChangeRecord.class, allow = {	"state", "hanpenDate", "detail", "machineryEquipment" }),
			@JsonFilter(pojo = MachineryEquipment.class, allow = {"deviceNo","terminalType","area","distributionPoints"}),
			@JsonFilter(pojo=Area.class,allow={"name","parentArea"})
	})
	@RequestMapping(value = "/machineryEquipmentStateChangeRecords", method = RequestMethod.POST)
	public Object getAllME(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value="order",defaultValue="-1")String direStr,
			@RequestParam(value="sort",defaultValue="-1")String sortName,
			@RequestParam(value = "rows", defaultValue = "20") int pageSize) {
		Direction direction=null;
		if(direStr.equals("-1")){
			sortName="hanpenDate";
			direction=Direction.DESC;
		}else if("asc".equals(direStr)){
			direction=Direction.ASC;
		}else{
			direction=Direction.DESC;
		}
		Page<MachineryEquipmentStateChangeRecord> pageMESCR = achineryEquipmentStateChangeRecordService
				.getAllME(page - 1, pageSize,direction,sortName);
		JqueryUiDatagardPageModel<MachineryEquipmentStateChangeRecord> datagrid = new JqueryUiDatagardPageModel<MachineryEquipmentStateChangeRecord>(
				pageMESCR.getTotalElements(), pageMESCR.getContent());
		datagrid.setRows(pageMESCR.getContent());
		return datagrid;
	}
	/**
	 * @描述 添加菜单项
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addMenu")
	public boolean addMenu(Menu menu){
		return menuService.addMenu(menu);
	}
	
	@JsonFilter(pojo=Menu.class,ignore="parentMenu")
	@RequestMapping("/searchmenubyname")
	public Object getMenuByName(@RequestParam(value="page",defaultValue="1")int page,@RequestParam(value="rows",defaultValue="25")int pageSize,@RequestParam(value = "name") String name)
	{
		Page<Menu> pageModelSearch=menuService.getMenuByName(page-1,pageSize,name);
		JqueryUiDatagardPageModel<Menu> menuDatagardPageModel = new JqueryUiDatagardPageModel<>(pageModelSearch.getTotalElements(), pageModelSearch.getContent());
		return menuDatagardPageModel;
	}
}
