package cn.zectec.contraceptive.management.system.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class ExcelUtil {

	public static byte[] exportExcel(String[] titles, String[][] values) {
		HSSFWorkbook wb = new HSSFWorkbook();
		return wb.getBytes();
	}
	
	
}
