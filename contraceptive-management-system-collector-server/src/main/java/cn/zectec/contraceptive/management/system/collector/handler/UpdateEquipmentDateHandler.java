package cn.zectec.contraceptive.management.system.collector.handler;

import java.util.Date;

import cn.zectec.contraceptive.management.system.collector.util.AttrName;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.sdk.Context;
import cn.zectec.contraceptive.management.system.sdk.MessageReceivedHandler;
import cn.zectec.contraceptive.management.system.sdk.message.Request;
import cn.zectec.contraceptive.management.system.sdk.message.Response;
import cn.zectec.contraceptive.management.system.sdk.message.TimeCheckRequestMessage;
import cn.zectec.contraceptive.management.system.sdk.message.TimeCheckResponseMessage;

public class UpdateEquipmentDateHandler implements MessageReceivedHandler{

	@Override
	public void handle(Context context, Request request) {
		MachineryEquipment me = (MachineryEquipment)context.attr(AttrName.MachineryEquipment);
		if(me != null){
			if(request.getMessage() instanceof TimeCheckRequestMessage){
				Response response = new Response();
				TimeCheckResponseMessage message = new TimeCheckResponseMessage();
				message.setTime(new Date());
				message.setTerminalNo((int)me.getDeviceNo());
				response.setMessage(message);
				context.wirte(response);
			}
		}
		context.doNextHandler();
	}

}
