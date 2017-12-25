package cn.zectec.contraceptive.management.system.uploaddata;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Unit test for simple App.
 */
public class AppTest {
	public static void main(String[] args) {
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.set(2015, 6, 1, 17, 01, 00);
		System.out.println(simple.format(calendar.getTime()));
		Date date = new Date();
		System.out.println(date.before(calendar.getTime()));
	}
  
}
