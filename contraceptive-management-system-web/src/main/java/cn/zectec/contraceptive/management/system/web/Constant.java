package cn.zectec.contraceptive.management.system.web;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Constant {
	public static int MAX_ITEM = 2000;
	public static ExecutorService THREAD_POLL = Executors.newFixedThreadPool(20);

}
