package cn.zectec.contraceptive.management.system.manager;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.zectec.contraceptive.management.system.model.Log;
import cn.zectec.contraceptive.management.system.model.Log.LogType;

public interface ILogManager extends IBaseManager<Log, Long> {
	public void add(List<Log> log);
	public Page<Log> getSpecifiedLog(int page,int pageSize,LogType logType);
	public boolean deleteLog(int id);
	public Page<Log> getLogByTitle(int page, int pageSize, String title);
}
