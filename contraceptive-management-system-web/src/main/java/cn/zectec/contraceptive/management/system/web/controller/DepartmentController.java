package cn.zectec.contraceptive.management.system.web.controller;


import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zectec.contraceptive.management.system.model.Department;
import cn.zectec.contraceptive.management.system.service.IDepartmentService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilter;

@Controller
public class DepartmentController {
	@Resource
	private IDepartmentService departmentService;

	/**
	 * @描述 部门管理跳转
	 * @return
	 */
	@RequestMapping(value = "/departMentController")
	public String getDepartment() {
		return "indexDatagrid/systemManager/departMent";
	}

	/**
	 * @描述 所有药店
	 * @return
	 */
	@JsonFilter(pojo = Department.class, allow = { "id", "name", "level",
			"childDepartments" })
	@RequestMapping(value = "/getAllDepartment")
	public Object getAllDepartment() {
		int level = 1;
		return departmentService.getDepartment(level);
	}

	/**
	 * @描述 所有药店子节点数据
	 * @return
	 */
	@JsonFilter(pojo = Department.class, allow = { "id", "name", "level" ,"childDepartments","description"})
	@RequestMapping(value = "/getAllDepartmentChild")
	public Object getAllDepartmentChild(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "25") int pageSize,
			@RequestParam(value = "id",defaultValue="-1") long id) {
		if (id==-1) {
			Page<Department> pageModle = departmentService.pageationDepartmentAll(-1, page-1, pageSize);
			JqueryUiDatagardPageModel<Department> datagrid = new JqueryUiDatagardPageModel<>(pageModle.getTotalElements(), pageModle.getContent());
			datagrid.setRows(pageModle.getContent());
			return datagrid;
		} else {
			Page<Department> pageModle = departmentService.pageationDepartmentAll(
					id, page - 1, pageSize);
			JqueryUiDatagardPageModel<Department> datagrid = new JqueryUiDatagardPageModel<>(
					pageModle.getTotalElements(), pageModle.getContent());
			datagrid.setRows(pageModle.getContent());
			return datagrid;
		}
	}
	/**
	 * @描述 添加部门
	 * @param area
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addDepartment")
	public boolean addDepartment(Department department) {
		System.out.println(department.getPranetDepartment().getId());
		return departmentService.addDepartment(department);
	}
	/**
	 * @描述 删除部门
	 * @param area
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteDepartment")
	public void deleteDepartment(long id) {
		departmentService.deleteDepartment(id);
	}
	/**
	 * @描述 修改部门
	 * @param area
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateDepartment")
	public boolean deleteDepartment(Department department) {
		return departmentService.updateDepartment(department);
	}
	/**
	 * @描述 模糊查询
	 * @param area
	 * @return
	 */
	@JsonFilter(pojo = Department.class, allow = { "id", "name", "level",
	"childDepartments" })
	@ResponseBody
	@RequestMapping(value = "/findByName")
	public Object findByName(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "25") int pageSize,
			@RequestParam(value = "name") String name) {
		Page<Department> pageModle = departmentService
				.findByLikeName(name, page - 1, pageSize);
		JqueryUiDatagardPageModel<Department> datagrid = new JqueryUiDatagardPageModel<>(
				pageModle.getTotalElements(), pageModle.getContent());
		datagrid.setRows(pageModle.getContent());
		return datagrid;
	}	
}
