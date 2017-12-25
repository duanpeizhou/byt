package cn.zectec.contraceptive.management.system.manager;

import org.springframework.data.domain.Page;

import cn.zectec.contraceptive.management.system.model.Nation;

public interface INationManager extends IBaseManager<Nation, Long>{
	public Page<Nation> getAll(int page,int pageSize);
}
