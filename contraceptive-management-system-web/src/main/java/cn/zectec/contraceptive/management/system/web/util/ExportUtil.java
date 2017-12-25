package cn.zectec.contraceptive.management.system.web.util;

import java.util.ArrayList;
import java.util.List;

import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.Aisle;
import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.model.OnlineOfflineRecord;
import cn.zectec.contraceptive.management.system.model.OutStockReplenishmentRecord;
import cn.zectec.contraceptive.management.system.utils.export.ExcelExport;
import cn.zectec.contraceptive.management.system.utils.export.Filed;
import cn.zectec.contraceptive.management.system.utils.export.Formatter;

public class ExportUtil {
	public static ExcelExport<OutStockReplenishmentRecord> exportOutStockReplenishmentRecord(){
		ExcelExport<OutStockReplenishmentRecord>  export = new ExcelExport<OutStockReplenishmentRecord>();
		List<Filed<OutStockReplenishmentRecord>> fileds = new ArrayList<Filed<OutStockReplenishmentRecord>>();
		export.setFileds(fileds);;
		fileds.add(new Filed<OutStockReplenishmentRecord>("终端编号", "machineryEquipment.deviceNo"));
		fileds.add(new Filed<OutStockReplenishmentRecord>("终端类型", "machineryEquipment.terminalType"));
		fileds.add(new Filed<OutStockReplenishmentRecord>("省 ", "machineryEquipment.area.parentArea.parentArea.parentArea.parentArea.name"));
		fileds.add(new Filed<OutStockReplenishmentRecord>("市", "machineryEquipment.area.parentArea.parentArea.parentArea.name"));
		fileds.add(new Filed<OutStockReplenishmentRecord>("区（县）", "machineryEquipment.area.parentArea.parentArea.name"));
		fileds.add(new Filed<OutStockReplenishmentRecord>("街道（乡镇）", "machineryEquipment.area.parentArea.name"));
		fileds.add(new Filed<OutStockReplenishmentRecord>("社区", "machineryEquipment.area.name"));
		fileds.add(new Filed<OutStockReplenishmentRecord>("发放点 ", "machineryEquipment.distributionPoints",250));
		fileds.add(new Filed<OutStockReplenishmentRecord>("缺货名称 ", "contraceptive.name"));
		fileds.add(new Filed<OutStockReplenishmentRecord>("缺货时间", "outStockDate",200));
		fileds.add(new Filed<OutStockReplenishmentRecord>("补货时间 ", "replenishmentDate",200));
		return export;
	}
	public static ExcelExport<MachineryEquipment> exportOnlineMachineryEquipment(){
		ExcelExport<MachineryEquipment>  export = new ExcelExport<MachineryEquipment>();
		List<Filed<MachineryEquipment>> fileds = new ArrayList<Filed<MachineryEquipment>>();
		export.setFileds(fileds);;
		fileds.add(new Filed<MachineryEquipment>("终端编号", "deviceNo"));
		fileds.add(new Filed<MachineryEquipment>("省 ", "area.parentArea.parentArea.parentArea.parentArea.name"));
		fileds.add(new Filed<MachineryEquipment>("市", "area.parentArea.parentArea.parentArea.name"));
		fileds.add(new Filed<MachineryEquipment>("区（县）", "area.parentArea.parentArea.name"));
		fileds.add(new Filed<MachineryEquipment>("街道（乡镇）", "area.parentArea.name",150));
		fileds.add(new Filed<MachineryEquipment>("社区", "area.name"));
		fileds.add(new Filed<MachineryEquipment>("发放点 ", "distributionPoints",200));
		fileds.add(new Filed<MachineryEquipment>("上线时间 ", "machineryEquipmentState.onlineDate",200));
		fileds.add(new Filed<MachineryEquipment>("信号强度", "machineryEquipmentState.signalStrength",100));
		fileds.add(new Filed<MachineryEquipment>("设备温度（℃） ", "machineryEquipmentState.deviceTemperature",150));
		fileds.add(new Filed<MachineryEquipment>("货道一剩余量 ", "aisles",new Formatter<MachineryEquipment>() {
			@Override
			public String formatter(MachineryEquipment t, int index,Filed<MachineryEquipment> filed) {
				return t.getAisles().get(0).getNum()+"";
			}
		},160));
		fileds.add(new Filed<MachineryEquipment>("货道二剩余量 ", "aisles",new Formatter<MachineryEquipment>() {

			@Override
			public String formatter(MachineryEquipment t, int index, Filed<MachineryEquipment> filed) {
				return t.getAisles().get(1).getNum()+"";
			}
		},160));
		fileds.add(new Filed<MachineryEquipment>("货道三剩余量 ", "aisles",new Formatter<MachineryEquipment>() {

			@Override
			public String formatter(MachineryEquipment t, int index,
					Filed<MachineryEquipment> filed) {
				return t.getAisles().get(2).getNum()+"";
			}
		},160));
		fileds.add(new Filed<MachineryEquipment>("货道四剩余量 ", "aisles",new Formatter<MachineryEquipment>() {
			@Override
			public String formatter(MachineryEquipment t, int index,
					Filed<MachineryEquipment> filed) {
				if(t.getAislesNum()<=4){
					return "";
				}else{
					return t.getAisles().get(3).getNum()+"";
				}
			}
		},160));
		return export;
	}
	public static ExcelExport<MachineryEquipment> exportOfflineMachineryEquipment(){
		ExcelExport<MachineryEquipment>  export = new ExcelExport<MachineryEquipment>();
		List<Filed<MachineryEquipment>> fileds = new ArrayList<Filed<MachineryEquipment>>();
		export.setFileds(fileds);;
		fileds.add(new Filed<MachineryEquipment>("终端编号", "deviceNo"));
		fileds.add(new Filed<MachineryEquipment>("省 ", "area.parentArea.parentArea.parentArea.parentArea.name"));
		fileds.add(new Filed<MachineryEquipment>("市", "area.parentArea.parentArea.parentArea.name"));
		fileds.add(new Filed<MachineryEquipment>("区（县）", "area.parentArea.parentArea.name"));
		fileds.add(new Filed<MachineryEquipment>("街道（乡镇）", "area.parentArea.name",150));
		fileds.add(new Filed<MachineryEquipment>("社区", "area.name"));
		fileds.add(new Filed<MachineryEquipment>("发放点 ", "distributionPoints",200));
		fileds.add(new Filed<MachineryEquipment>("离线时间 ", "machineryEquipmentState.offlineDate",200));
		fileds.add(new Filed<MachineryEquipment>("设备温度（℃） ", "machineryEquipmentState.deviceTemperature",150));
		fileds.add(new Filed<MachineryEquipment>("货道一剩余量 ", "aisles",new Formatter<MachineryEquipment>() {
			@Override
			public String formatter(MachineryEquipment t, int index,Filed<MachineryEquipment> filed) {
				return t.getAisles().get(0).getNum()+"";
			}
		},160));
		fileds.add(new Filed<MachineryEquipment>("货道二剩余量 ", "aisles",new Formatter<MachineryEquipment>() {

			@Override
			public String formatter(MachineryEquipment t, int index, Filed<MachineryEquipment> filed) {
				return t.getAisles().get(1).getNum()+"";
			}
		},160));
		fileds.add(new Filed<MachineryEquipment>("货道三剩余量 ", "aisles",new Formatter<MachineryEquipment>() {

			@Override
			public String formatter(MachineryEquipment t, int index,
					Filed<MachineryEquipment> filed) {
				return t.getAisles().get(2).getNum()+"";
			}
		},160));
		fileds.add(new Filed<MachineryEquipment>("货道四剩余量 ", "aisles",new Formatter<MachineryEquipment>() {
			@Override
			public String formatter(MachineryEquipment t, int index,
					Filed<MachineryEquipment> filed) {
				if(t.getAislesNum()<=4){
					return "";
				}else{
					return t.getAisles().get(3).getNum()+"";
				}
			}
		},160));
		return export;
	}
	public static ExcelExport<GetMedicineRecord> exportGetMedicineRecord() {
		ExcelExport<GetMedicineRecord>  export = new ExcelExport<GetMedicineRecord>();
		List<Filed<GetMedicineRecord>> fileds = new ArrayList<Filed<GetMedicineRecord>>();
		export.setFileds(fileds);
		fileds.add(new Filed<GetMedicineRecord>("ID", "id",50));
		fileds.add(new Filed<GetMedicineRecord>("领取时间 ", "addDate"));
		/*fileds.add(new Filed<GetMedicineRecord>("添加时间", "addDate"));*/
		fileds.add(new Filed<GetMedicineRecord>("领用状态", "currentConnectionState",new Formatter<GetMedicineRecord>() {
			@Override
			public String formatter(GetMedicineRecord t, int index,Filed<GetMedicineRecord> filed) {
				if(t.isCurrentConnectionState()){
					return "在线";
				}else{
					return "脱机";
				}
			}
		}));
		fileds.add(new Filed<GetMedicineRecord>("身份证号", "idNumber",new Formatter<GetMedicineRecord>() {
			@Override
			public String formatter(GetMedicineRecord t, int index,Filed<GetMedicineRecord> filed) {
				if(t.getIdNumber()!=null &&t.getIdNumber().length()==18){
					return new StringBuilder(t.getIdNumber().subSequence(0, 6)).append("******").append(t.getIdNumber().substring(12)).toString();
				}else{
					return "";
				}
			}
		}));
		fileds.add(new Filed<GetMedicineRecord>("姓名", "name"));
		fileds.add(new Filed<GetMedicineRecord>("性别", "sex",50));
		fileds.add(new Filed<GetMedicineRecord>("年龄", "age",50));
		fileds.add(new Filed<GetMedicineRecord>("领用药具", "contraceptive.name"));
		fileds.add(new Filed<GetMedicineRecord>("数量", "amount",50));
		fileds.add(new Filed<GetMedicineRecord>("终端编号", "machineryEquipment.deviceNo"));
		fileds.add(new Filed<GetMedicineRecord>("领取地", "machineryEquipment.area.parentArea.parentArea.name"));
		fileds.add(new Filed<GetMedicineRecord>("户籍地", "householdRegistration",200));
		fileds.add(new Filed<GetMedicineRecord>("发放点", "machineryEquipment.distributionPoints",250));
		fileds.add(new Filed<GetMedicineRecord>("人员流动情况", "turnoverSituation"));
		return export;
	}

