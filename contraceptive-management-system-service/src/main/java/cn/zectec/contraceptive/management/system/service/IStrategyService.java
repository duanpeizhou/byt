package cn.zectec.contraceptive.management.system.service;

import org.springframework.data.domain.Page;

import cn.zectec.contraceptive.management.system.model.Strategy;


public interface IStrategyService {
	/**
	 * 获取当前的策略
	 * @return
	 */
	public Strategy currentStrategy();
	/**
	 * 更改策略的是否使用
	 */
	public void updatecurrentStrategy();
	/**
	 * 执行默认策略
	 */
	public void init();
	/**
	 * 分页查询策略
	 * @param page 当前页数
	 * @param pageSize 当前每页显示的条数
	 * @return 特定页数的页码的策略
	 */
	public Page<Strategy> getStrategy(int page, int pageSize);
	/**
	 * 添加策略
	 * @param strategy 策略
	 */
	public void add(Strategy strategy);
	/**
	 * 更新策略
	 * @param strategy 特定的策略
	 */
	public void update(Strategy strategy);
	/**
	 * 删除策略
	 * @param id 所要删除的策略id
	 * @return 删除是否成功
	 */
	public boolean deleteStrategy(long id);
	/**
	 * 通过策略的id查询策略
	 * @param l 策略的id
	 * @return 符合条件的策略
	 */
	public Strategy searchStrategy(long l);
	/**
	 * 开启策略
	 * @param id 要开启策略的id
	 */
	public void updateUsed(Integer id);
	/**
	 * 判断是否能够领取药具或药品
	 * @param idcardno 领取人的身份证号码
	 * @param age 领取人的年龄
	 * @return 是否能够领取药具或药品
	 */
	public boolean enableGet(String idcardno,int age);
}
