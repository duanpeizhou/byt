package cn.zectec.contraceptive.management.system.collector.handler;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.zectec.contraceptive.management.system.collector.util.AttrName;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.sdk.Context;
import cn.zectec.contraceptive.management.system.sdk.MessageReceivedHandler;
import cn.zectec.contraceptive.management.system.sdk.Session;
import cn.zectec.contraceptive.management.system.sdk.message.Request;
import cn.zectec.contraceptive.management.system.service.IMachineryEquipmentService;

public class UpdateConnectedStateHandler implements MessageReceivedHandler{
	@Autowired
	private IMachineryEquipmentService machineryEquipmentService;
	private static Logger logger = Logger.getLogger(UpdateConnectedStateHandler.class);

	@Override
	public void handle(Context context, Request request) {
		try {
			Session session = context.session();
			MachineryEquipment me = (MachineryEquipment)context.attr(AttrName.MachineryEquipment);
			MachineryEquipment me_ = (MachineryEquipment)session.attr(AttrName.MachineryEquipment);
			if(me_ == null){
				session.attr(AttrName.MachineryEquipment,me);
				me = machineryEquipmentService.getMachineryEquipmentById(me.getId());
				if(!me.getMachineryEquipmentState().isConnectionState()){
					machineryEquipmentService.updateEquipmentConnectionState(me.getId(),true,(Date)session.attr(AttrName.ConnetDate));
				}
			}else{
				if(me.getId() != me_.getId()){
					return;
				}
			}
		} catch (Exception e) {
			logger.error("UpdateConnectedStateHandler  出错了  ",e);
			e.printStackTrace();
		}
		context.doNextHandler();
	}

}
