package cn.zectec.contraceptive.management.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import cn.zectec.contraceptive.management.system.manager.INationManager;
import cn.zectec.contraceptive.management.system.model.Nation;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
import cn.zectec.contraceptive.management.system.service.INationService;

@Service
public class NationServiceImpl implements INationService{
	@Autowired
	private INationManager nationManager;
	/**
	 * 通过民族的编码查询民族
	 * @param  民族的编码
	 * @return 符合条件的民族
	 */
	@Override
	public Nation getNationByCode(byte code) {
		SearchFilter searchFilter = new SearchFilter("code", code);
		return nationManager.findBySearchFilters(searchFilter);
	}
	/**
	 * 分页查询民族
	 * @param page 当前的页码
	 * @param pageSize 当前的页数的显示的条数
	 * @return 特定的 民族
	 */
	@Override
	public Page<Nation> getAll(int page, int pageSize) {
		return nationManager.getAll(page, pageSize);
	}

}