	public static ExcelExport<Aisle> exportstockoutAisle() {
		ExcelExport<Aisle>  export = new ExcelExport<Aisle>();
		List<Filed<Aisle>> fileds = new ArrayList<Filed<Aisle>>();
		export.setFileds(fileds);
		fileds.add(new Filed<Aisle>("终端编号", "machineryEquipment.deviceNo"));
		fileds.add(new Filed<Aisle>("终端类型", "machineryEquipment.terminalType"));
		fileds.add(new Filed<Aisle>("省 ", "machineryEquipment.area.parentArea.parentArea.parentArea.parentArea.name"));
		fileds.add(new Filed<Aisle>("市", "machineryEquipment.area.parentArea.parentArea.parentArea.name"));
		fileds.add(new Filed<Aisle>("区（县）", "machineryEquipment.area.parentArea.parentArea.name"));
		fileds.add(new Filed<Aisle>("街道（乡镇）", "machineryEquipment.area.parentArea.name"));
		fileds.add(new Filed<Aisle>("社区", "machineryEquipment.area.name"));
		fileds.add(new Filed<Aisle>("发放点 ", "machineryEquipment.distributionPoints",250));
		fileds.add(new Filed<Aisle>("缺货名称 ", "contraceptive.name"));
		fileds.add(new Filed<Aisle>("缺货时间", "stockoutDate",200));
		return export;
	}

