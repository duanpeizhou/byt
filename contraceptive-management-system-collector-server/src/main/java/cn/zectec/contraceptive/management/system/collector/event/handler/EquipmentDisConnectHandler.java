package cn.zectec.contraceptive.management.system.collector.event.handler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.collector.util.AttrName;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.sdk.Context;
import cn.zectec.contraceptive.management.system.sdk.Session;
import cn.zectec.contraceptive.management.system.sdk.SessionEventHandler;
import cn.zectec.contraceptive.management.system.service.IMachineryEquipmentService;

@Component
public class EquipmentDisConnectHandler implements SessionEventHandler{
	@Autowired
	private IMachineryEquipmentService machineryEquipmentService;
	private static Logger logger = Logger.getLogger(EquipmentDisConnectHandler.class);
	
	@Override
	public void handleEvent(Context context, Event event) {
		if(event == Event.DisConnect){
			logger.debug("失去连接");
			Session session = context.session();
			MachineryEquipment me = null;
			try{
				me = (MachineryEquipment)session.attr(AttrName.MachineryEquipment);
			}catch(Exception e){
				e.printStackTrace();
			}
			if(me == null){
				return;
			}
			//machineryEquipmentService.updateEquipmentConnectionState(me.getId(),false,new Date());
		}
	}

}
