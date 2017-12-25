package cn.zectec.contraceptive.management.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import cn.zectec.contraceptive.management.system.manager.ILogManager;
import cn.zectec.contraceptive.management.system.model.Log;
import cn.zectec.contraceptive.management.system.model.Log.LogType;
import cn.zectec.contraceptive.management.system.service.ILogService;

@Service
public class LogServiceImpl implements ILogService{
	@Autowired
	private ILogManager logManager;
	private int bufferSize = 1;
	private List<Log> buffers = new ArrayList<Log>();
	/**
	 * 描述：添加日志信息
	 * @author Administrator
	 * @param logs 日志
	 * @return
	 */
	@Override
	public void addLog(Log...logs) {
		buffers.addAll(Arrays.asList(logs));
		if(buffers.size() >= bufferSize){
			flushLogs();
		}
	}
	
	/**
	 * 描述：刷新日志信息
	 * @author Administrator
	 * @param 
	 * @return
	 */
	private synchronized void flushLogs() {
		try{
			logManager.add(buffers);
			buffers.clear();
		}catch(Exception e){}
		
	}

	public ILogManager getLogManager() {
		return logManager;
	}

	public void setLogManager(ILogManager logManager) {
		this.logManager = logManager;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	/**
	 * 描述：得到日志
	 * @author Administrator
	 * @param page  页码
	 * @param pageSize 每页数据数量
	 * @param logType 日志类型
	 * @return 分页查询到的日志
	 */
	@Override
	public Page<Log> getSpecifiedLog(int page, int pageSize, LogType logType) {
		return logManager.getSpecifiedLog(page, pageSize, logType);
	}

	/**
	 * 描述：通过id删除日志
	 * @author Administrator
	 * @param id 日志id
	 * @return true 删除成功  false  删除失败
	 */
	@Override
	public boolean deleteLog(int id) {
		return logManager.deleteLog(id);
	}

	/**
	 * 通过title获取日志
	 * @author Administrator
	 * @param page 页码
	 * @param pageSize 每页数据数量
	 * @param title 标题
	 * @return 得到的日志
	 */
	@Override
	public Page<Log> getLogByTitle(int page, int pageSize, String title) {
		return logManager.getLogByTitle(page,pageSize,title);
	}
	

}
