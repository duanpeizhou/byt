package cn.zectec.contraceptive.management.system.manager.impl;


import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.IAisleManager;
import cn.zectec.contraceptive.management.system.model.Aisle;
import cn.zectec.contraceptive.management.system.repository.IAisleRepository;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
@Component
public class AisleManageImpl extends SimpleBaseManagerImpl<Aisle> implements IAisleManager {
	private IAisleRepository aisleRepository;
	
	@Autowired
	public AisleManageImpl(IAisleRepository baseRepository) {
		super(baseRepository);
		this.aisleRepository=baseRepository;
	}
	@Override
	public List<Aisle> getAislesByContraceptiveId(int id) {
		SearchFilter filter=new SearchFilter("contraceptive.id",id);
		return this.findBySearchFilters(Arrays.asList(filter));
	}
	

}
