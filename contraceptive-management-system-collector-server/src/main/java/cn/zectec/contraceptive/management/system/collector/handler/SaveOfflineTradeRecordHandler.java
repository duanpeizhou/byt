package cn.zectec.contraceptive.management.system.collector.handler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.zectec.contraceptive.management.system.collector.util.AttrName;
import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.Nation;
import cn.zectec.contraceptive.management.system.sdk.Context;
import cn.zectec.contraceptive.management.system.sdk.MessageReceivedHandler;
import cn.zectec.contraceptive.management.system.sdk.message.OfflineGetRequestMessage;
import cn.zectec.contraceptive.management.system.sdk.message.OfflineGetResponseMessage;
import cn.zectec.contraceptive.management.system.sdk.message.Request;
import cn.zectec.contraceptive.management.system.sdk.message.Response;
import cn.zectec.contraceptive.management.system.service.IGetMedicineRecordService;
import cn.zectec.contraceptive.management.system.service.INationService;

public class SaveOfflineTradeRecordHandler implements MessageReceivedHandler{
	@Autowired
	private IGetMedicineRecordService getMedicineRecordService;
	@Autowired
	private INationService nationService;
	private static Logger logger = Logger.getLogger(SaveOfflineTradeRecordHandler.class);
	private String provinceNo = "11";
	private String cityNo = "1101";
	private String oldCityNO = "1111";
	
	@Override
	public void handle(Context context, Request request) {
		try {
			MachineryEquipment me = (MachineryEquipment)context.attr(AttrName.MachineryEquipment);
			String countyID = me.getArea().getParentArea().getParentArea().getId()+"";
			String countryNO = me.getArea().getParentArea().getParentArea().getNo()+"";
			if(me!=null && request.getMessage() instanceof OfflineGetRequestMessage){
				logger.debug("if(request.getMessage() instanceof OfflineGetRequestMessage)进入入");
				OfflineGetRequestMessage tradeMessage = (OfflineGetRequestMessage)request.getMessage();
				GetMedicineRecord getMedicineRecord = new GetMedicineRecord();
				String idCardNo = tradeMessage.getIDCardNo();
				getMedicineRecord.setIdNumber(idCardNo);
				getMedicineRecord.setAmount(1);
				getMedicineRecord.setIdNumber(idCardNo);
				getMedicineRecord.setName(tradeMessage.getName());
				getMedicineRecord.setSex(tradeMessage.getGender()==1?"男":"女");
				getMedicineRecord.setNation(parseNation(tradeMessage.getNation()));
				getMedicineRecord.setAge(tradeMessage.getAge());
				getMedicineRecord.setAddress(tradeMessage.getAddress());
				getMedicineRecord.setHouseholdRegistration(getHouseholdRegistration(tradeMessage.getAddress()));
				getMedicineRecord.setStationName(tradeMessage.getStationName());
				getMedicineRecord.setBillNumber(tradeMessage.getBillNumber());
				getMedicineRecord.setMachineryEquipment(me);
				getMedicineRecord.setGetMedicineDate(tradeMessage.getTradeDate());
				getMedicineRecord.setContraceptive(me.getAisles().get(tradeMessage.getCargoRoadNo()-1<=-2?0:tradeMessage.getCargoRoadNo()-1).getContraceptive());
				getMedicineRecord.setCurrentConnectionState(false);
				getMedicineRecord.setCargoId(tradeMessage.getCargoId()+"");
				getMedicineRecord.setCargoRoadNo(tradeMessage.getCargoRoadNo()+"");
				getMedicineRecord.setBirthDay(tradeMessage.getBirthDay());
				getMedicineRecord.setBeginDate(tradeMessage.getBeginDate());
				getMedicineRecord.setEndDate(tradeMessage.getEndDate());
				if(idCardNo.startsWith(provinceNo)){
					if(idCardNo.startsWith(cityNo) || idCardNo.startsWith(oldCityNO)){
						if(idCardNo.startsWith(countyID) || idCardNo.startsWith(countryNO)){
							getMedicineRecord.setTurnoverSituation("本市本县");
						}else{
							getMedicineRecord.setTurnoverSituation("本市外县");
						}
					}else{
						getMedicineRecord.setTurnoverSituation("本省外市");
					}
				}else{
					getMedicineRecord.setTurnoverSituation("外省市");
				}
				boolean success = getMedicineRecordService.saveGetMedicineRecord(getMedicineRecord);
				logger.info("离线：getMedicineRecord存入"+success);
				Response response = new Response();
				OfflineGetResponseMessage message = new OfflineGetResponseMessage();
				message.setBillNumber(tradeMessage.getBillNumber());
				message.setTerminalNo((int)me.getDeviceNo());
				response.setMessage(message);
				context.wirte(response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("SaveOfflineTradeRecordHandler  出错了  ",e);
		}
		context.doNextHandler();
	}
	private String getHouseholdRegistration(String address) {
		if(address.contains("县")){
			return address.substring(0,address.indexOf("县")+1);
		}else if(address.contains("区")){
			return address.substring(0,address.indexOf("区")+1);
		}else if(address.contains("市")){
			return address.substring(0,address.lastIndexOf("市")+1);
		}
		else{
			return address;
		}
	}
	private Nation parseNation(byte nationid) {
		return nationService.getNationByCode(nationid);
	}
	public String getProvinceNo() {
		return provinceNo;
	}
	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}
	public String getCityNo() {
		return cityNo;
	}
	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}
	public String getOldCityNO() {
		return oldCityNO;
	}
	public void setOldCityNO(String oldCityNO) {
		this.oldCityNO = oldCityNO;
	}
	

}