	public static ExcelExport<OnlineOfflineRecord> exportOnlineOfflineRecord() {
		ExcelExport<OnlineOfflineRecord>  export = new ExcelExport<OnlineOfflineRecord>();
		List<Filed<OnlineOfflineRecord>> fileds = new ArrayList<Filed<OnlineOfflineRecord>>();
		export.setFileds(fileds);;
		fileds.add(new Filed<OnlineOfflineRecord>("终端编号", "machineryEquipment.deviceNo"));
		fileds.add(new Filed<OnlineOfflineRecord>("终端类型", "machineryEquipment.terminalType"));
		fileds.add(new Filed<OnlineOfflineRecord>("省 ", "machineryEquipment.area.parentArea.parentArea.parentArea.parentArea.name"));
		fileds.add(new Filed<OnlineOfflineRecord>("市", "machineryEquipment.area.parentArea.parentArea.parentArea.name"));
		fileds.add(new Filed<OnlineOfflineRecord>("区（县）", "machineryEquipment.area.parentArea.parentArea.name"));
		fileds.add(new Filed<OnlineOfflineRecord>("街道（乡镇）", "machineryEquipment.area.parentArea.name"));
		fileds.add(new Filed<OnlineOfflineRecord>("社区", "machineryEquipment.area.name"));
		fileds.add(new Filed<OnlineOfflineRecord>("发放点 ", "machineryEquipment.distributionPoints",250));
		fileds.add(new Filed<OnlineOfflineRecord>("上线时间", "onlineDate",200));
		fileds.add(new Filed<OnlineOfflineRecord>("下线时间 ", "offlineDate",200));
		return export;
	}
	
}
