package cn.zectec.contraceptive.management.system.manager.impl;

import java.util.ArrayList;
import java.util.List;








import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.ILogManager;
import cn.zectec.contraceptive.management.system.model.Log;
import cn.zectec.contraceptive.management.system.model.Log.LogType;
import cn.zectec.contraceptive.management.system.repository.ILogRepository;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.Operator;

@Component
public class LogManagerImpl extends SimpleBaseManagerImpl<Log> implements ILogManager {
	@Autowired
	private ILogRepository logRepository;
	
	@Autowired
	public LogManagerImpl(ILogRepository baseRepository) {
		super(baseRepository);
		this.logRepository = baseRepository;
	}

	@Override
	public void add(List<Log> logs) {
		logRepository.save(logs);
	}

	@Override
	public Page<Log> getSpecifiedLog(int page, int pageSize, LogType logType) {
		Order order=new Order(Direction.DESC,"operationDate");
		Sort sort=new Sort(order);
		if(logType!=null){
			List<SearchFilter> filters=new ArrayList<SearchFilter>();
			SearchFilter filter=new SearchFilter("logType",logType);
			filters.add(filter);
			return this.findBySearchFilters(filters, page, pageSize, sort);
		}else {
			return logRepository.findAll(new PageRequest(page, pageSize, sort));
		}
	}

	@Override
	public boolean deleteLog(int id) {
		try {
			logRepository.delete((long)id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Page<Log> getLogByTitle(int page, int pageSize, String title) {
		List<SearchFilter> seracherFilters=new ArrayList<SearchFilter>();
		SearchFilter searchFilter=new SearchFilter("title",Operator.LIKE,title);
		seracherFilters.add(searchFilter);
		return this.findBySearchFilters(seracherFilters, page, pageSize);
	}


}
