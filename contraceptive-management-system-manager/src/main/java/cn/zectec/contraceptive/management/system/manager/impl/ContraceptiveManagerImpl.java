package cn.zectec.contraceptive.management.system.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.IContraceptiveManager;
import cn.zectec.contraceptive.management.system.model.Contraceptive;
import cn.zectec.contraceptive.management.system.repository.IContraceptiveRepository;

@Component
public class ContraceptiveManagerImpl extends
		SimpleBaseManagerImpl<Contraceptive> implements IContraceptiveManager {
	private IContraceptiveRepository contraceptiveRepository;

	@Autowired
	public ContraceptiveManagerImpl(IContraceptiveRepository baseRepository) {
		super(baseRepository);
		this.contraceptiveRepository = baseRepository;
	}

	@Override
	public Page<Contraceptive> getAllContraceptive(int page, int pageSize) {
		return contraceptiveRepository.findAll(new PageRequest(page, pageSize));
	}

	@Override
	public List<Contraceptive> getAllContraceptive() {
		return this.findAll();
	}

	@Override
	public boolean addContraceptive(Contraceptive contraceptive) {
		try {
			contraceptiveRepository.save(contraceptive);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean updateContraceptive(Contraceptive contraceptive) {
		try {
			this.update(contraceptive);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteContraceptive(int id) {
		try {
			this.delete((long) id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
