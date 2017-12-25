package cn.zectec.contraceptive.management.system.sender.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;

@Component
public class Encoder {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	
	private String encode2String(GetMedicineRecord record){	
		StringBuilder sb = new StringBuilder();
		sb.append(sdf.format(record.getAddDate())).append("@").append(record.getIdNumber()).append("@").append(record.getName()).append("@")
		.append(record.getSex()).append("@").append(record.getAddress()).append("@").append(record.getStationName()).append("@")
		.append(record.getContraceptive().getName()).append("@")
		.append(getDQBZMAndNo(record));//.append("@@@");
		return sb.toString();
	}	
	
	public String encode2String(List<GetMedicineRecord> records) throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		for(int i =0;i <records.size();i++){
			sb.append(encode2String(records.get(i)));	
//			if(i != records.size()-1){
				sb.append("@@@");
//			}	
		}
		
		sb.append(">");
		return sb.toString();	
	}
	
	private String getDQBZMAndNo(GetMedicineRecord record){
		return record.getMachineryEquipment().getSendNum();
//		String result1="";
//		//type为0，则用3位表示
//		//type为1,则用5位表示
//		String DQBZM="";
//		int type = 0;
//		long deviceNo = record.getMachineryEquipment().getDeviceNo();
//		Area area = record.getMachineryEquipment().getArea();
//		Area parentArea = area.getParentArea().getParentArea();
//			switch(parentArea.getName()){
//			case "西城区": DQBZM="Xc";type = 0;break;
//			case "东城区": DQBZM="Dc";type = 0;break;
//			case "海淀区": DQBZM="Hd";type = 0;break;
//			case "昌平区": DQBZM="Cp";type = 0;break;
//			case "丰台区": DQBZM="Ft";type = 0;break;
//			case "大兴区": DQBZM="Dx";type = 0;break;
//			
//			case "门头沟区": DQBZM="Mt";type = 1;break;
//			case "石景山区": DQBZM="Sj";type = 1;break;
//			case "房山区": DQBZM="Fs";type = 1;break;
//			case "延庆区": DQBZM="Yq";type = 1;break;
//			case "通州区": DQBZM="Tz";type = 1;break;
//			case "顺义区": DQBZM="Sy";type = 1;break;
//			case "密云区": DQBZM="My";type = 1;break;
//			case "怀柔区": DQBZM="Hr";type = 1;break;
//			case "平谷区": DQBZM="Pg";type = 1;break;
//			case "朝阳区": DQBZM="Cy";type = 1;break;
//			
//			}
//			String temp = deviceNo+"";
//			String result="";
//			if(type == 0){
////				if(temp.length()<3){
////					for(int i =0;i<3-temp.length();i++){
////						result +="0";
////					}
////					result+=temp;
////				}
//				result = temp.substring(temp.length()-3);
//				
//			}else if(type == 1){
////				if(temp.length()<5){
////					for(int i =0;i<5-temp.length();i++){
////						result +="0";
////					}
////					result+=temp;
////				}
//				result = temp.substring(temp.length()-5);
//			}
//			result1 = DQBZM+result;
//				
//		return result1;
	}

}
