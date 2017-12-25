package cn.zectec.contraceptive.management.system.collector.handler;

import org.springframework.beans.factory.annotation.Autowired;

import cn.zectec.contraceptive.management.system.collector.util.AttrName;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.sdk.Context;
import cn.zectec.contraceptive.management.system.sdk.MessageReceivedHandler;
import cn.zectec.contraceptive.management.system.sdk.message.Request;
import cn.zectec.contraceptive.management.system.sdk.message.StatusReportRequestMessage;
import cn.zectec.contraceptive.management.system.service.IMachineryEquipmentService;

public class CheckStockOutHandler implements MessageReceivedHandler{
	@Autowired
	private IMachineryEquipmentService machineryEquipmentService;
	
	@Override
	public void handle(Context context, Request request) {
		if(request.getMessage() instanceof StatusReportRequestMessage){
			MachineryEquipment me = (MachineryEquipment)context.attr(AttrName.MachineryEquipment);
			machineryEquipmentService.checkStock(me.getId(),((StatusReportRequestMessage)request.getMessage()).getCargoLeft());
		}
		context.doNextHandler();
	}

}
