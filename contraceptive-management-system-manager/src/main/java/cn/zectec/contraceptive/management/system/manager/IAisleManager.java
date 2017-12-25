package cn.zectec.contraceptive.management.system.manager;


import java.util.List;


import cn.zectec.contraceptive.management.system.model.Aisle;

public interface IAisleManager extends IBaseManager<Aisle, Long>{
	/**
	 * 根据药具的id查询货道
	 * @param id
	 * @return
	 */
	public List<Aisle> getAislesByContraceptiveId(int id);
}
