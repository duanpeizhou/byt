package cn.zectec.contraceptive.management.system.collector.handler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.collector.util.AttrName;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.sdk.Context;
import cn.zectec.contraceptive.management.system.sdk.MessageReceivedHandler;
import cn.zectec.contraceptive.management.system.sdk.message.Request;
import cn.zectec.contraceptive.management.system.service.IMachineryEquipmentService;

@Component
public class GetEquipmentHandler implements MessageReceivedHandler{
	@Autowired
	private IMachineryEquipmentService machineryEquipmentService;
	private static Logger logger = Logger.getLogger(GetEquipmentHandler.class);
	@Override
	public void handle(Context context, Request request) {
		try{
			MachineryEquipment me = machineryEquipmentService.getMachineryEquipmentByDeviceNo(request.getMessage().getTerminalNo());
			if(me != null){
				context.attr(AttrName.MachineryEquipment, me);
			}else{
				logger.error("在数据库中没有找到设备编号为："+request.getMessage().getTerminalNo()+"的机器");
			}
		}catch(Exception e){
			logger.error("GetEquipmentHandler  出错了", e);
		}
		context.doNextHandler();
			
	}

}
