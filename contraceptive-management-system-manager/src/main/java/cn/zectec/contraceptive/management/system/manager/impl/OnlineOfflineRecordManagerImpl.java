package cn.zectec.contraceptive.management.system.manager.impl;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.IOnlineOfflineRecordManager;
import cn.zectec.contraceptive.management.system.model.OnlineOfflineRecord;
import cn.zectec.contraceptive.management.system.repository.IOnlineOfflineRecordRepository;


@Component
public class OnlineOfflineRecordManagerImpl extends SimpleBaseManagerImpl<OnlineOfflineRecord> implements
		IOnlineOfflineRecordManager {
	private IOnlineOfflineRecordRepository onlineOfflineRecordRepository; 
	
	@Autowired
	public OnlineOfflineRecordManagerImpl(IOnlineOfflineRecordRepository baseRepository) {
		super(baseRepository);
		this.onlineOfflineRecordRepository=baseRepository;
		
	}

}
