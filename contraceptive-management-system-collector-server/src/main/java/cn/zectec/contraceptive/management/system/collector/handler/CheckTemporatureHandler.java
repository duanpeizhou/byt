package cn.zectec.contraceptive.management.system.collector.handler;

import javax.annotation.Resource;

import cn.zectec.contraceptive.management.system.collector.util.AttrName;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.sdk.Context;
import cn.zectec.contraceptive.management.system.sdk.MessageReceivedHandler;
import cn.zectec.contraceptive.management.system.sdk.message.Request;
import cn.zectec.contraceptive.management.system.sdk.message.StatusReportRequestMessage;
import cn.zectec.contraceptive.management.system.service.IMachineryEquipmentService;

public class CheckTemporatureHandler implements MessageReceivedHandler{
	@Resource
	private IMachineryEquipmentService machineryEquipmentService;

	@Override
	public void handle(Context context, Request request) {
		if(request.getMessage() instanceof StatusReportRequestMessage){
			MachineryEquipment me = (MachineryEquipment)context.attr(AttrName.MachineryEquipment);
			machineryEquipmentService.checkTemporature(me.getId(),((StatusReportRequestMessage)request.getMessage()).getTemporature());
		}
		context.doNextHandler();
	}

}
