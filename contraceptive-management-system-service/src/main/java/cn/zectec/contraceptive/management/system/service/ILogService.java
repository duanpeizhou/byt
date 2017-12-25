package cn.zectec.contraceptive.management.system.service;

import org.springframework.data.domain.Page;

import cn.zectec.contraceptive.management.system.model.Log;
import cn.zectec.contraceptive.management.system.model.Log.LogType;

public interface ILogService {
	/**
	 * 描述：添加日志信息
	 * @author Administrator
	 * @param logs
	 * @return
	 */
	public void addLog(Log...logs);
	/**
	 * 描述：得到日志
	 * @author Administrator
	 * @param page  页码
	 * @param pageSize 每页数据数量
	 * @param logType 日志类型
	 * @return
	 */
	public Page<Log> getSpecifiedLog(int page,int pageSize,LogType logType);
	/**
	 * 描述：通过id删除日志
	 * @author Administrator
	 * @param id
	 * @return true 删除成功  false  删除失败
	 */
	public boolean deleteLog(int id);
	/**
	 * 通过title获取日志
	 * @author Administrator
	 * @param page 页码
	 * @param pageSize 每页数据数量
	 * @param title 标题
	 * @return 得到的日志
	 */
	public Page<Log> getLogByTitle(int page, int pageSize, String title);
}
