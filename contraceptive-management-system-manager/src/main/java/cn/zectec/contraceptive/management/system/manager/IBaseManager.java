package cn.zectec.contraceptive.management.system.manager;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;

public interface IBaseManager<T, ID extends Serializable> {
	/**
	 * 添加
	 * @param t
	 * @return
	 */
	public T add(T t);
	
	/**
	 * 更新
	 * @param t
	 * @return
	 */
	public T update(T t);
	/**
	 * 通过ID找到
	 * @param id
	 * @return
	 */
	public T findOne(ID id);
	/**
	 * 得到所有的
	 * @return
	 */
	public List<T> findAll();
	
	/**
	 * 删除
	 * @param t
	 */
	public void delete(T t);
	/**
	 * 通过ID查找
	 * @param id
	 */
	public void delete(ID id);
	
	public List<T> findBySearchFilters(List<SearchFilter> seracherFilters);
	
	public Page<T> findBySearchFilters(List<SearchFilter> seracherFilters,int page,int pageSize);
	
	public Page<T> findBySearchFilters(List<SearchFilter> seracherFilters,int page,int pageSize,Sort sort);
	public T findBySearchFilters(SearchFilter...filters);
	
}
