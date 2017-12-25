package cn.zectec.contraceptive.management.system.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.IStrategyManager;
import cn.zectec.contraceptive.management.system.model.Strategy;
import cn.zectec.contraceptive.management.system.repository.IStrategyRepository;
@Component
public class StrategyManagerImpl extends SimpleBaseManagerImpl<Strategy> implements IStrategyManager {

	
	private IStrategyRepository strategyRepository;
	@Autowired
	public StrategyManagerImpl(IStrategyRepository baseRepository) {
		super(baseRepository);
		this.strategyRepository=baseRepository;
	}

	@Override
	public Page<Strategy> getStrategy(int page, int pageSize) {
		return strategyRepository.findAll(new PageRequest(page, pageSize));
	}

}
