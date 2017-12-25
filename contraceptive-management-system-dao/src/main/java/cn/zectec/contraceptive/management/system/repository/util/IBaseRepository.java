package cn.zectec.contraceptive.management.system.repository.util;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface IBaseRepository<T> extends CrudRepository<T, Long>,JpaSpecificationExecutor<T>, PagingAndSortingRepository<T, Long>{

}