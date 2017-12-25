package cn.zectec.contraceptive.management.system.sdk.codec;

import org.apache.log4j.Logger;

import cn.zectec.contraceptive.management.system.sdk.message.OfflineGetRequestMessage;
import cn.zectec.contraceptive.management.system.sdk.message.OnlineGetRequestMessage;
import cn.zectec.contraceptive.management.system.sdk.message.Request;
import cn.zectec.contraceptive.management.system.sdk.message.Response;
import cn.zectec.contraceptive.management.system.sdk.message.StatusReportRequestMessage;
import cn.zectec.contraceptive.management.system.sdk.message.TimeCheckRequestMessage;
import cn.zectec.contraceptive.management.system.utils.ByteUtil;

public class MessageCodec {
	private static Logger logger = Logger.getLogger(MessageCodec.class);
	public byte[] encode(Response response) {

		return response.bytes();
	}

	public Request decode(byte[] data) {
		Request r = new Request();
		switch (data[3]) {
		case 0x01:
			r.setMessage(new OnlineGetRequestMessage());
			break;
		case 0x02:
			r.setMessage(new OfflineGetRequestMessage());
			break;
		case 0x03:
			r.setMessage(new StatusReportRequestMessage());
			break;
		case 0x04:
			r.setMessage(new TimeCheckRequestMessage());
			break;
		default:
			logger.error("找不到对应的request："+ByteUtil.outputHexofByte(data));
			break;
		}
		if (r.parse(data)) {
			return r;
		}
		return null;
	}
}
