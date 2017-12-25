package cn.zectec.contraceptive.management.system.manager;

import org.springframework.data.domain.Page;

import cn.zectec.contraceptive.management.system.model.Strategy;

public interface IStrategyManager extends IBaseManager<Strategy, Long>{

	Page<Strategy> getStrategy(int page, int pageSize);

}
