package cn.zectec.contraceptive.management.system.sender.util;

import java.lang.reflect.Type;

import cn.zectec.contraceptive.management.system.model.GiveOutInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GiveOutInfoJsonSerializer implements JsonSerializer<GiveOutInfo>{
	
	@Override
	public JsonElement serialize(GiveOutInfo src, Type typeOfSrc,
			JsonSerializationContext context) {
		GsonBuilder gb=new GsonBuilder();
		gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
		Gson gson=gb.create();
		return gson.toJsonTree(src);
	}
}
