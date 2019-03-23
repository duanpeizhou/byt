package cn.zectec.contraceptive.management.system.uploaddata;

import java.util.Date;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;

import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentState;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateChangeRecord;
import cn.zectec.contraceptive.management.system.repository.listener.DataChangeHandler;
import cn.zectec.contraceptive.management.system.repository.listener.GetMachineryEquipmentStatusInfoListener;
import cn.zectec.contraceptive.management.system.service.IMachineryEquipmentService;
import cn.zectec.contraceptive.management.system.uploaddata.utils.DelayItem;
import cn.zectec.contraceptive.management.system.uploaddata.utils.ResponseHandler;
import cn.zectec.contraceptive.management.system.uploaddata.utils.Transmitter;

public class UploadMachineryEquipmentStatusInfo implements DataChangeHandler<MachineryEquipmentStateChangeRecord>,ApplicationContextAware {
	
	private DelayQueue<DelayItem<MachineryEquipmentStateChangeRecord>> queue = new DelayQueue<>();
	
	private ExecutorService threadPool = Executors.newCachedThreadPool();
	
	private String url;
	
	private ApplicationContext context;
	
	//单位为分钟
	private long uploadMaxInterval = 1;
	private static Logger logger = Logger.getLogger(UploadMachineryEquipmentStatusInfo.class);
	
	@Override
	public void doAfterSave(MachineryEquipmentStateChangeRecord t) {
		logger.debug("发送的地址为："+url);
		Transmitter<MachineryEquipmentStateChangeRecord> transmitter = new Transmitter<>(url, t,new ResponseHandler<MachineryEquipmentStateChangeRecord, String>() {
			@Override
			public void handle(MachineryEquipmentStateChangeRecord record, String t) {
				if ("200".equals(t)) {
					logger.info("200,接收并保存成功，无异常");
				}else if ("400".equals(t)) {
					logger.info("400,数据格式不完整，解析错误");
				}else if ("401".equals(t)) {
					logger.info("401,发放机编号错误，无该发放机");
				}else if ("402".equals(t)) {
					logger.info("402,数据内容异常，有非法数据");
				}else if ("500".equals(t)) {
					logger.info("500,请求不成功，服务器遇到异常情况");
				}else {
					logger.info("上传机器转态返回返回无法解释的数据为："+t);
					System.err.println("上传机器转态返回返回无法解释的数据为："+t);
				}
				
			}
		});
		threadPool.execute(transmitter);
		offerDataIntoQueue(t);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = applicationContext;
		GetMachineryEquipmentStatusInfoListener.registeHandler(this);
		loadMEStatusFormDB();
	}
	
//	@Scheduled(fixedRate=1000*60*1)
	public void uploadDataFromQueue(){
		logger.info("queue的大小为："+queue.size());
		while (true) {
			DelayItem<MachineryEquipmentStateChangeRecord> delayItem = queue.poll();
			if (null == delayItem) {
				break;
			}else{
				doAfterSave(delayItem.getT());
				logger.info("从queue中发送了一条数据，设备id："+delayItem.getId());
			}
		}
	}
	
	public void loadMEStatusFormDB(){
		IMachineryEquipmentService machineryEquipmentService = context.getBean(IMachineryEquipmentService.class);
		List<MachineryEquipment> online = machineryEquipmentService.getAllOnlineMachineryEquipment();
//		List<MachineryEquipment> online = new ArrayList<>();
//		online.add(machineryEquipmentService.findById(1));
		for (MachineryEquipment me : online) {
			MachineryEquipmentStateChangeRecord ma = new MachineryEquipmentStateChangeRecord();
			ma.setHanpenDate(new Date());
			ma.setMachineryEquipment(me);
			ma.setState(MachineryEquipmentState.Online);
			offerDataIntoQueue(ma);
		}
	}
	
	
	public void offerDataIntoQueue(MachineryEquipmentStateChangeRecord mestatus) {
		DelayItem<MachineryEquipmentStateChangeRecord> delayItem = new DelayItem<MachineryEquipmentStateChangeRecord>(mestatus,mestatus.getMachineryEquipment().getId(), uploadMaxInterval);
		if (queue.contains(delayItem)) {
			queue.remove(delayItem);
		}
		if(!mestatus.getState().equals(MachineryEquipmentState.OffineLine))queue.offer(delayItem);
		
	}
	public long getUploadMaxInterval() {
		return uploadMaxInterval;
	}
	public void setUploadMaxInterval(long uploadMaxInterval) {
		this.uploadMaxInterval = uploadMaxInterval;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public void doAfterUpdate(MachineryEquipmentStateChangeRecord t) {
		// TODO Auto-generated method stub
		
	}
}
