package cn.zectec.contraceptive.management.system.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileTools {

	public static boolean createFileOrDirectory(File file){
		try {
			if(file.exists()){
				return true;
			}
			else if(file.isDirectory()){
				return file.mkdirs();
			}
			else if(file.getParentFile().exists()){
				return file.createNewFile();
			}
			else{
				return file.getParentFile().mkdirs() && file.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static byte[] loadFileBytes(File file){
		byte[] data = new byte[0];
		InputStream is = null;
		ByteArrayOutputStream out = null;
		try {
			is = new BufferedInputStream(new FileInputStream(file));
			out = new ByteArrayOutputStream(4096);  
			byte[] b = new byte[4096];  
			int n ;
			while((n = is.read(b)) != -1){
				out.write(b, 0, n);
			}
			data = out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException(e);
		}finally{
			try {
				is.close();
				out.close();
			} catch (Exception e) {
			}
		}
		return data;
		
	}
		
	public static File loadByte2File(byte[] data,String fileName){
		OutputStream os = null;
		File re = null;
		try {
			re = new File(fileName);
			os = new BufferedOutputStream(new FileOutputStream(re));
			os.write(data);
			return re;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			try {
				os.close();
			} catch (Exception e) {
			}
		}
	} 
}
