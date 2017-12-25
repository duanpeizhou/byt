package cn.zectec.contraceptive.management.system.sender.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.log4j.Logger;

import cn.zectec.contraceptive.management.system.model.Base;
import cn.zectec.contraceptive.management.system.sender.handler.ResponseHandler;

public class SendMsg<K extends Base> implements Runnable{
	private String url;
	private String postData;
	private ResponseHandler<K,String> responseHandler;
	private K getMedicineRecord;
	private static Logger logger=Logger.getLogger(SendMsg.class);
	public SendMsg(String url, String postData,K getMedicineRecord,ResponseHandler<K,String> re) {
		super();
		this.url = url;
		this.postData = postData;
		this.responseHandler = re;
		this.getMedicineRecord=getMedicineRecord;
	}
	@Override
	public void run() {
		PostMethod post=new PostMethod(url.trim());
		String responseString=null;
		 try {
			RequestEntity rquestEntity = new StringRequestEntity(postData,"application/json","UTF-8");
			post.setRequestEntity(rquestEntity);
			HttpClient httpclient = new HttpClient();
			httpclient.executeMethod(post);
			responseString= post.getResponseBodyAsString();
			responseHandler.handle(getMedicineRecord, responseString);
			post.releaseConnection();
		} catch (Exception e) {
			logger.error(e.getStackTrace().toString());
		}
		//System.out.println("收到响应："+responseString);
	}
}
