package cn.zectec.contraceptive.management.system.collector.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import cn.zectec.contraceptive.management.system.collector.util.AttrName;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateInfo;
import cn.zectec.contraceptive.management.system.sdk.Context;
import cn.zectec.contraceptive.management.system.sdk.MessageReceivedHandler;
import cn.zectec.contraceptive.management.system.sdk.message.Request;
import cn.zectec.contraceptive.management.system.sdk.message.StatusReportRequestMessage;
import cn.zectec.contraceptive.management.system.service.IMachineryEquipmentService;


public class UpdateLastUpdateTimeHandler implements MessageReceivedHandler{
	@Autowired
	private IMachineryEquipmentService machineryEquipmentService;
	private long ideTime = 60*60*1000;
	private Map<Long, Pair> maps = new ConcurrentHashMap<Long,Pair>();
	private static Logger logger = Logger.getLogger(UpdateLastUpdateTimeHandler.class);
	
	
	@Override
	public void handle(Context context, Request message) {
		
		try {
			MachineryEquipment me = (MachineryEquipment)context.attr(AttrName.MachineryEquipment);
			if(me != null){
				me = machineryEquipmentService.getMachineryEquipmentById(me.getId());
				MachineryEquipmentStateInfo machineryEquipmentState = me.getMachineryEquipmentState();
				
				if(machineryEquipmentState == null){
					machineryEquipmentState = new MachineryEquipmentStateInfo();
				}
				machineryEquipmentState.setLastUpdateTime(new Date());
				me.setMachineryEquipmentState(machineryEquipmentState);
				
				me.setIp(context.session().remoteIP());
				machineryEquipmentState.setHardNo(message.getMessage().getHardNo());
				if(message.getMessage() instanceof StatusReportRequestMessage){
					machineryEquipmentState.setVer(((StatusReportRequestMessage)message.getMessage()).getVersion());
					machineryEquipmentState.setFaultNo(((StatusReportRequestMessage)message.getMessage()).getFaultNo());
				}
				machineryEquipmentService.updateMachineryEquipment(me);
				if(!maps.containsKey(me.getId())){
					Pair p = new Pair();
					p.setLastTime(new Date());
					p.setMachineryEquipment(me);
					maps.put(me.getId(), p);
				}else{
					maps.get(me.getId()).setLastTime(new Date());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		context.doNextHandler();
	}
	
	//@Scheduled(fixedRate=29*1000)
	public void checkLastUpdateTime(){
		
		List<Long>  remove = new ArrayList<Long>();
		for(Pair pair : maps.values()){
			if(System.currentTimeMillis()-pair.getLastTime().getTime() > ideTime){
				machineryEquipmentService.updateEquipmentConnectionState(pair.getMachineryEquipment().getId(), false, new Date());
				remove.add(pair.getMachineryEquipment().getId());
				logger.info("离线的设备为："+pair.getMachineryEquipment().getDeviceNo());
			}
		}
		for(Long id : remove){
			maps.remove(id);
		}
	}
	
	@Scheduled(fixedRate=10*60*1000)
	public void checkDBLastUpdateTime(){
		logger.debug("数据库检测在线离线.............");
		for(MachineryEquipment machineryEquipment : machineryEquipmentService.getAllOnlineMachineryEquipment()){
			Date lastUpdateTime = machineryEquipment.getMachineryEquipmentState().getLastUpdateTime();
			if(machineryEquipment.getMachineryEquipmentState() != null && lastUpdateTime!=null){
				if(System.currentTimeMillis() - lastUpdateTime.getTime() > ideTime){
//					machineryEquipmentService.updateEquipmentConnectionState(machineryEquipment.getId(), false, new Date());
					int minutes = lastUpdateTime.getMinutes();
					lastUpdateTime.setMinutes(minutes+4);
					machineryEquipmentService.updateEquipmentConnectionState(machineryEquipment.getId(), false, lastUpdateTime);
					logger.info("离线的设备为："+machineryEquipment.getDeviceNo()+"，最后改变的时间为："+lastUpdateTime);
					maps.remove(machineryEquipment.getId());
				}
			}
		}
	}

	public long getIdeTime() {
		return ideTime;
	}

	public void setIdeTime(long ideTime) {
		this.ideTime = ideTime;
	}

	
	private class Pair{
		private MachineryEquipment machineryEquipment;
		private Date lastTime;
		public MachineryEquipment getMachineryEquipment() {
			return machineryEquipment;
		}
		public void setMachineryEquipment(MachineryEquipment machineryEquipment) {
			this.machineryEquipment = machineryEquipment;
		}
		public Date getLastTime() {
			return lastTime;
		}
		public void setLastTime(Date lastTime) {
			this.lastTime = lastTime;
		}
		
		
	}
}
