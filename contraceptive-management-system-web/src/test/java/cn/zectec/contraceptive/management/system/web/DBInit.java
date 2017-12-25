/*package cn.zectec.contraceptive.management.system.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.zectec.contraceptive.management.system.manager.IAreaManager;
import cn.zectec.contraceptive.management.system.manager.IMachineryEquipmentManager;
import cn.zectec.contraceptive.management.system.model.Aisle;
import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.Area.Level;
import cn.zectec.contraceptive.management.system.model.Contraceptive;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateInfo;
import cn.zectec.contraceptive.management.system.service.IMachineryEquipmentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/application-context.xml" })
public class DBInit {
	@Autowired
	private IMachineryEquipmentManager machineryEquipmentManager;
	@Autowired
	private IAreaManager areaManager;
	@Autowired
	private IMachineryEquipmentService machineryEquipmentService;

	@Test
	public void init2() throws Exception {
		InputStream is = DBInit.class.getClassLoader().getResourceAsStream(
				"data.txt");
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(is));
		String s = bufferedReader.readLine();
		while (s != null) {
			s = s.trim();
			s = s.replaceAll("\\s+", ";");
			String[] ss = s.split(";");
			System.out.println(s);
			for (int i = 0; i < ss.length - 1; i += 2) {
				//System.out.println(ss[i]+"  "+ss[i+1]);
				long num = Long.parseLong(ss[i]);
				MachineryEquipment m = new MachineryEquipment();
				Area area = new Area();
				if (ss[i + 1].contains("Cp")) {
					m.setAlias("昌平机器");
					m.setDistributionPoints("昌平发放点");
					area.setId(1101180109);
				} else if (ss[i + 1].contains("Dc")) {
					m.setAlias("东城机器");
					m.setDistributionPoints("东城发放点");
					area.setId(1101010105);
				} else if (ss[i + 1].contains("Dx")) {
					m.setAlias("大兴机器");
					m.setDistributionPoints("大兴发放点");
					area.setId(1101170206);
				} else if (ss[i + 1].contains("Ft")) {
					m.setAlias("丰台机器");
					m.setDistributionPoints("丰台发放点");
					area.setId(1101060101);
				} else if (ss[i + 1].contains("Hd")) {
					m.setAlias("海淀机器");
					m.setDistributionPoints("海淀发放点");
					area.setId(1101080304);
				}else if(ss[i+1].contains("Xc")){
					m.setAlias("西城机器");
					m.setDistributionPoints("西城发放点");
					area.setId(1101020401);
				}else if(ss[i+1].contains("Cy")){
					m.setAlias("朝阳机器");
					m.setDistributionPoints("朝阳发放点");
					area.setId(1101050201);
				}else if(ss[i+1].contains("Tz")){
					m.setAlias("通州机器");
					m.setDistributionPoints("通州发放点");
					area.setId(1101120103);
				}else if(ss[i+1].contains("Mt")){
					m.setAlias("门头沟机器");
					m.setDistributionPoints("门头沟发放点");
					area.setId(1101090201);
				}else if(ss[i+1].contains("Fs")){
					m.setAlias("房山机器");
					m.setDistributionPoints("房山发放点");
					area.setId(1101100903);
				}else if(ss[i+1].contains("Sj")){
					m.setAlias("石景山机器");
					m.setDistributionPoints("石景山发放点");
					area.setId(1101070203);
				}else if(ss[i+1].contains("Pg")){
					m.setAlias("平谷机器");
					m.setDistributionPoints("平谷发放点");
					area.setId(1101160104);
				}else if(ss[i+1].contains("My")){
					m.setAlias("密云机器");
					m.setDistributionPoints("密云发放点");
					area.setId(1101140301);
				}else if(ss[i+1].contains("Hr")){
					m.setAlias("怀柔机器");
					m.setDistributionPoints("怀柔发放点");
					area.setId(1101150602);
				}else if(ss[i+1].contains("Yq")){
					m.setAlias("延庆机器");
					m.setDistributionPoints("延庆发放点");
					area.setId(1101110102);
				}else if(ss[i+1].contains("Sy")){
					m.setAlias("顺义机器");
					m.setDistributionPoints("顺义发放点");
					area.setId(1101130301);
				}
				m.setArea(area);
				m.setAdminPassword("111111");
				m.setLatitude(39.886992 + "");
				m.setLongitude(116.502185 + "");
				//设置发送编号
				m.setSendNum(ss[i+1]);
				m.setAislesNum(3);
				
				m.setAisles(new ArrayList<Aisle>());
				for (int j = 0; j < 4; j++) {
					Aisle arg0 = new Aisle();
					arg0.setIndex(j);
					Contraceptive c = new Contraceptive();
					c.setId(j + 1);
					arg0.setContraceptive(c);
					arg0.setMachineryEquipment(m);
					m.getAisles().add(arg0);
				}
				m.setDeviceNo(num);
				machineryEquipmentService.add(m);
			}
			s = bufferedReader.readLine();
		}

	}

	// @Test
	public void initmachineryEquipmentManager() {
		long l = 10000l;
		for (int i = 0; i < 10; i++) {
			MachineryEquipment m = new MachineryEquipment();
			Area a = new Area();

			a.setLevel(Level.Community);
			m.setAdminPassword("1234" + i);
			m.setCreationTime(new Date());
			m.setDeviceNo(l + i);
			m.setDistributionPoints("东方花园" + i);
			MachineryEquipmentStateInfo machineryEquipmentState = new MachineryEquipmentStateInfo();
			machineryEquipmentState.setOfflineDate(new Date());
			machineryEquipmentState.setConnectionState(false);
			m.setMachineryEquipmentState(machineryEquipmentState);
		}
	}

	//@Test
	public void ins() {
		Random r = new Random();
		for (int i = 140001; i <= 140011; i++) {
			MachineryEquipment m = new MachineryEquipment();
			Area area = new Area();
			area.setId(1101010105);
			m.setArea(area);
			m.setAdminPassword("111111");
			m.setLatitude(39.886992 + r.nextDouble() / 100000.0 + "");
			m.setLongitude(116.502185 + r.nextDouble() / 100000.0 + "");
			m.setAislesNum(3);
			m.setAlias("东城机器");
			m.setDistributionPoints("东城发放点");
			m.setAisles(new ArrayList<Aisle>());
			for (int j = 0; j < 4; j++) {
				Aisle arg0 = new Aisle();
				arg0.setIndex(j);
				Contraceptive c = new Contraceptive();
				c.setId(j + 1);
				arg0.setContraceptive(c);
				m.getAisles().add(arg0);
			}
			m.setDeviceNo(i);
			machineryEquipmentService.add(m);
		}

		for (int i = 110101; i <= 110285; i++) {
			MachineryEquipment m = new MachineryEquipment();
			Area area = new Area();
			area.setId(1101020401);
			m.setArea(area);
			m.setAdminPassword("111111");
			m.setLatitude(39.886992 + r.nextDouble() / 100000.0 + "");
			m.setLongitude(116.502185 + r.nextDouble() / 100000.0 + "");
			m.setAislesNum(3);
			m.setAlias("西城机器");
			m.setDistributionPoints("西城发放点");
			m.setAisles(new ArrayList<Aisle>());
			for (int j = 0; j < 4; j++) {
				Aisle arg0 = new Aisle();
				arg0.setIndex(j);
				Contraceptive c = new Contraceptive();
				c.setId(j + 1);
				arg0.setContraceptive(c);
				m.getAisles().add(arg0);
			}
			m.setDeviceNo(i);
			machineryEquipmentService.add(m);
		}

		for (int i = 130131; i <= 130230; i++) {
			MachineryEquipment m = new MachineryEquipment();
			Area area = new Area();
			area.setId(1101050201);
			m.setArea(area);
			m.setAdminPassword("111111");
			m.setLatitude(39.886992 + r.nextDouble() / 100000.0 + "");
			m.setLongitude(116.502185 + r.nextDouble() / 100000.0 + "");
			m.setAislesNum(3);
			m.setAlias("朝阳机器");
			m.setDistributionPoints("朝阳发放点");
			m.setAisles(new ArrayList<Aisle>());
			for (int j = 0; j < 4; j++) {
				Aisle arg0 = new Aisle();
				arg0.setIndex(j);
				Contraceptive c = new Contraceptive();
				c.setId(j + 1);
				arg0.setContraceptive(c);
				m.getAisles().add(arg0);
			}
			m.setDeviceNo(i);
			machineryEquipmentService.add(m);
		}

		for (int i = 150101; i <= 150220; i++) {
			MachineryEquipment m = new MachineryEquipment();
			Area area = new Area();
			area.setId(1101080304);
			m.setArea(area);
			m.setAdminPassword("111111");
			m.setLatitude(39.886992 + r.nextDouble() / 100000.0 + "");
			m.setLongitude(116.502185 + r.nextDouble() / 100000.0 + "");
			m.setAislesNum(3);
			m.setAlias("海淀机器");
			m.setDistributionPoints("海淀发放点");
			m.setAisles(new ArrayList<Aisle>());
			for (int j = 0; j < 4; j++) {
				Aisle arg0 = new Aisle();
				arg0.setIndex(j);
				Contraceptive c = new Contraceptive();
				c.setId(j + 1);
				arg0.setContraceptive(c);
				m.getAisles().add(arg0);
			}
			m.setDeviceNo(i);
			machineryEquipmentService.add(m);
		}
	}

	// @Test
	public void initAera() {
		Area a = new Area();
		a.setLevel(Level.Province);
		a.setName("北京市");
		a.setOrder(1);
		Area a_ = new Area();
		a_.setLevel(Level.City);
		a_.setName("北京市");
		a_.setOrder(1);
		Area a1 = new Area();
		a1.setLevel(Level.County);
		a1.setName("海淀区");
		a1.setOrder(1);
		Area a2 = new Area();
		a2.setLevel(Level.County);
		a2.setName("西城区");
		a2.setOrder(2);
		Area a3 = new Area();
		a3.setLevel(Level.County);
		a3.setName("东城区");
		a3.setOrder(3);

		Area a11 = new Area();
		a11.setLevel(Level.TownshipStreet);
		a11.setName("大台街道");
		a11.setParentArea(a1);
		a1.getChildAreas().add(a11);

		Area a111 = new Area();
		a111.setLevel(Level.Community);
		a111.setName("灰地社区");
		a111.setParentArea(a11);
		a11.getChildAreas().add(a111);

		a_.setParentArea(a);
		a1.setParentArea(a_);
		a2.setParentArea(a_);
		a3.setParentArea(a_);
		a.getChildAreas().add(a_);
		a_.getChildAreas().add(a1);
		a_.getChildAreas().add(a2);
		a_.getChildAreas().add(a3);
		areaManager.add(a);
	}
}
*/