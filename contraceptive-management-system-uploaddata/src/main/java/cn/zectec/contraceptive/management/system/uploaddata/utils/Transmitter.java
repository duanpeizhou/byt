package cn.zectec.contraceptive.management.system.uploaddata.utils;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;

public class Transmitter<K> implements Runnable {
	
	private String url;
	private ResponseHandler<K, String> handler;
	private K k;
	private static Logger logger = Logger.getLogger(Transmitter.class);
	public Transmitter(String url,K k, ResponseHandler<K, String> handler) {
		super();
		this.url = url;
		this.k = k;
		this.handler = handler;
	}

	@Override
	public void run() {
//		PostMethod postMethod = new PostMethod(url.trim());
		try {
			String message = EntityConverter.entity2Tags(k);
//			RequestEntity requestEntity = new StringRequestEntity(message,"application/xml","GBK");
//			postMethod.setRequestEntity(requestEntity);
//			HttpClient client= new HttpClient();
//			client.executeMethod(postMethod);
//			BufferedReader br= new BufferedReader(new InputStreamReader(postMethod.getResponseBodyAsStream(), "GBK"));
//			String responseBodyAsString=br.readLine();
//			if (responseBodyAsString != null && handler != null) {
//				handler.handle(k, responseBodyAsString);
//			}
//			postMethod.releaseConnection();
//			logger.info("向："+url+"发送的数据为："+message+"\n返回的数据为："+responseBodyAsString);
			
			Service service = new Service();
			Call call = (Call) service.createCall();// 通过service创建call对象
			call.setTargetEndpointAddress(new java.net.URL(url));
			call.setOperationName(new QName("http://service.nfptsis.ws.nrifp.org","ImportData"));
			call.addParameter(new QName("http://service.nfptsis.ws.nrifp.org","data"), org.apache.axis.encoding.XMLType.XSD_STRING,	javax.xml.rpc.ParameterMode.IN);
			call.setUseSOAPAction(true);
			call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_STRING);
			// 返回参数的类型
			call.setSOAPActionURI("http://service.nfptsis.ws.nrifp.org/ImportData");
			// 这个也要注意
			String responseBodyAsString = (String) call.invoke(new Object[] { message });
			if (responseBodyAsString != null && handler != null) {
				handler.handle(k, responseBodyAsString);
			}
			logger.debug("向："+url+"发送的数据为："+message+"\n返回的数据为："+responseBodyAsString);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error((e.getStackTrace()));
		}
		
	}

}
