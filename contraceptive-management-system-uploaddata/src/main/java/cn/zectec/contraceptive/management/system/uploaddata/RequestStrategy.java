package cn.zectec.contraceptive.management.system.uploaddata;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import cn.zectec.contraceptive.management.system.uploaddata.utils.ResponseHandler;
import cn.zectec.contraceptive.management.system.uploaddata.utils.Transmitter;

public class RequestStrategy implements ApplicationContextAware{
	
	private ApplicationContext applicationContext;
	
	private ExecutorService threadPool = Executors.newCachedThreadPool();
	
	private String url;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
//	@Scheduled(fixedRate=1000*60*1)
	public void requestStrategy(){
		Transmitter<Object> transmitter = new Transmitter<>(url, "ss",new ResponseHandler<Object, String>() {

			@Override
			public void handle(Object k, String t) {
				try {
					Document dom = DocumentHelper.parseText(t);
					Element rootElement = dom.getRootElement();
					@SuppressWarnings("unchecked")
					Iterator<Element> ei = rootElement.elementIterator();
					while (ei.hasNext()) {
						Element type = ei.next();
						System.err.println("element name="+type.getName()+",value="+type.getTextTrim());
					}
				} catch (DocumentException e) {
					e.printStackTrace();
				}
			}

		});
		threadPool.execute(transmitter);
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
