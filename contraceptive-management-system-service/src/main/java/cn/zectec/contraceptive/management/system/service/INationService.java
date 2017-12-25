package cn.zectec.contraceptive.management.system.service;

import org.springframework.data.domain.Page;

import cn.zectec.contraceptive.management.system.model.Nation;

public interface INationService {
	/**
	 * 通过民族的编码查询民族
	 * @param  民族的编码
	 * @return 符合条件的民族
	 */
	public Nation getNationByCode(byte code);
	/**
	 * 分页查询民族
	 * @param page 当前的页码
	 * @param pageSize 当前的页数的显示的条数
	 * @return 特定的 民族
	 */
	public Page<Nation> getAll(int page,int pageSize);
}
