package cn.zectec.contraceptive.management.system.web.controller;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zectec.contraceptive.management.system.manager.IGetMedicineRecordManager;
import cn.zectec.contraceptive.management.system.model.Contraceptive;
import cn.zectec.contraceptive.management.system.service.IContraceptiveService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilter;

@Controller
public class ContraceptiveController {
	@Resource
	private IContraceptiveService contraceptiveService;
	
	@Autowired
	private IGetMedicineRecordManager recordService;
	
//	@RequestMapping(value={"/shutdown"},method=RequestMethod.GET)
//	public String down(@RequestParam(value="s",defaultValue="true")Boolean s){
//		initApplication = s;
//		return "redirect:/index";
//	}
//
//	//@Scheduled(cron="0 15 * 26 * ?")
//	public void init(){
//		initApplication =true;
//	}
//
	@JsonFilter(pojo=Contraceptive.class,allow={"id","name"})
	@RequestMapping(value="/getContraceptive")
	public Object getAllContraceptive(){
		return this.contraceptiveService.getAllContraceptive();
	}
	@RequestMapping(value="/getContraceptiveView")
	public String getContraceptiveView(){
		return "indexDatagrid/giveoutManager/contraceptive";
	}
	@ResponseBody
	@RequestMapping(value="/getAllContraceptive")
	public JqueryUiDatagardPageModel<Contraceptive> getAll(@RequestParam(value="page",defaultValue="1")int page,@RequestParam(value="rows",defaultValue="20")int pageSize){
		Page<Contraceptive> pageModel=contraceptiveService.getAllContraceptive(page-1, pageSize);
		JqueryUiDatagardPageModel<Contraceptive> datagrid=new JqueryUiDatagardPageModel<Contraceptive>(pageModel.getTotalElements(), pageModel.getContent());
		datagrid.setRows(pageModel.getContent());
		return datagrid;
	}
	public static boolean initApplication = false;
	/**
	 * 添加药具
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addContraceptive")
	public boolean addContraceptive(Contraceptive contraceptive){
		/*contraceptive.setId(-1l);
		System.out.println(contraceptive);*/
		return contraceptiveService.addContraceptive(contraceptive);
	}
	/**
	 * 修改药具
	 * @param contraceptive
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/updateContraceptive")
	public boolean updateContraceptive(Contraceptive contraceptive){
		return contraceptiveService.updateContraceptive(contraceptive);
	}
	/**
	 * 删除药具
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteContraceptive")
	public boolean deleteContraceptive(int id){
		return contraceptiveService.deleteContraceptive(id);
	}
}
