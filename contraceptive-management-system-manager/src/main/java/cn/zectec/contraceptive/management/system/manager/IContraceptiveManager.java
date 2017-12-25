package cn.zectec.contraceptive.management.system.manager;


import java.util.List;

import org.springframework.data.domain.Page;

import cn.zectec.contraceptive.management.system.model.Contraceptive;

public interface IContraceptiveManager extends IBaseManager<Contraceptive, Long>{
	public List<Contraceptive> getAllContraceptive();
	public Page<Contraceptive> getAllContraceptive(int page,int pageSize);
	public boolean addContraceptive(Contraceptive contraceptive);
	public boolean updateContraceptive(Contraceptive contraceptive);
	public boolean deleteContraceptive(int id);
}
