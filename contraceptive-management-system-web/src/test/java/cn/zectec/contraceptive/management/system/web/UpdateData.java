package cn.zectec.contraceptive.management.system.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.zectec.contraceptive.management.system.manager.IAisleManager;
import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.service.IMachineryEquipmentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/application-context.xml" })
public class UpdateData {
	@Autowired
	private IMachineryEquipmentService machineryEquipmentService;
	@Autowired
	private IAisleManager aisleManager;
	
	//@Test
	public void update() throws IOException{
		InputStream is = UpdateData.class.getClassLoader().getResourceAsStream(
				"data2.txt");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
		String s = bufferedReader.readLine();
		while(s != null){
			s = s.trim().replaceAll("\\s", ";");
			String data[] =  s.split(";");
			long deviceNo = Long.parseLong(data[1]);
			long areaId = Long.parseLong(data[9]);
			String point = data[10];
			String l = data[11];
			String d = data[12];
			int num1 = Integer.parseInt(data[17+1]);
			int num2 = Integer.parseInt(data[19+1]);
			int num3 = Integer.parseInt(data[21+1]);
			int num4 = Integer.parseInt(data[23+1]);
			System.out.println(deviceNo +"  "+areaId+" "+point+"  "+l+"  "+d+"  "+num1+"  "+num2+"  "+num3+"  "+num4);
			MachineryEquipment machineryEquipment = machineryEquipmentService.getMachineryEquipmentByDeviceNo(deviceNo);
			if(machineryEquipment != null){
				machineryEquipment.setDistributionPoints(point);
				machineryEquipment.setLatitude(d);
				machineryEquipment.setLongitude(l);
				machineryEquipment.getAisles().get(0).setNum(num1);
				machineryEquipment.getAisles().get(1).setNum(num2);
				machineryEquipment.getAisles().get(2).setNum(num3);
				machineryEquipment.getAisles().get(3).setNum(num4);
				Area a = new Area();
				a.setId(areaId);
				machineryEquipment.setArea(a);
				aisleManager.update(machineryEquipment.getAisles().get(0));
				aisleManager.update(machineryEquipment.getAisles().get(1));
				aisleManager.update(machineryEquipment.getAisles().get(2));
				aisleManager.update(machineryEquipment.getAisles().get(3));
				machineryEquipmentService.updateMachineryEquipment(machineryEquipment);
			}else{
				System.err.println(deviceNo+"不存在..............");
			}
			s = bufferedReader.readLine();
		}
	}
	
//	@Test
	public void update2() throws IOException{
		InputStream is = UpdateData.class.getClassLoader().getResourceAsStream(
				"data3.txt");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
		String s = bufferedReader.readLine();
		while(s != null){
			s = s.trim().replaceAll("\\s", ";");
			String data[] =  s.split(";");
			long deviceNo = Long.parseLong(data[1]);
			long areaId = Long.parseLong(data[9]);
			MachineryEquipment machineryEquipment = machineryEquipmentService.getMachineryEquipmentByDeviceNo(deviceNo);
			if(machineryEquipment != null){
				Area a = new Area();
				a.setId(areaId);
				machineryEquipment.setArea(a);
				machineryEquipmentService.updateMachineryEquipment(machineryEquipment);
			}else{
				System.err.println(deviceNo+"不存在..............");
			}
			System.out.println(deviceNo +"  "+areaId);
			s = bufferedReader.readLine();
		}
	}
	
}
