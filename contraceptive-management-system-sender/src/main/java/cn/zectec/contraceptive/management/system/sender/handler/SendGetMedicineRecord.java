package cn.zectec.contraceptive.management.system.sender.handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.IGetMedicineRecordManager;
import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.model.GiveOutInfo;
import cn.zectec.contraceptive.management.system.model.IDCardInfo;
import cn.zectec.contraceptive.management.system.repository.listener.DataChangeHandler;
import cn.zectec.contraceptive.management.system.repository.listener.GetMedicineRecordListener;
import cn.zectec.contraceptive.management.system.sender.util.ChangeMessageEntity;
import cn.zectec.contraceptive.management.system.sender.util.MessageConverter;
import cn.zectec.contraceptive.management.system.sender.util.SendMsg;

@Component
public class SendGetMedicineRecord implements DataChangeHandler<GetMedicineRecord>,ApplicationContextAware{

	
	private ExecutorService threadPool;
	private static Logger logger=Logger.getLogger(SendGetMedicineRecord.class);
	private ApplicationContext applicationContext;
	private String url;
	public SendGetMedicineRecord(){
		threadPool = Executors.newCachedThreadPool();
		logger.debug("生成了   UpdateGetMedicineRecordListenerImpl ");
	}
	



	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		GetMedicineRecordListener.registeHandler(this);
	}
	
	


	public String getUrl() {
		return url;
	}




	public void setUrl(String url) {
		this.url = url;
	}




	@Override
	public void doAfterSave(GetMedicineRecord o) {

		if(applicationContext != null){
			final IGetMedicineRecordManager getMedicineRecordManager = applicationContext.getBean(IGetMedicineRecordManager.class);
			
			IDCardInfo idCardInfo=ChangeMessageEntity.getIdCardInfo(o);
			GiveOutInfo giveOutInfo=ChangeMessageEntity.getGiveOutInfo(o);
			
			String jsonString=MessageConverter.entity2JsonString(giveOutInfo, idCardInfo);
			logger.debug("发送的地址为："+url);
			logger.debug("发送的药物领用记录数据为："+jsonString);
			
			SendMsg<GetMedicineRecord> send=new SendMsg<GetMedicineRecord>(url, jsonString, o, new ResponseHandler<GetMedicineRecord,String>() {

				@Override
				public void handle(GetMedicineRecord getMedicineRecord, String responseString) {
					if("1".equals(responseString)){
						logger.info("数据上传成功");
						getMedicineRecordManager.updateSendStatus(true,getMedicineRecord.getId());
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
		}
		
		
//		System.out.println(o.getName());
//		System.out.println("getMediceneRecord");
	
	}

	@Override
	public void doAfterUpdate(GetMedicineRecord t) {
//		
	}



}
