package cn.zectec.contraceptive.management.system.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.zectec.contraceptive.management.system.manager.IMachineryEquipmentManager;
import cn.zectec.contraceptive.management.system.manager.INationManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/application-context.xml" })
public class CopyOfDBInit {
	@Autowired
	private IMachineryEquipmentManager machineryEquipmentManager;
	@Autowired
	private INationManager nationManager;
	
	@Test
	public void add11(){
		
	}

	
}
