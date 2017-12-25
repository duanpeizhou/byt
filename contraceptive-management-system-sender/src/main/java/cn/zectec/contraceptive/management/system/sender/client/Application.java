package cn.zectec.contraceptive.management.system.sender.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

	public static void main(String[] args) {
		//定时器，然后，在定时器内连接tcp，并向服务器发送数据
		//netty        tcp链接框架
		//QuargZ    定时器，与spring结合
		//加载spring文件，
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/application-context.xml");
		
	}

}
