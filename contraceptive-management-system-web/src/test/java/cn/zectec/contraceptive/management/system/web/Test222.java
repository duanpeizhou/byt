package cn.zectec.contraceptive.management.system.web;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.zectec.contraceptive.management.system.repository.IContraceptiveStatisticalRepository;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/application-context.xml" })
public class Test222 {
	@Autowired
	public IContraceptiveStatisticalRepository contraceptivIContraceptiveRepository;
	
//	@Test
	public void test(){
		contraceptivIContraceptiveRepository.statistByArea(1101060101,new Date(),new Date());
	}
}
