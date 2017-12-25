package cn.zectec.contraceptive.management.system.collector.handler;

import cn.zectec.contraceptive.management.system.collector.util.AttrName;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.sdk.Context;
import cn.zectec.contraceptive.management.system.sdk.MessageReceivedHandler;
import cn.zectec.contraceptive.management.system.sdk.message.Request;
import cn.zectec.contraceptive.management.system.sdk.message.Response;
import cn.zectec.contraceptive.management.system.sdk.message.StatusReportRequestMessage;
import cn.zectec.contraceptive.management.system.sdk.message.StatusReportResponseMessage;

public class StatusReportResponseHandler implements MessageReceivedHandler{

	@Override
	public void handle(Context context, Request request) {
		MachineryEquipment me = (MachineryEquipment)context.attr(AttrName.MachineryEquipment);
		if(me != null && request.getMessage() instanceof StatusReportRequestMessage){
			Response response = new Response();
			StatusReportResponseMessage message = new StatusReportResponseMessage();
			message.setTerminalNo((int)me.getDeviceNo());
			response.setMessage(message);
			context.wirte(response);
		}
		context.doNextHandler();
	}

}
