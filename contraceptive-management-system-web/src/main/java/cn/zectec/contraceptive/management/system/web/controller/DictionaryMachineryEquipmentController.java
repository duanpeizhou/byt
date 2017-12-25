package cn.zectec.contraceptive.management.system.web.controller;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zectec.contraceptive.management.system.model.Nation;
import cn.zectec.contraceptive.management.system.service.INationService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;

@Controller
public class DictionaryMachineryEquipmentController{
	/**
	 * 
	 * @author Administrator
	 * @描述 字典管理跳转
	 */
	@Resource
	private INationService nationService;
	/**
	 * 
	 * @author Administrator
	 * @描述 乡镇字典跳转
	 */
	@RequestMapping(value="/townsDictionaryMachineryEquipment")
	public String getTowns(){
		return "indexDatagrid/systemManager/townsDictionaryMachineryEquipment";
	}
	/**
	 * 
	 * @author Administrator
	 * @描述 村级字典跳转
	 */
	@RequestMapping(value="/communityDictionaryMachineryEquipment")
	public String getAnther(){
		System.out.println("1111");
		return "indexDatagrid/systemManager/communityDictionaryMachineryEquipment";
	}
	/**
	 * 
	 * @author Administrator
	 * @描述 字典跳转管理
	 */
	@RequestMapping(value="/dictionaryMachineryEquipment")
	public String getStateME(){
		return "indexDatagrid/systemManager/dictionaryMachineryEquipment";
	}
	/**
	 * 
	 * @author Administrator
	 * @描述 民族字典跳转
	 */
	@RequestMapping(value="/nationDictionaryMachineryEquipment")
	public String getStateNation(){
		return "indexDatagrid/systemManager/nationDictionaryMachineryEquipment";
	}
	/**
	 * 
	 * @author Administrator
	 * @描述 民族字典查询
	 */
	@ResponseBody
	@RequestMapping(value="/datagridNation")
	public JqueryUiDatagardPageModel<Nation> getAll(@RequestParam(value="page",defaultValue="1")int page,@RequestParam(value="rows",defaultValue="25")int pageSize){
		Page<Nation> pageModle=nationService.getAll(page-1, pageSize);
		JqueryUiDatagardPageModel<Nation> datagrid=new JqueryUiDatagardPageModel<>(pageModle.getTotalElements(), pageModle.getContent());
		datagrid.setRows(pageModle.getContent());
		return datagrid;
	}
	/**
	 * 
	 * @author Administrator
	 * @描述 市区字典跳转
	 */
	@RequestMapping(value="/areaDictionaryMachineryEquipment")
	public String getArea(){
		return "indexDatagrid/systemManager/areaDictionaryMachineryEquipment";
	}
}
