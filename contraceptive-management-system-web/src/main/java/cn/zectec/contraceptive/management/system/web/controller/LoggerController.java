package cn.zectec.contraceptive.management.system.web.controller;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.zectec.contraceptive.management.system.model.Log;
import cn.zectec.contraceptive.management.system.model.Log.LogType;
import cn.zectec.contraceptive.management.system.service.ILogService;
import cn.zectec.contraceptive.management.system.utils.JqueryUiDatagardPageModel;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilter;

@Controller
public class LoggerController {
	@Resource
	private ILogService logService;
	@RequestMapping(value="/log")
	public String get(){
		return "indexDatagrid/log";
	}

	@RequestMapping(value="/logdatagrid")
	@JsonFilter(pojo=Log.class,ignore="managerID")
	public Object getLog(@RequestParam(value="page",defaultValue="1")int page,
						@RequestParam(value="rows",defaultValue="25")int pageSize,
						LogType logType){
		Page<Log> pageModel=logService.getSpecifiedLog(page-1, pageSize, logType);
		JqueryUiDatagardPageModel<Log> datagrid=new JqueryUiDatagardPageModel<Log>(pageModel.getTotalElements(), pageModel.getContent());
		datagrid.setRows(pageModel.getContent());
		return datagrid;
	}
	@ResponseBody
	@RequestMapping(value="/deleteLog")
	public boolean deleteLog(@RequestParam(value="id")int id){
		return logService.deleteLog(id);
	}
	@RequestMapping(value="/searchlogbytitle")
	@JsonFilter(pojo=Log.class,ignore="managerID")
	public Object getLogByTitle(@RequestParam(value="page",defaultValue="1")int page,@RequestParam(value="rows",defaultValue="25")int pageSize,@RequestParam(value="title")String title)
	{
		Page<Log> pageModelSearch=logService.getLogByTitle(page-1,pageSize,title);
		JqueryUiDatagardPageModel<Log> datagrid=new JqueryUiDatagardPageModel<Log>(pageModelSearch.getTotalElements(), pageModelSearch.getContent());
		return datagrid;
	}
}
