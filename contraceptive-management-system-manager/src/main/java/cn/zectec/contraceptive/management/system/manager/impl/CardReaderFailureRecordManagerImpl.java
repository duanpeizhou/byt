package cn.zectec.contraceptive.management.system.manager.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.ICardReaderFailureRecordManager;
import cn.zectec.contraceptive.management.system.model.CardReaderFailureRecord;
import cn.zectec.contraceptive.management.system.repository.ICardReaderFailureRecordRepository;
@Component
public class CardReaderFailureRecordManagerImpl extends SimpleBaseManagerImpl<CardReaderFailureRecord> implements
		ICardReaderFailureRecordManager {
	
	private ICardReaderFailureRecordRepository cardReaderFailureRecordRepository;
	@Autowired
	public CardReaderFailureRecordManagerImpl(ICardReaderFailureRecordRepository baseRepository) {
		super(baseRepository);
		this.cardReaderFailureRecordRepository=baseRepository;
	}



}
