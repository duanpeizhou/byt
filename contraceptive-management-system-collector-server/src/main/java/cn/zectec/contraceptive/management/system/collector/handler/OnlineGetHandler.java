package cn.zectec.contraceptive.management.system.collector.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import cn.zectec.contraceptive.management.system.collector.util.AttrName;
import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.Nation;
import cn.zectec.contraceptive.management.system.sdk.Context;
import cn.zectec.contraceptive.management.system.sdk.MessageReceivedHandler;
import cn.zectec.contraceptive.management.system.sdk.message.OnlineGetRequestMessage;
import cn.zectec.contraceptive.management.system.sdk.message.OnlineGetResponseMessage;
import cn.zectec.contraceptive.management.system.sdk.message.Request;
import cn.zectec.contraceptive.management.system.sdk.message.Response;
import cn.zectec.contraceptive.management.system.sdk.message.StatusReportRequestMessage;
import cn.zectec.contraceptive.management.system.service.IGetMedicineRecordService;
import cn.zectec.contraceptive.management.system.service.INationService;
import cn.zectec.contraceptive.management.system.service.IStrategyService;

public class OnlineGetHandler implements MessageReceivedHandler{
	@Autowired
	private IStrategyService strategyService;
	
	@Autowired
	private IGetMedicineRecordService getMedicineRecordService;
	@Autowired
	private INationService nationService;
	private static Logger logger = Logger.getLogger(SaveOfflineTradeRecordHandler.class);
	private String provinceNo = "11";
	private String cityNo = "110";
	private String oldCityNO = "1111";
	private List<String> idcardNos=new ArrayList<String>();
	private long idel = 35*1000;
	public long getIdel() {
		return idel;
	}


	public void setIdel(long idel) {
		this.idel = idel;
	}
	private Map<String, TimeRecord> deviceNo2GetMedicineRecordMap = Collections.synchronizedMap(new HashMap<String, TimeRecord>());
	
