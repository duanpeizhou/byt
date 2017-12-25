package cn.zectec.contraceptive.management.system.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ZIPUtil {
	public static byte[] packageFiles(List<File> files) throws IOException{
		Map<String, byte[]> filename2DatasMap = new HashMap<String, byte[]>();
		for(File f : files){
			filename2DatasMap.put(f.getName(), FileTools.loadFileBytes(f));
		}
		return packageFiles(filename2DatasMap);
	}
	
	public static byte[] packageFiles(Map<String, byte[]> filename2DatasMap) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 CheckedOutputStream cos = new CheckedOutputStream(baos,new CRC32());  
		ZipOutputStream zos = new ZipOutputStream(cos);
		for(String name : filename2DatasMap.keySet()){
			ZipEntry zipEntry = new ZipEntry(name);
			zos.putNextEntry(zipEntry);
			zos.write(filename2DatasMap.get(name));
		}
		zos.finish();
		byte[] datas = baos.toByteArray();
		zos.close();
		return datas;
	}
}
