package cn.zectec.contraceptive.management.system.sender.util;

import java.lang.reflect.Type;

import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStatusInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class MachineryEquipmentStatusInfoJsonSeriailzer implements JsonSerializer<MachineryEquipmentStatusInfo>{

	@Override
	public JsonElement serialize(MachineryEquipmentStatusInfo src,
			Type typeOfSrc, JsonSerializationContext context) {
		GsonBuilder gb=new GsonBuilder();
		gb.setDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		Gson gson=gb.create();
		return gson.toJsonTree(src);
	}

}
