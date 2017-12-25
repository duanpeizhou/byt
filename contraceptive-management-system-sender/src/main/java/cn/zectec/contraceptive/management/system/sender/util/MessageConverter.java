package cn.zectec.contraceptive.management.system.sender.util;

import java.util.Arrays;

import cn.zectec.contraceptive.management.system.model.GetRecordInformation;
import cn.zectec.contraceptive.management.system.model.GiveOutInfo;
import cn.zectec.contraceptive.management.system.model.IDCardInfo;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStatusInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageConverter {
	public static String entity2JsonString(GiveOutInfo giveOutInfo,IDCardInfo cardInfo){
		Gson gson=new GsonBuilder().registerTypeAdapter(GiveOutInfo.class, new GiveOutInfoJsonSerializer()).registerTypeAdapter(IDCardInfo.class, new IDCardInfoJsonSerializer()).create();
		GetRecordInformation getRecordInformation=new GetRecordInformation(giveOutInfo, cardInfo);
		return gson.toJson(Arrays.asList(getRecordInformation));
	}
	public static String entity2JsonString(MachineryEquipmentStatusInfo statusInfo){
		Gson gson2=new GsonBuilder().registerTypeAdapter(MachineryEquipmentStatusInfo.class, new MachineryEquipmentStatusInfoJsonSeriailzer()).create();
		return gson2.toJson(Arrays.asList(statusInfo));
	}
	
	
}
