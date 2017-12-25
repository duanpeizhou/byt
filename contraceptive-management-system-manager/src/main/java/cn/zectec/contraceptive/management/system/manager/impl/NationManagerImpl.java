package cn.zectec.contraceptive.management.system.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.INationManager;
import cn.zectec.contraceptive.management.system.model.Nation;
import cn.zectec.contraceptive.management.system.repository.INationRepository;

@Component
public class NationManagerImpl extends SimpleBaseManagerImpl<Nation> implements INationManager {
	private INationRepository nationRepository;
	@Autowired
	public NationManagerImpl(INationRepository baseRepository) {
		super(baseRepository);
		this.nationRepository=baseRepository;
	}

	@Override
	public Page<Nation> getAll(int page, int pageSize) {
		return nationRepository.findAll(new PageRequest(page, pageSize));
	}

}
