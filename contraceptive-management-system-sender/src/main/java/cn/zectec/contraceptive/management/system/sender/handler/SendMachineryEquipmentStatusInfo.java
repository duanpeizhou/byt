package cn.zectec.contraceptive.management.system.sender.handler;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cn.zectec.contraceptive.management.system.manager.IMachineryEquipmentManager;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentState;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateChangeRecord;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStatusInfo;
import cn.zectec.contraceptive.management.system.repository.listener.DataChangeHandler;
import cn.zectec.contraceptive.management.system.repository.listener.GetMachineryEquipmentStatusInfoListener;
import cn.zectec.contraceptive.management.system.sender.util.ChangeMessageEntity;
import cn.zectec.contraceptive.management.system.sender.util.MessageConverter;
import cn.zectec.contraceptive.management.system.sender.util.SendMsg;

public class SendMachineryEquipmentStatusInfo implements DataChangeHandler<MachineryEquipmentStateChangeRecord>,ApplicationContextAware{
	private ExecutorService threadPool;
	private static Logger logger=Logger.getLogger(SendGetMedicineRecord.class);
	private String url;
	
	private DelayQueue<DelayItem> queue = new DelayQueue<DelayItem>();
	
	private int sendStatusMaxInterval=24;
	
	private Calendar calendar = Calendar.getInstance();
	
	private ApplicationContext applicationContext;
	
	
	public SendMachineryEquipmentStatusInfo(){
		threadPool = Executors.newCachedThreadPool();
		logger.debug("生成了   UpdateGetMedicineRecordListenerImpl ");
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		GetMachineryEquipmentStatusInfoListener.registeHandler(this);
		loadStatusFromDB();
	}
	@Override
	public void doAfterSave(MachineryEquipmentStateChangeRecord ma) {
			
			MachineryEquipmentStatusInfo info=ChangeMessageEntity.getInfo(ma);
			
			String jsonString=MessageConverter.entity2JsonString(info);
			logger.debug("发送数据地址---"+url);
			logger.debug("要发送的设备状态信息为："+jsonString);
			SendMsg<MachineryEquipmentStateChangeRecord> send=new SendMsg<MachineryEquipmentStateChangeRecord>(url, jsonString, ma, new ResponseHandler<MachineryEquipmentStateChangeRecord, String>() {
				@Override
				public void handle(MachineryEquipmentStateChangeRecord k,String responseString) {
					
					if("1".equals(responseString)){
						logger.info("数据上传成功");
					}else if("-1".equals(responseString)){
						logger.error("数据上传失败：发放机未注册");
					}else if("-2".equals(responseString)){
						logger.error("数据上传失败：发放机已停用 ");
					}else if("-3".equals(responseString)){
						logger.error("数据上传失败：发放机已注销 ");
					}else if("-4".equals(responseString)){
						logger.error("数据上传失败：数据非法 ");
					}else if("-5".equals(responseString)){
						logger.error("数据上传失败：数据重复提交 ");
					}else if("-9".equals(responseString)){
						logger.error("数据上传失败：未知的异常");
					}
					
				}
			});
			
			threadPool.execute(send);
			addMsgIntoQueue(ma);
		
	
	}

	@Override
	public void doAfterUpdate(MachineryEquipmentStateChangeRecord t) {
		
	}
	/**将数据库中的设备状态加载到队列中*/
	public void loadStatusFromDB(){
		IMachineryEquipmentManager equipmentManager = applicationContext.getBean(IMachineryEquipmentManager.class);
		List<MachineryEquipment> all = equipmentManager.findAll();
		for (MachineryEquipment me : all) {
			if(me.getMachineryEquipmentState() == null)continue;
			if(!me.getMachineryEquipmentState().isConnectionState())continue;
			MachineryEquipmentStateChangeRecord ma = new MachineryEquipmentStateChangeRecord();
			ma.setHanpenDate(new Date());
			ma.setMachineryEquipment(me);
			ma.setState(MachineryEquipmentState.Online);
			doAfterSave(ma);
//			addMsgIntoQueue(ma);
		}
	}
	
	private void addMsgIntoQueue(MachineryEquipmentStateChangeRecord ma){
		calendar.setTime(ma.getHanpenDate());
		calendar.add(Calendar.MINUTE, sendStatusMaxInterval);
		ma.setHanpenDate(calendar.getTime());
		DelayItem delayItem = new DelayItem(ma, sendStatusMaxInterval*60*1000+System.currentTimeMillis());
		if(queue.contains(delayItem)){
			queue.remove(delayItem);
		}
		if(!ma.getState().equals(MachineryEquipmentState.OffineLine))queue.offer(delayItem);
	}
	
	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}

	public DelayQueue<DelayItem> getQueue() {
		return queue;
	}
	public void setQueue(DelayQueue<DelayItem> queue) {
		this.queue = queue;
	}
	public int getSendStatusMaxInterval() {
		return sendStatusMaxInterval;
	}

	public void setSendStatusMaxInterval(int sendStatusMaxInterval) {
		this.sendStatusMaxInterval = sendStatusMaxInterval;
	}

	
}
