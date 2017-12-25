package cn.zectec.contraceptive.management.system.repository;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class CreateTable {
	public static void main(String[] args) {
		ApplicationContext app = new FileSystemXmlApplicationContext("classpath:repository-context.xml");
	}
}
