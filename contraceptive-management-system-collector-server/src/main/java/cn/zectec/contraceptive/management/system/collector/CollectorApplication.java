package cn.zectec.contraceptive.management.system.collector;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import cn.zectec.contraceptive.management.system.sdk.SDKServer;


public class CollectorApplication {
	public static ApplicationContext context = new FileSystemXmlApplicationContext("classpath:spring/application-context.xml");
	
	public static void main(String[] args) {
		
		SDKServer collectorServer =  context.getBean(SDKServer.class);
		collectorServer.run();
		
	}
}
