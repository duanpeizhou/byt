package cn.zectec.contraceptive.management.system.service.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.zectec.contraceptive.management.system.manager.IGetMedicineRecordManager;
import cn.zectec.contraceptive.management.system.manager.IStrategyManager;
import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.model.Strategy;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.Operator;
import cn.zectec.contraceptive.management.system.service.IStrategyService;

@Service
public class StrategyServiceImpl implements IStrategyService{
	@Autowired
	private IStrategyManager strategyManager;
	@Autowired
	private IGetMedicineRecordManager getMedicineRecordManager;
	private static Logger logger = Logger.getLogger(StrategyServiceImpl.class);
	private Strategy currentStrategy;
	/**
	 * 获取当前的策略
	 * @return 当前的策略
	 */
	@Override
	public Strategy currentStrategy() {
		return currentStrategy;
	}
	/**
	 * 更改策略的是否使用
	 */
	@Override
	@Scheduled(fixedRate=1*60*60*1000)
	public void updatecurrentStrategy() {
		SearchFilter sf = new SearchFilter("used", true);
		List<Strategy> list = strategyManager.findBySearchFilters(Arrays.asList(sf));
		if(list.isEmpty()){
			sf = new SearchFilter("defaultStrategy", true);
			list = strategyManager.findBySearchFilters(Arrays.asList(sf));
			if(!list.isEmpty()){
				currentStrategy =  list.get(0);
			}
		}else{
			currentStrategy =  list.get(0);
		}
		logger.debug("当前的策略为"+currentStrategy);
	}
	/**
	 * 执行默认策略
	 */
	@Override
	@PostConstruct
	public void init() {
		updatecurrentStrategy();
	}
	/**
	 * 分页查询策略
	 * @param page 当前页数
	 * @param pageSize 当前每页显示的条数
	 * @return 特定页数的页码的策略
	 */
	@Override
	public Page<Strategy> getStrategy(int page, int pageSize) {
		return strategyManager.getStrategy(page,pageSize);
	}
	/**
	 * 添加策略
	 * @param strategy 策略
	 */
	@Override
	public void add(Strategy strategy) {
		
		if (strategy.isDefaultStrategy()) {
			List<Strategy> strategies=strategyManager.findAll();
			for (Strategy strategy1:strategies) {
				strategy1.setDefaultStrategy(false);
				strategyManager.update(strategy1);
			}
		}
		strategyManager.add(strategy);
		
}
	/**
	 * 更新策略
	 * @param strategy 特定的策略
	 */
	@Override
	public void update(Strategy strategy) {
		
		if (strategy.isDefaultStrategy()) {
			List<Strategy> strategies=strategyManager.findAll();
			for (Strategy strategy1:strategies) {
				strategy1.setDefaultStrategy(false);
				strategyManager.update(strategy1);
			}
		}
		strategyManager.update(strategy);
	}
	/**
	 * 删除策略
	 * @param id 所要删除的策略id
	 * @return 删除是否成功
	 */
	@Override
	public boolean deleteStrategy(long id) {
		try {
			strategyManager.delete(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	/**
	 * 通过策略的id查询策略
	 * @param l 策略的id
	 * @return 符合条件的策略
	 */
	@Override
	public Strategy searchStrategy(long id) {
		return strategyManager.findOne(id);
	}
	/**
	 * 开启策略
	 * @param id 要开启策略的id
	 */
	@Override
	public void updateUsed(Integer id) {
			List<Strategy> strategies=strategyManager.findAll();
			for (Strategy strategy:strategies) {
				if (id == strategy.getId()) {
					strategy.setDefaultStrategy(true);
					strategyManager.update(strategy);
				}else {
					strategy.setDefaultStrategy(false);
					strategyManager.update(strategy);
				}
			}
		
	}
	/**
	 * 判断是否能够领取药具或药品
	 * @param idcardno 领取人的身份证号码
	 * @param age 领取人的年龄
	 * @return 是否能够领取药具或药品
	 */
	@Override
	public boolean enableGet(String idcardno, int age) {
		if(age < currentStrategy.getMinAge() || age>currentStrategy.getMaxAge()){
			return false;
		}
		
		SearchFilter searchFilter = new SearchFilter("idNumber",idcardno);
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, 0-currentStrategy.getGiveOutDay());
//		SearchFilter searchFilter_ = new SearchFilter("getMedicineDate",Operator.GT,calendar.getTime());
		//广东的机器有由于上传的时间为0不能限制多次领取
		SearchFilter searchFilter_ = new SearchFilter("addDate",Operator.GT,calendar.getTime());
		List<GetMedicineRecord> records = getMedicineRecordManager.findBySearchFilters(Arrays.asList(searchFilter,searchFilter_));
		logger.debug(idcardno+" "+currentStrategy.getGiveOutDay()+"天 的领取数据情况"+records.size());
		
		if(records.isEmpty()){
			return true;
		}else{
			return false;
		}
	}

}
