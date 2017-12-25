package cn.zectec.contraceptive.management.system.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zectec.contraceptive.management.system.model.Strategy;
import cn.zectec.contraceptive.management.system.service.IStrategyService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;
@Controller
public class StrategyController {
	@Resource
	private IStrategyService strategyService;
	
	/**
	 * 跳转的策略设备管理
	 * @return
	 */
	@RequestMapping(value="/strategylist")
	public String getStrategy(){
		return "indexDatagrid/StrategyList";
	}
	
	
	/**
	 * 
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/getStrategyList")
	public Object  getDatagridStrategy(@RequestParam(value="page",defaultValue="1")int page,@RequestParam(value="rows",defaultValue="10")int pageSize){
		Page<Strategy> pageStrategy=strategyService.getStrategy(page-1, pageSize);
		JqueryUiDatagardPageModel<Strategy> datagrid=new JqueryUiDatagardPageModel<Strategy>(pageStrategy.getTotalElements(), pageStrategy.getContent());
		datagrid.setRows(pageStrategy.getContent());
		return datagrid;
	}
	
	
	/**
	 * 添加设备策略
	 * @param machineryEquipment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/strategyAdd")
	public boolean strategyAdd(Strategy strategy){
		try {
			strategyService.add(strategy);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * 更新设备策略
	 * @param machineryEquipment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/strategyUpdate")
	public boolean strategyUpdate(Strategy strategy){
		try {
			strategyService.update(strategy);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 删除设备策略
	 * @param machineryEquipment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteStrategy")
	public Map<String, Object> strategyDelete(@RequestParam(value = "id") long id)
	{
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("success", strategyService.deleteStrategy(id));
		return map;
	}
	
	/**
	 * 更新设备策略Used
	 * @param machineryEquipment
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateUsed")
	public boolean updateUsed(int id){
		try {
			strategyService.updateUsed(id);
			return true;
		} catch (Exception e) {
			return false;
		}	
	}
	

}