	@Override
	public void handle(Context context, Request request) {
		
		MachineryEquipment me = (MachineryEquipment)context.attr(AttrName.MachineryEquipment);
		String countyID = me.getArea().getParentArea().getParentArea().getId()+"";
		String countyNo = me.getArea().getParentArea().getParentArea().getNo()+"";
		String terminalNo = request.getMessage().getTerminalNo()+"";
		if(request.getMessage() instanceof StatusReportRequestMessage && deviceNo2GetMedicineRecordMap.containsKey(terminalNo)){
			StatusReportRequestMessage srrm = (StatusReportRequestMessage) request.getMessage();
			TimeRecord timeRecord = deviceNo2GetMedicineRecordMap.get(terminalNo);
			if(srrm.getCargoLeft()[timeRecord.cargoRoadNo-1]+1 == timeRecord.amount){
				boolean saveGetMedicineRecord = getMedicineRecordService.saveGetMedicineRecord(timeRecord.getRecord());
				logger.info(terminalNo+"在线领用记录存入："+saveGetMedicineRecord);
				deviceNo2GetMedicineRecordMap.remove(terminalNo);
			}
		}
		
		
		if(me!=null && request.getMessage() instanceof OnlineGetRequestMessage){
			
			OnlineGetRequestMessage message = (OnlineGetRequestMessage)request.getMessage();
			boolean enbale = strategyService.enableGet(message.getIDCardNo(), message.getAge());
			
			if(idcardNos.contains(message.getIDCardNo())){
				enbale = true;
			}
			
			Response response = new Response();
			OnlineGetResponseMessage onlineGetResponseMessage = new OnlineGetResponseMessage();
			onlineGetResponseMessage.setEnable(enbale);
			onlineGetResponseMessage.setBillNumber(message.getBillNumber());
			onlineGetResponseMessage.setTerminalNo((int)me.getDeviceNo());
			response.setMessage(onlineGetResponseMessage);
			
			if(enbale){
				OnlineGetRequestMessage tradeMessage = (OnlineGetRequestMessage)request.getMessage();
				GetMedicineRecord getMedicineRecord = new GetMedicineRecord();
				String idCardNo = tradeMessage.getIDCardNo();
				getMedicineRecord.setIdNumber(idCardNo);
				getMedicineRecord.setAmount(1);
				getMedicineRecord.setName(tradeMessage.getName());
				getMedicineRecord.setSex(tradeMessage.getGender()==1?"男":"女");
				getMedicineRecord.setNation(parseNation(tradeMessage.getNation()));
				getMedicineRecord.setAge(tradeMessage.getAge());
				getMedicineRecord.setAddress(tradeMessage.getAddress());
				getMedicineRecord.setHouseholdRegistration(getHouseholdRegistration(tradeMessage.getAddress()));
				getMedicineRecord.setStationName(tradeMessage.getStationName());
				getMedicineRecord.setBillNumber((long) tradeMessage.getBillNumber());
				getMedicineRecord.setMachineryEquipment(me);
				getMedicineRecord.setGetMedicineDate(tradeMessage.getTradeDate());
				getMedicineRecord.setContraceptive(me.getAisles().get(tradeMessage.getCargoRoadNo()-1).getContraceptive());
				getMedicineRecord.setCurrentConnectionState(true);
				getMedicineRecord.setCargoId(tradeMessage.getCargoId()+"");
				getMedicineRecord.setCargoRoadNo(tradeMessage.getCargoRoadNo()+"");
				getMedicineRecord.setBirthDay(tradeMessage.getBirthDay());
				getMedicineRecord.setBeginDate(tradeMessage.getBeginDate());
				getMedicineRecord.setEndDate(tradeMessage.getEndDate());
				if(idCardNo.startsWith(provinceNo)){
					if(idCardNo.startsWith(cityNo) || idCardNo.startsWith(oldCityNO)){
						if(idCardNo.startsWith(countyID) || idCardNo.startsWith(countyNo)){
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
				if(deviceNo2GetMedicineRecordMap.containsKey(terminalNo)){
					deviceNo2GetMedicineRecordMap.remove(terminalNo);
				}
				TimeRecord timeRecord = new TimeRecord(new Date(), getMedicineRecord, tradeMessage.getCargoRoadNo(), me.getAisles().get(tradeMessage.getCargoRoadNo()-1).getNum(),terminalNo);
				deviceNo2GetMedicineRecordMap.put(terminalNo, timeRecord);
				logger.info("放入一个terminalNo="+terminalNo+"到deviceNo2GetMedicineRecordMap  timeRecord"+timeRecord);
//				boolean success = getMedicineRecordService.saveGetMedicineRecord(getMedicineRecord);
//				logger.info("在线。getMedicineRecord存入"+success);
			}
			context.wirte(response);
		}
		context.doNextHandler();
	}
	
	@Scheduled(fixedRate=2*60*1000)
	public void removeDeviceNo2GetMedicineRecordMap(){
		if(!deviceNo2GetMedicineRecordMap.isEmpty()){
			List<String> removeDeviceNo = new ArrayList<>();
			for(TimeRecord tr : deviceNo2GetMedicineRecordMap.values()){
				if(System.currentTimeMillis() - tr.getIncomeTime().getTime() > idel){
					removeDeviceNo.add(tr.getDeviceNo());
				}
			}
			for (String string : removeDeviceNo) {
				deviceNo2GetMedicineRecordMap.remove(string);
			}
		}
		logger.info("removeDeviceNo2GetMedicineRecordMap 中的数量："+deviceNo2GetMedicineRecordMap.size());
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

	public List<String> getIdcardNos() {
		return idcardNos;
	}

	public void setIdcardNos(List<String> idcardNos) {
		this.idcardNos = idcardNos;
	}
	private class TimeRecord{
		
		private Date incomeTime;
		private GetMedicineRecord record;
		private int cargoRoadNo;
		private int amount;
		private String deviceNo;
		
		public TimeRecord(Date incomeTime, GetMedicineRecord record,int cargoRoadNo, int amount,String deviceNo) {
			super();
			this.incomeTime = incomeTime;
			this.record = record;
			this.cargoRoadNo = cargoRoadNo;
			this.amount = amount;
			this.deviceNo = deviceNo;
		}
		
		public String getDeviceNo() {
			return deviceNo;
		}


		public Date getIncomeTime() {
			return incomeTime;
		}
		public GetMedicineRecord getRecord() {
			return record;
		}

		@Override
		public String toString() {
			return "TimeRecord [incomeTime=" + incomeTime + ", cargoRoadNo="
					+ cargoRoadNo + ", amount=" + amount + ", deviceNo="
					+ deviceNo + "]";
		}
		
		
	}

}
