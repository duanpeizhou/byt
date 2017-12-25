package cn.zectec.contraceptive.management.system.sender.handler;

import java.util.concurrent.DelayQueue;

import org.apache.log4j.Logger;

import cn.zectec.contraceptive.management.system.sender.service.IPushQueue;

public class PushQueueImpl implements IPushQueue{
	
	private Logger logger = Logger.getLogger(PushQueueImpl.class);
	private SendMachineryEquipmentStatusInfo sendMachineryEquipmentStatusInfo;
	
	@Override
	public void doSendMsg() {
		DelayQueue<DelayItem> delayItems = sendMachineryEquipmentStatusInfo.getQueue();
		logger.info("队列中的大小为："+delayItems.size());
		while(true){
			DelayItem item = delayItems.poll();
			if(item==null){
				break;
			}else{
				sendMachineryEquipmentStatusInfo.doAfterSave(item.getMesc());
				logger.debug("从队列中发送了一个设备状态信息，设备的no："+item.getMesc().getMachineryEquipment().getNo());
			}
		}
	}

	public SendMachineryEquipmentStatusInfo getSendMachineryEquipmentStatusInfo() {
		return sendMachineryEquipmentStatusInfo;
	}

	public void setSendMachineryEquipmentStatusInfo(
			SendMachineryEquipmentStatusInfo sendMachineryEquipmentStatusInfo) {
		this.sendMachineryEquipmentStatusInfo = sendMachineryEquipmentStatusInfo;
	}

}
