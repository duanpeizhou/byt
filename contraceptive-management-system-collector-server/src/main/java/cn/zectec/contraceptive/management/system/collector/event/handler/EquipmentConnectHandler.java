package cn.zectec.contraceptive.management.system.collector.event.handler;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.collector.util.AttrName;
import cn.zectec.contraceptive.management.system.sdk.Context;
import cn.zectec.contraceptive.management.system.sdk.SessionEventHandler;

@Component
public class EquipmentConnectHandler implements SessionEventHandler{
	private static Logger logger = Logger.getLogger(EquipmentConnectHandler.class);

	@Override
	public void handleEvent(Context context, Event event) {
		
		if(event == Event.Connect){
			logger.debug("有连接");
			//目前不知道是什么  只能存一个上线时间
			context.session().attr(AttrName.ConnetDate, new Date());
		}
	}

}
