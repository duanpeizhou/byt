package cn.zectec.contraceptive.management.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import cn.zectec.contraceptive.management.system.manager.IAisleManager;
import cn.zectec.contraceptive.management.system.manager.IContraceptiveManager;
import cn.zectec.contraceptive.management.system.model.Contraceptive;
import cn.zectec.contraceptive.management.system.service.IContraceptiveService;
@Service
public class ContraceptiveServiceImpl implements IContraceptiveService {
	@Resource
	private IContraceptiveManager contraceptiveManager;
	@Resource
	private IAisleManager aisleManager;
	@Override
	/**
	 * 描述：获取所有的避孕药具
	 * @author Administrator
	 * @param
	 * @return 获取所有的避孕药具
	 */
	public List<Contraceptive> getAllContraceptive() {
		return contraceptiveManager.findAll();
	}
	/**
	 * 描述：分页查询所有的避孕药具
	 * @author Administrator
	 * @param page 页数
	 * @param pageSize 每一页的数据条数
	 * @return 所有的避孕药具
	 */
	@Override
	public Page<Contraceptive> getAllContraceptive(int page, int pageSize) {
		return contraceptiveManager.getAllContraceptive(page, pageSize);
	}
	/**
	 * 描述：添加药具
	 * @author Administrator
	 * @param contraceptive 药具实体类
	 * @return true 添加成功   false  添加失败
	 */
	@Override
	public boolean addContraceptive(Contraceptive contraceptive) {
		return contraceptiveManager.addContraceptive(contraceptive);
	}
	/**
	 * 描述：更新药具
	 * @author Administrator
	 * @param contraceptive 药具实体类
	 * @return true 更新成功   false 更新失败
	 */
	@Override
	public boolean updateContraceptive(Contraceptive contraceptive) {
		return contraceptiveManager.updateContraceptive(contraceptive);
	}
	/**
	 * 描述：通过id删除药具
	 * @author Administrator
	 * @param id 药具id
	 * @return true 删除成功   false 删除失败
	 */
	@Override
	public boolean deleteContraceptive(int id) {
		if(aisleManager.getAislesByContraceptiveId(id).isEmpty()){
			return contraceptiveManager.deleteContraceptive(id);
		}else {
			return false;
		}
	}

}
