package cn.zectec.contraceptive.management.system.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.Area.Level;
import cn.zectec.contraceptive.management.system.service.IAreaService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilter;
import cn.zectec.contraceptive.management.system.web.util.AreaFilter;

@Controller
public class AreaController {
	@Resource
	private IAreaService areaService;
	/**
	 * @描述 所有的城市
	 * @return
	 */
	@JsonFilter(pojo = Area.class, allow = { "id", "name" })
	@RequestMapping(value = "/cityMenu")
	public Object getCity() {
		return areaService.getCityByLevel(Level.City);
	}

	/**
	 * @描述 所有区县tree
	 * @param area
	 * @return
	 */
	@ResponseBody
	@JsonFilter(pojo = Area.class, allow = { "id", "name", "parentArea",
			"order" })
	@RequestMapping(value = "/allCountryTree")
	public Object getAllCountry(@RequestParam(value = "id") int id,
			@RequestParam(value = "country") Level country) {
		return areaService.getAreaByLevel(id, country);
	}

	/**
	 * @描述 所有区县
	 * @param area
	 * @return
	 */
	@ResponseBody
	@JsonFilter(pojo = Area.class, allow = { "id", "name", "parentArea",
			"order" })
	@RequestMapping(value = "/allCountry")
	public Object getCountry(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "25") int pageSize) {
		Page<Area> pageModle = areaService.getAreaByLevel(page - 1, pageSize,
				Level.County);
		JqueryUiDatagardPageModel<Area> datagrid = new JqueryUiDatagardPageModel<>(
				pageModle.getTotalElements(), pageModle.getContent());
		datagrid.setRows(pageModle.getContent());
		return datagrid;
	}

	/**
	 * @描述 所有街道
	 * @param area
	 * @return
	 */
	@ResponseBody
	@JsonFilter(pojo = Area.class, allow = { "id", "name", "parentArea",
			"order" })
	@RequestMapping(value = "/getAllTownshipStreet")
	public Object allTownshipStreet(
			@RequestParam(value = "pid", defaultValue = "-1") long pid,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "25") int pageSize) {
		if (pid == -1) {
			Page<Area> pageModle = (Page<Area>) areaService.getAreaByLevel(
					page - 1, pageSize, Level.TownshipStreet);
			JqueryUiDatagardPageModel<Area> datagrid = new JqueryUiDatagardPageModel<>(
					pageModle.getTotalElements(), pageModle.getContent());
			datagrid.setRows(pageModle.getContent());
			return datagrid;
		} else {
			Page<Area> pageModle = (Page<Area>) areaService.getAreaByLevel(pid,
					Level.TownshipStreet, page - 1, pageSize);
			JqueryUiDatagardPageModel<Area> datagrid = new JqueryUiDatagardPageModel<>(
					pageModle.getTotalElements(), pageModle.getContent());
			datagrid.setRows(pageModle.getContent());
			return datagrid;
		}
	}

	/**
	 * @描述 所有街道路口
	 * @param area
	 * @return
	 */
	@ResponseBody
	@JsonFilter(pojo = Area.class, allow = { "id", "name", "parentArea",
			"order" })
	@RequestMapping(value = "/getAllCommunty")
	public Object getAllCommunty(
			@RequestParam(value = "pid", defaultValue = "-1") long pid,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "25") int pageSize) {
		if (pid == -1) {
			Page<Area> pageModle = (Page<Area>) areaService.getAreaByLevel(
					page - 1, pageSize, Level.Community);
			JqueryUiDatagardPageModel<Area> datagrid = new JqueryUiDatagardPageModel<>(
					pageModle.getTotalElements(), pageModle.getContent());
			datagrid.setRows(pageModle.getContent());
			return datagrid;
		} else {
			Page<Area> pageModle = (Page<Area>) areaService.getAreaByLevel(pid,
					Level.Community, page - 1, pageSize);
			JqueryUiDatagardPageModel<Area> datagrid = new JqueryUiDatagardPageModel<>(
					pageModle.getTotalElements(), pageModle.getContent());
			datagrid.setRows(pageModle.getContent());
			return datagrid;
		}
	}

	/**
	 * @描述 删除区县街道路口
	 * @param area
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteCommunty")
	public boolean deleteCommunty(Long id) {
		return areaService.deleArea(id);
	}

	/**
	 * @描述 添加区县街道路口
	 * @param area
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addcommunty")
	public boolean addaddcommunty(Area area) {
		return areaService.addArea(area);
	}

	/**
	 * @描述 添加区县街道
	 * @param area
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addTownshipStreet")
	public boolean addTownshipStreet(Area area) {
		return areaService.addArea(area);
	}

	/**
	 * @描述 删除区县街道
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteTownshipStreet")
	public boolean deleteTownshipStreet(Long id) {
		return areaService.deleArea(id);
	}

	/**
	 * @描述 添加区县
	 * @param area
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addAreaDictionary")
	public boolean addAreaDictionary(Area area) {
		return areaService.addArea(area);
	}

	/**
	 * @描述 修改区县街道路口
	 * @param area
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateCommunty")
	public boolean updateCommunty(Area area) {
		area.getName();
		return areaService.updateArea(area);
	}

	/**
	 * @描述 修改区县街道
	 * @param area
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upTownshipStreet")
	public boolean upTownshipStreet(Area towns) {
		towns.getName();
		return areaService.updateTown(towns);
	}

	/**
	 * @描述 修改区县
	 * @param area
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upAreaDictionary")
	public boolean upAreaDictionary(Area area) {
		area.getName();
		return areaService.updateArea(area);
	}

	/**
	 * @描述 删除区县
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteAreaDictionary")
	public boolean deleAreaDictionary(Long id) {
		return areaService.deleArea(id);
	}
	/**
	 * @描述 工具类辅助生成特定tree格式
	 * @param id
	 * @return
	 */
	public class JsonTreeArea {
		private long id;
		private String text;
		private Object children[];

		public long getId() {
			return id;
		}

		public Object[] getChildren() {
			return children;
		}

		public void setChildren(Object[] children) {
			this.children = children;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}
	}

	/**
	 * @描述 工具类辅助生成特定tree格式
	 * @param id
	 * @return
	 */
	@JsonFilter(pojo = Area.class, allow = { "id", "name" })
	@RequestMapping(value = "/countryMenu")
	public Object getCountry(
			@RequestParam(value = "id", defaultValue = "-1") int id) {
		List<Area> oldResult = null;
		if (id == -1) {
			oldResult = areaService.getCityByLevel(Level.County);
		} else {
			oldResult = areaService.getAreaByLevel(id, Level.TownshipStreet);
		}
		List<JsonTreeArea> newResult = null;
		if (oldResult != null && oldResult.size() > 0) {
			newResult = new ArrayList<JsonTreeArea>();
			for (Area area : oldResult) {
				JsonTreeArea jta = new JsonTreeArea();
				jta.setId(area.getId());
				jta.setText(area.getName());
				newResult.add(jta);
			}
		}
		return (newResult != null) ? newResult : oldResult;
	}

	/**
	 * @描述 获取三级节点,村级字典
	 * @param id
	 * @return
	 */
	@JsonFilter(pojo = Area.class, allow = { "id", "name", "childAreas" })
	@RequestMapping(value = "/countryMenuOne")
	public Object getCountryAll(
			@RequestParam(value = "id", defaultValue = "-1") int id) {
		List<Area> oldResult = null;
		if (id == -1) {
			oldResult = areaService.getCityByLevel(Level.County);
		} else {
			oldResult = areaService.getAreaByLevel(id, Level.County);
		}
		return AreaFilter.filter(oldResult,2);
	}
	@JsonFilter(pojo = Area.class, allow = { "id", "name" })
	@RequestMapping(value="/countryMenuOptions")
	public Object getCountryOptions(@RequestParam(value = "id") int id){
		return areaService.getAreaByLevel(id, Level.County);
	}
	@JsonFilter(pojo = Area.class, allow = { "id", "name" })
	@RequestMapping(value="/getAreaById")
	public Object findAreaById(@RequestParam(value="id",required=true)long id){
		return Arrays.asList(areaService.getAreaById(id));
	}
	/**
	 * @描述 获取节点下所有街道
	 * @param id
	 * @return
	 */
	@JsonFilter(pojo = Area.class, allow = { "id", "name" })
	@RequestMapping(value = "/townshipStreetMenu")
	public Object getTownshipStreet(@RequestParam(value = "id") int id) {
		return areaService.getAreaByLevel(id, Level.TownshipStreet);
	}
	/**
	 * @描述 获取节点下所有街道路口
	 * @param id
	 * @return
	 */
	@JsonFilter(pojo = Area.class, allow = { "id", "name" })
	@RequestMapping(value = "/communityMenu")
	public Object getCommunity(@RequestParam(value = "id") long id) {
		return areaService.getAreaByLevel(id, Level.Community);
	}
	/**
	 * 通过夫地点得到地区
	 * @param parentId
	 * @return
	 */
	@JsonFilter(pojo=Area.class,allow={"id","name"})
	@RequestMapping(value="/getAreaByParentAreaId")
	public Object getAreasByParentAreaId(long parentId){
		return areaService.getAreasByParentAreaId(parentId);
	}
}
