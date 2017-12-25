package cn.zectec.contraceptive.management.system.uploaddata.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import cn.zectec.contraceptive.management.system.model.Aisle;
import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateChangeRecord;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateInfo;

public class EntityConverter {
	private static SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String MachineryEquipmentStatus2Tags(MachineryEquipmentStateChangeRecord record){
//		d1	发放机编号
//		d2	数据发送的时间戳（如2014-11-19 00:53:21）
//		d3	信号强度
//		d4	货道一状态（正常：0，故障: 1）
//		d5	货道一余量
//		d6	货道二状态（正常：0，故障: 1）
//		d7	货道二余量
//		d8	货道三状态（正常：0，故障: 1）
//		d9	货道三余量
//		d10	货道四状态（正常：0，故障: 1）
//		d11	货道四余量
//		d12	药具一缺货（正常：0，缺货：1）
//		d13	药具二缺货（正常：0，缺货：1）
//		d14	药具三缺货（正常：0，缺货：1）
//		d15	药具四缺货（正常：0，缺货：1）
//		d16	身份证读卡器故障（正常：0，故障：1）
//		d17	温度
//		d18	开门时间（在状态发送间隔期间，最后一次开门的时间）
//		d19	关门时间（在状态发送间隔期间，最后一次关门的时间）
//		d20	在线状态：（离线：0，在线：1）
//		<Data>
//		<D1></D1>
//		<D2></D2>
//		.....
//		</Data>
		List<Aisle> aisles = record.getMachineryEquipment().getAisles();
		MachineryEquipmentStateInfo machineryEquipmentState = record.getMachineryEquipment().getMachineryEquipmentState();
		StringBuffer sb = new StringBuffer("<Data><D1>");
		sb.append(record.getMachineryEquipment().getNo());
		sb.append("</D1><D2>");
		sb.append(simple.format(new Date()));
		sb.append("</D2><D3>");
		sb.append(machineryEquipmentState.getSignalStrength());
		sb.append("</D3><D4>");
		sb.append(aisles.get(0).getAisleFailure()?1:0);
		sb.append("</D4><D5>");
		sb.append(aisles.get(0).getNum());
		sb.append("</D5><D6>");
		sb.append(aisles.get(1).getAisleFailure()?1:0);
		sb.append("</D6><D7>");
		sb.append(aisles.get(1).getNum());
		sb.append("</D7><D8>");
		sb.append(aisles.get(2).getAisleFailure()?1:0);
		sb.append("</D8><D9>");
		sb.append(aisles.get(2).getNum());
		sb.append("</D9><D10>");
		if(record.getMachineryEquipment().getAislesNum() == 4){
			sb.append(aisles.get(3).getAisleFailure()?1:0);
			sb.append("</D10><D11>");
			sb.append(aisles.get(3).getNum());
		}else {
			sb.append("0</D10><D11>0");
		}
		sb.append("</D11><D12>");
		sb.append(aisles.get(0).getStockout()?1:0);
		sb.append("</D12><D13>");
		sb.append(aisles.get(1).getStockout()?1:0);
		sb.append("</D13><D14>");
		sb.append(aisles.get(2).getStockout()?1:0);
		sb.append("</D14><D15>");
		if(record.getMachineryEquipment().getAislesNum() == 4){
			sb.append(aisles.get(3).getStockout()?1:0);
			sb.append("</D15><D16>");
		}else {
			sb.append("0</D15><D16>");
		}
		sb.append(machineryEquipmentState.isCardReaderFailure()?1:0);
		sb.append("</D16><D17>");
		sb.append(machineryEquipmentState.getDeviceTemperature());
		sb.append("</D17><D18>");
		sb.append(machineryEquipmentState.getDoorDate() == null?null:simple.format(machineryEquipmentState.getDoorDate()));
		sb.append("</D18><D19>");
		sb.append(machineryEquipmentState.getCloseDoorDate()== null?null:simple.format(machineryEquipmentState.getCloseDoorDate()));
		sb.append("</D19><D20>");
		sb.append(machineryEquipmentState.isConnectionState()?1:0);
		sb.append("</D20></Data>");
		Document dom=null;
		try {
			 dom = DocumentHelper.parseText(sb.toString());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return dom.asXML();
	}
	
	private static String GetMedicineRecord2Tags(GetMedicineRecord record){
//		d1	发放机编号
//		d2	货道号
//		d3	领用药具类型
//		d4	领取时间（离线数据必须有，在线实时数据可为空）
//		d5	领取数量
//		d6	记录状态（离线：0，在线：1）
//		d7	记录发送时间
//		d8	身份证号
//		d9	姓名
//		d10	性别
//		d11	出生日期
//		d12	发证机关
//		d13	家庭住址
//		d14	有效期起
//		d15	有效期止
//		d16	人员流动情况：本市本县、本市外县、本省外市、外省市
		StringBuffer sb = new StringBuffer("<Data><D1>");
		sb.append(record.getMachineryEquipment().getNo());
		sb.append("</D1><D2>");
		sb.append(record.getCargoRoadNo());
		sb.append("</D2><D3>");
		sb.append(contraceptiveNameToNum(record.getContraceptive().getName()));
		sb.append("</D3><D4>");
		sb.append(simple.format(record.getGetMedicineDate()));
		sb.append("</D4><D5>");
		sb.append(record.getAmount());
		sb.append("</D5><D6>");
		sb.append(record.isCurrentConnectionState()?1:0);
		sb.append("</D6><D7>");
		sb.append(simple.format(new Date()));
		sb.append("</D7><D8>");
		sb.append(record.getIdNumber());
		sb.append("</D8><D9>");
		sb.append(record.getName());
		sb.append("</D9><D10>");
		sb.append(record.getSex());
		sb.append("</D10><D11>");
		sb.append(simple.format(record.getBirthDay()));
		sb.append("</D11><D12>");
		sb.append(record.getStationName());
		sb.append("</D12><D13>");
		sb.append(record.getAddress());
		sb.append("</D13><D14>");
		sb.append(simple.format(record.getBeginDate()));
		sb.append("</D14><D15>");
		sb.append(simple.format(record.getEndDate()));
		sb.append("</D15><D16>");
		sb.append(record.getTurnoverSituation());
		sb.append("</D16></Data>");
		Document dom=null;
		try {
			 dom = DocumentHelper.parseText(sb.toString());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return dom.asXML();
	}
	
	private static String parseStringArr(String[] str){
		StringBuffer sb = new StringBuffer();
		sb.append("<Data><D1>");
		sb.append(str[0]);
		sb.append("</D1><D2>");
		sb.append(str[1]);
		sb.append("</D2></Data>");
		Document dom=null;
		try {
			 dom = DocumentHelper.parseText(sb.toString());
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return dom.asXML();
	}
	
	public static String entity2Tags(Object obj) {
		if(obj instanceof GetMedicineRecord){
			return GetMedicineRecord2Tags((GetMedicineRecord) obj);
		}else if(obj instanceof MachineryEquipmentStateChangeRecord){
			return MachineryEquipmentStatus2Tags((MachineryEquipmentStateChangeRecord) obj);
		}else if(obj instanceof String){
			return (String)obj;
		}else if (obj instanceof String[]) {
			return parseStringArr((String[]) obj);
		}else{
			return null;
		}
	}
	
	private static int contraceptiveNameToNum(String name){
		if (name.equals("避孕套（大）")) {
			return 1;
		}else if (name.equals("避孕套（中）")) {
			return 2;
		}else if (name.equals("避孕栓")) {
			return 3;
		}else if (name.equals("避孕套（小）")) {
			return 4;
		}else {
			return 1;
		}
		
	}
	
	public static void main(String[] args) {
//		String string=parseStringArr(new String[]{"we","4"});
//		System.out.println(string);
		
		System.out.println(simple.format(new Date()));
		
		
	}
}
