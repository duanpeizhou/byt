package cn.zectec.contraceptive.management.system.service;

import java.util.Random;


public class MEServiceImpl {
	private static Random random = new Random();
	public static void main(String[] args) {
		//MenuManagerImpl mm=new MenuManagerImpl(null);
		int s = random.nextInt(200000)+100000;
		int ss = random.nextInt(20000)+1;
		System.out.println(s);
		System.out.println(ss);
//		             110108******183714
//		             372922******089966
		String id = "372922199508089966";
		String sd = id.substring(0, 6)+"******"+id.substring(12);
		System.out.println(sd);
	}
}
