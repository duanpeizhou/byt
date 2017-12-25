package cn.zectec.contraceptive.management.system.sender.util;

import cn.zectec.contraceptive.management.system.model.Aisle;
import cn.zectec.contraceptive.management.system.model.Channel;
import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.model.GiveOutInfo;
import cn.zectec.contraceptive.management.system.model.IDCardInfo;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateChangeRecord;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStatusInfo;

public class ChangeMessageEntity {
//	private static Logger logger = Logger.getLogger(ChangeMessageEntity.class);
	public static GiveOutInfo getGiveOutInfo(GetMedicineRecord o){
		GiveOutInfo giveOutInfo=new GiveOutInfo();
		giveOutInfo.setBillNumber(o.getBillNumber());
		giveOutInfo.setGoodsID(getGoodsID(o.getContraceptive().getName()));
		giveOutInfo.setGrove(o.getCargoRoadNo());
		giveOutInfo.setTFactoryID(o.getMachineryEquipment().getDeviceNo()+"");
		giveOutInfo.setTime(o.getGetMedicineDate());
		return giveOutInfo;
	}
	public static IDCardInfo getIdCardInfo(GetMedicineRecord o){
		IDCardInfo idCardInfo=new IDCardInfo();
		idCardInfo.setAddress(o.getAddress());
		idCardInfo.setBeginDate(o.getBeginDate());
		idCardInfo.setBirthday(o.getBirthDay());
		idCardInfo.setCardNumber(o.getIdNumber());
		idCardInfo.setEndDate(o.getEndDate());
		idCardInfo.setGender(o.getSex().equals("男") == true?1:0);
		idCardInfo.setName(o.getName());
		idCardInfo.setNation(o.getNation().getCode());
		idCardInfo.setStationName(o.getStationName());
		return idCardInfo;
	}
	public static MachineryEquipmentStatusInfo getInfo(MachineryEquipmentStateChangeRecord record){
		MachineryEquipmentStatusInfo info=new MachineryEquipmentStatusInfo();
		info.setConnectionState(record.getMachineryEquipment().getMachineryEquipmentState().isConnectionState()?1:0);
		info.setDoorState(record.getMachineryEquipment().getMachineryEquipmentState().isDoorState()?1:0);
		info.setIDDeviceState(record.getMachineryEquipment().getMachineryEquipmentState().isCardReaderFailure()?1:0);
		info.setTemperature(record.getMachineryEquipment().getMachineryEquipmentState().getDeviceTemperature()+"");
		info.setTime(record.getHanpenDate());
		info.setTFactoryID(record.getMachineryEquipment().getDeviceNo()+"");
		info.setHumidity("0");
		for(Aisle a:record.getMachineryEquipment().getAisles()){
			Channel c=new Channel(a.getIndex_(), getGoodsID(a.getContraceptive().getName()), a.getStockout()?1:0, a.getAisleFailure()?0:1);
			info.getChannel().add(c);
		}
		return info;
	}
	private static String getGoodsID(String contraceptiveName){
		if(contraceptiveName.contains("套")){
			return "327";
		}else if(contraceptiveName.contains("栓")){
			return "309";
		}else if(contraceptiveName.contains("膜")){
			return "313";
		}else if(contraceptiveName.contains("胶")){
			return "312";
		}
		return "327";
	}
}
