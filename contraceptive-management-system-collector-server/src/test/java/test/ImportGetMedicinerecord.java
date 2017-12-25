package test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.zectec.contraceptive.management.system.model.Contraceptive;
import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.Nation;
import cn.zectec.contraceptive.management.system.service.IContraceptiveService;
import cn.zectec.contraceptive.management.system.service.IGetMedicineRecordService;
import cn.zectec.contraceptive.management.system.service.IMachineryEquipmentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context.xml")
public class ImportGetMedicinerecord {
	@Resource
	private IGetMedicineRecordService getMedicineRecordService;
	@Resource
	private IMachineryEquipmentService machineryEquipmentService;

	@Test
	public void test() throws Exception {
		Workbook workbook = new XSSFWorkbook("E:/src.xlsx");
		Iterator<Sheet> sheets = workbook.iterator();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		DataFormatter formatter = new DataFormatter();
		Nation nation = new Nation();
		nation.setCode((byte) 1);
		nation.setId(1);
		nation.setName("汉族");
		while (sheets.hasNext()) {
			Sheet next = sheets.next();
			Iterator<Row> rows = next.iterator();
			rows.next();
			while (rows.hasNext()) {
				GetMedicineRecord getMedicineRecord = new GetMedicineRecord();
				Row row = rows.next();
				Cell getDateCell = row.getCell(1);
				Date getDate = dateFormat.parse(formatter
						.formatCellValue(getDateCell));
				getMedicineRecord.setIdNumber(formatter.formatCellValue(row.getCell(3)));
				getMedicineRecord.setName(formatter.formatCellValue(row.getCell(4)));
				getMedicineRecord.setSex(formatter.formatCellValue(row.getCell(5)));
				getMedicineRecord.setNation(nation);
				getMedicineRecord.setAge(Integer.parseInt(formatter.formatCellValue(row.getCell(6))));
				getMedicineRecord.setContraceptive(getContraceptive(formatter.formatCellValue(row.getCell(7))));
				getMedicineRecord.setAmount(1);
				getMedicineRecord.setAddress(formatter.formatCellValue(row.getCell(11)));
				getMedicineRecord.setHouseholdRegistration(getHouseholdRegistration(formatter.formatCellValue(row.getCell(11))));
				getMedicineRecord.setStationName(getHouseholdRegistration(formatter.formatCellValue(row.getCell(11))));
				getMedicineRecord.setBillNumber(Long.parseLong(GenerateInviteCode.generate()));
				long deviceNo = Long.parseLong(formatter.formatCellValue(row.getCell(9)));
				MachineryEquipment me = machineryEquipmentService.getMachineryEquipmentByDeviceNo(deviceNo);
				getMedicineRecord.setMachineryEquipment(me);
				getMedicineRecord.setGetMedicineDate(getDate);
				getMedicineRecord.setCurrentConnectionState(formatter.formatCellValue(row.getCell(2)).equals("在线"));
				getMedicineRecord.setBirthDay(new Date());
				getMedicineRecord.setBeginDate(new Date());
				getMedicineRecord.setEndDate(new Date());
				getMedicineRecord.setTurnoverSituation(formatter.formatCellValue(row.getCell(13)));
				getMedicineRecordService.saveGetMedicineRecord(getMedicineRecord);
			}
		}
	}

	private String getHouseholdRegistration(String address) {
		if (address.contains("县")) {
			return address.substring(0, address.indexOf("县") + 1);
		} else if (address.contains("区")) {
			return address.substring(0, address.indexOf("区") + 1);
		} else if (address.contains("市")) {
			return address.substring(0, address.lastIndexOf("市") + 1);
		} else {
			return address;
		}
	}
	@Resource
	private IContraceptiveService contraceptiveService;
	List<Contraceptive> allContraceptive=null;
	private Contraceptive getContraceptive(String name) {
//		if(allContraceptive == null){
//			allContraceptive = contraceptiveService.getAllContraceptive();
//		}
		Contraceptive c = new Contraceptive();
		switch (name) {
		case "避孕套（中）":
			c.setId(2L);
			break;
		case "避孕套（大）":
			c.setId(1L);
			break;
		case "避孕套（小）":
			c.setId(4L);
			break;
		case "避孕栓":
			c.setId(3L);
			break;
		}
		return c;
	}
}
