package cn.zectec.contraceptive.management.system.web.util;

import java.util.ArrayList;
import java.util.List;

import cn.zectec.contraceptive.management.system.model.Area;

public class AreaFilter {
	
	public static List<Area> filter(List<Area> areas,int count){
		if(count == 0){
			return null;
		}
		if(areas == null){
			return null;
		}
		count --;
		List<Area> result = new ArrayList<>();
		for(Area area : areas){
			result.add(area);
			if(count != 0){
				area.setChildAreas(filter(area.getChildAreas(),count));
			}else{
				area.setChildAreas(null);
			}
		}
		return result;
	}
}
