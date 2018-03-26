package cn.zectec.contraceptive.management.system.web;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.zectec.contraceptive.management.system.manager.IManagerManager;
import cn.zectec.contraceptive.management.system.manager.IRoleManager;
import cn.zectec.contraceptive.management.system.repository.IMenuRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/application-context.xml" })
public class Test {
	@Autowired
	private IMenuRepository menuRepository;
	@Autowired
	private IManagerManager managerManager;
	@Autowired
	private IRoleManager roleManager;
	
	@Transactional
//	@org.junit.Test
	public void test(){
		/*Role r = roleManager.findOne(1L);
		roleManager.add(r);
		Manager manager = managerManager.findOne(1L);
		manager.getRole().add(r);
		managerManager.update(manager);
		List<Menu> menus = menuRepository.findMenuByManager(manager);
		System.out.println(manager);
		System.out.println(menus);*/
	}
}
