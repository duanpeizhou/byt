package cn.zectec.contraceptive.management.system.manager.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import cn.zectec.contraceptive.management.system.manager.IBaseManager;
import cn.zectec.contraceptive.management.system.repository.util.DynamicSpecifications;
import cn.zectec.contraceptive.management.system.repository.util.IBaseRepository;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;

public class SimpleBaseManagerImpl<T> implements IBaseManager<T, Long>{
	private IBaseRepository<T> baseRepository;

	public SimpleBaseManagerImpl(IBaseRepository<T> baseRepository) {
		this.baseRepository = baseRepository;
	}

	@Override
	public T add(T t) {
		return baseRepository.save(t);
	}

	@Override
	public T update(T t) {
		return baseRepository.save(t);
	}

	@Override
	public T findOne(Long id) {
		return baseRepository.findOne(id);
	}

	@Override
	public List<T> findAll() {
		return (List<T>)baseRepository.findAll();
	}

	@Override
	public void delete(T t) {
		baseRepository.delete(t);
	}

	@Override
	public void delete(Long id) {
		baseRepository.delete(id);
	}

	@Override
	public List<T> findBySearchFilters(List<SearchFilter> seracherFilters) {
		if(seracherFilters == null || seracherFilters.isEmpty()){
			return (List<T>)baseRepository.findAll();
		}
		Specification<T> spec = DynamicSpecifications.bySearchFilters(seracherFilters);
		return baseRepository.findAll(spec);
	}

	@Override
	public Page<T> findBySearchFilters(List<SearchFilter> seracherFilters,int page, int pageSize) {
		if(seracherFilters == null || seracherFilters.isEmpty()){
			return baseRepository.findAll(new PageRequest(page, pageSize));
		}
		Specification<T> spec = DynamicSpecifications.bySearchFilters(seracherFilters);
		return baseRepository.findAll(spec, new PageRequest(page, pageSize));
	}
	
	@Override
	public Page<T> findBySearchFilters(List<SearchFilter> seracherFilters,int page, int pageSize, Sort sort) {
		if(seracherFilters == null || seracherFilters.isEmpty()){
			return baseRepository.findAll(new PageRequest(page, pageSize,sort));
		}
		Specification<T> spec = DynamicSpecifications.bySearchFilters(seracherFilters);
		return baseRepository.findAll(spec, new PageRequest(page, pageSize,sort));
	}

	@Override
	public T findBySearchFilters(SearchFilter... filters) {
		Specification<T> spec = DynamicSpecifications.bySearchFilters(Arrays.asList(filters));
		return baseRepository.findOne(spec);
	}

}
