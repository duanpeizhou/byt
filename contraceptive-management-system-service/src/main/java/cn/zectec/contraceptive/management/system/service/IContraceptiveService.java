package cn.zectec.contraceptive.management.system.service;

import java.util.List;

import org.springframework.data.domain.Page;

import cn.zectec.contraceptive.management.system.model.Contraceptive;

public interface IContraceptiveService {
	/**
	 * 描述：获取所有的避孕药具
	 * @author Administrator
	 * @param
	 * @return 获取所有的避孕药具
	 */
	public List<Contraceptive> getAllContraceptive();
	/**
	 * 描述：分页查询所有的避孕药具
	 * @author Administrator
	 * @param page
	 * @param pageSize
	 * @return 所有的避孕药具
	 */
	public Page<Contraceptive> getAllContraceptive(int page,int pageSize);
	/**
	 * 描述：添加药具
	 * @author Administrator
	 * @param contraceptive
	 * @return true 添加成功   false  添加失败
	 */
	public boolean addContraceptive(Contraceptive contraceptive);
	/**
	 * 描述：更新药具
	 * @author Administrator
	 * @param contraceptive
	 * @return true 更新成功   false 更新失败
	 */
	public boolean updateContraceptive(Contraceptive contraceptive);
	/**
	 * 描述：通过id删除药具
	 * @author Administrator
	 * @param id
	 * @return true 删除成功   false 删除失败
	 */
	public boolean deleteContraceptive(int id);
}
