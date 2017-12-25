package cn.zectec.contraceptive.management.system.sender.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.IGetMedicineRecordManager;
import cn.zectec.contraceptive.management.system.manager.IMachineryEquipmentStateChangeRecordManager;
import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.sender.handler.SendGetMedicineRecord;
import cn.zectec.contraceptive.management.system.sender.service.IPushQueue;
@Component
public class SendMsgOtherThread{
	@Autowired
	private IGetMedicineRecordManager getMedicineRecordManager;
	@Autowired
	private IMachineryEquipmentStateChangeRecordManager changeRecordManager;
	
	private List<GetMedicineRecord> getMedicineRecords = new ArrayList<GetMedicineRecord>();
	private SendGetMedicineRecord sendGetMedicineRecord;
	
	private static Logger logger=Logger.getLogger(SendMsgOtherThread.class);
	private IPushQueue pushQueue;
	
	public IPushQueue getPushQueue() {
		return pushQueue;
	}

	public void setPushQueue(IPushQueue pushQueue) {
		this.pushQueue = pushQueue;
	}

	public SendMsgOtherThread(){
		logger.info("生成了：SendMsgOtherThread");
	}
	
	@Scheduled(fixedRate=1000*60*1)
	public void send() {
		logger.info("执行了：SendMsgOtherThread.send");
		getMedicineRecords=getMedicineRecordManager.findNotSendRecords();
		logger.debug("没有上传的数据条数为："+getMedicineRecords.size());
		if(!getMedicineRecords.isEmpty()){
			for(GetMedicineRecord g:getMedicineRecords){
				sendGetMedicineRecord.doAfterSave(g);
			}
		}
		
	}
	
	@Scheduled(fixedRate=1000*60*3)
	public void sendMachineyEquipmentStatus(){
		logger.info("执行了：sendMachineyEquipmentStatus pushQueue" +pushQueue);
		if(pushQueue != null)
		pushQueue.doSendMsg();
	}
	
	
	public SendGetMedicineRecord getSendGetMedicineRecord() {
		return sendGetMedicineRecord;
	}
	public void setSendGetMedicineRecord(SendGetMedicineRecord sendGetMedicineRecord) {
		this.sendGetMedicineRecord = sendGetMedicineRecord;
	}

	
	
	
	
	
}
