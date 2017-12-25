package cn.zectec.contraceptive.management.system.sender;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.sender.util.Encoder;
public class Test {

	
	public static void main(String[] args) throws UnsupportedEncodingException {
		List<GetMedicineRecord> records = new ArrayList<GetMedicineRecord>();
		
		GetMedicineRecord record = new GetMedicineRecord();
		
		record.setAddDate(new Date());
		record.setAddress("天津市南开区白堤路云居里4号楼4门603号");
		record.setIdNumber("130101195203271014");
		record.setName("三毛");
		record.setStationName("天津市公安局南开分局");
		record.setSex("男");
		MachineryEquipment temp = new MachineryEquipment();
		temp.setDeviceNo(9);
		temp.setTerminalType("避孕套（大）");
		Area area1 = new Area();
		area1.setName("西城区");
		area1.setLevel(Area.Level.City);
		temp.setArea(area1);
		
		record.setMachineryEquipment(temp);	
		records.add(record);
		
		record = new GetMedicineRecord();
		record.setAddDate(new Date());
		record.setAddress("天津市南开区白堤路云居里4号楼4门604号");
		record.setIdNumber("130101195203271014");
		record.setName("四毛");
		record.setStationName("天津市公安局南开分局");
		record.setSex("女");
		MachineryEquipment temp1 = new MachineryEquipment();
		temp1.setDeviceNo(9);
		temp1.setTerminalType("避孕套（中）");
		Area area2 = new Area();
		area2.setLevel(Area.Level.City);
		area2.setName("朝阳");
		temp1.setArea(area2);
		record.setMachineryEquipment(temp1);
		records.add(record);
		
		Encoder encoder = new Encoder();
		String res = encoder.encode2String(records);

		System.out.println(res);
		
		byte[] bytes = res.getBytes("UTF-8");
		
		for(int i =0;i<bytes.length;i++){
			System.out.printf("%2x ", bytes[i]);
		}
	}

}
