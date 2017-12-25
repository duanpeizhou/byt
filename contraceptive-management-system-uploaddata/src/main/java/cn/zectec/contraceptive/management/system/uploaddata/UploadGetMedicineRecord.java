package cn.zectec.contraceptive.management.system.uploaddata;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Scheduled;

import cn.zectec.contraceptive.management.system.manager.IGetMedicineRecordManager;
import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.repository.listener.DataChangeHandler;
import cn.zectec.contraceptive.management.system.repository.listener.GetMedicineRecordListener;
import cn.zectec.contraceptive.management.system.uploaddata.utils.ResponseHandler;
import cn.zectec.contraceptive.management.system.uploaddata.utils.Transmitter;

public class UploadGetMedicineRecord implements DataChangeHandler<GetMedicineRecord>,ApplicationContextAware{
	
	private static Logger logger = Logger.getLogger(UploadGetMedicineRecord.class);
	
	private ExecutorService threadPool = Executors.newCachedThreadPool();
	
	private String url;
	
	private String confirmurl;
	
	private IGetMedicineRecordManager getMedicineRecordManager;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		getMedicineRecordManager = applicationContext.getBean(IGetMedicineRecordManager.class);
		GetMedicineRecordListener.registeHandler(this);
	}

	@Override
	public void doAfterSave(GetMedicineRecord t) {
		
		
		Transmitter<GetMedicineRecord> transmitter = new Transmitter<GetMedicineRecord>(url, t, new ResponseHandler<GetMedicineRecord, String>() {

			@Override
			public void handle(GetMedicineRecord k, String t) {
				if(null != t){
					String[] str = t.split(",");
					if (str.length == 2) {
						k.setSent(true);
						getMedicineRecordManager.updateSendStatus(true, k.getId());
						Transmitter<String[]> trans = new Transmitter<String[]>(confirmurl, str, null);
						threadPool.execute(trans);
					}else {
						System.err.println("上传药具领用记录返回的无法解释的数据是："+t);
						logger.error("上传药具领用记录返回的无法解释的数据是："+t);
					}
				}
			}
		});
		threadPool.execute(transmitter);
	}
	
	@Scheduled(fixedRate=1000*60*1)
	private void loadGetMedicineRecordFromDB() {
		List<GetMedicineRecord> sentTrue = getMedicineRecordManager.findNotSendRecords();
		logger.info("执行了loadGetMedicineRecordFromDB。要发送的记录数为："+sentTrue.size());
		for (GetMedicineRecord r : sentTrue) {
			doAfterSave(r);
		}
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getConfirmurl() {
		return confirmurl;
	}

	public void setConfirmurl(String confirmurl) {
		this.confirmurl = confirmurl;
	}

	@Override
	public void doAfterUpdate(GetMedicineRecord t) {
		// TODO Auto-generated method stub
		
	}


}
