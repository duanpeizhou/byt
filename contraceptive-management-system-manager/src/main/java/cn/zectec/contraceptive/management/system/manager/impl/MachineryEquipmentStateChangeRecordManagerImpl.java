package cn.zectec.contraceptive.management.system.manager.impl;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import cn.zectec.contraceptive.management.system.manager.IMachineryEquipmentStateChangeRecordManager;
import cn.zectec.contraceptive.management.system.model.Aisle;
import cn.zectec.contraceptive.management.system.model.AisleFailureRecord;
import cn.zectec.contraceptive.management.system.model.CardReaderFailureRecord;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentState;
import cn.zectec.contraceptive.management.system.model.MachineryEquipmentStateChangeRecord;
import cn.zectec.contraceptive.management.system.model.OnlineOfflineRecord;
import cn.zectec.contraceptive.management.system.model.OpenDoorRecord;
import cn.zectec.contraceptive.management.system.model.OutStockReplenishmentRecord;
import cn.zectec.contraceptive.management.system.model.OverTemperatureRecord;
import cn.zectec.contraceptive.management.system.repository.IAisleFailureRecordRepository;
import cn.zectec.contraceptive.management.system.repository.ICardReaderFailureRecordRepository;
import cn.zectec.contraceptive.management.system.repository.IMachineryEquipmentStateChangeRecordRepository;
import cn.zectec.contraceptive.management.system.repository.IOnlineOfflineRecordRepository;
import cn.zectec.contraceptive.management.system.repository.IOpenDoorRecordRepository;
import cn.zectec.contraceptive.management.system.repository.IOutStockReplenishmentRecordRepository;
import cn.zectec.contraceptive.management.system.repository.IOverTemperatureRecordRepository;
import cn.zectec.contraceptive.management.system.repository.IRecordRepository;

@Component
public class MachineryEquipmentStateChangeRecordManagerImpl extends SimpleBaseManagerImpl<MachineryEquipmentStateChangeRecord> implements IMachineryEquipmentStateChangeRecordManager {
	private IMachineryEquipmentStateChangeRecordRepository machineryEquipmentStateChangeRecordRepository;
	@Autowired
	private IRecordRepository recordRepository;
	@Autowired
	private IOutStockReplenishmentRecordRepository outStockReplenishmentRecordRepository;
	@Autowired
	private IOnlineOfflineRecordRepository onlineOfflineRecordRepository;
	@Autowired
	private IOpenDoorRecordRepository openDoorRecordRepository;
	@Autowired
	private IOverTemperatureRecordRepository overTemperatureRecordRepository;
	@Autowired
	private IAisleFailureRecordRepository aisleFailureRecordRepository;
	@Autowired
	private ICardReaderFailureRecordRepository cardReaderFailureRecordRepository;
	@Autowired
	public MachineryEquipmentStateChangeRecordManagerImpl(
			IMachineryEquipmentStateChangeRecordRepository baseRepository) {
		super(baseRepository);
		this.machineryEquipmentStateChangeRecordRepository = baseRepository;
	}

	@Override
	public void addStockout(Aisle aisle, int number,boolean stockout) {
		Date date = new Date();
		MachineryEquipmentStateChangeRecord machineryEquipmentStateChangeRecord = new MachineryEquipmentStateChangeRecord();
		machineryEquipmentStateChangeRecord.setMachineryEquipment(aisle
				.getMachineryEquipment());
		machineryEquipmentStateChangeRecord.setContraceptive(aisle
				.getContraceptive());
		machineryEquipmentStateChangeRecord.setHanpenDate(date);
		machineryEquipmentStateChangeRecord.setDetail("第" + aisle.getIndex_()+ "货道缺货，货物数量" + number);
		machineryEquipmentStateChangeRecord.setState(MachineryEquipmentState.OutStock);
		if(!stockout){
			OutStockReplenishmentRecord outStockReplenishmentRecord = new OutStockReplenishmentRecord();
			outStockReplenishmentRecord.setContraceptive(aisle.getContraceptive());
			outStockReplenishmentRecord.setAisle(aisle);
			outStockReplenishmentRecord.setMachineryEquipment(aisle
					.getMachineryEquipment());
			outStockReplenishmentRecord.setOutStockDate(date);
			recordRepository.save(Arrays.asList(machineryEquipmentStateChangeRecord,outStockReplenishmentRecord));
		}
		recordRepository.save(machineryEquipmentStateChangeRecord);
	}

	@Override
	public void addReplenishment(Aisle aisle, int number,boolean stockout) {
		Date date = new Date();
		MachineryEquipmentStateChangeRecord machineryEquipmentStateChangeRecord = new MachineryEquipmentStateChangeRecord();
		machineryEquipmentStateChangeRecord.setMachineryEquipment(aisle
				.getMachineryEquipment());
		machineryEquipmentStateChangeRecord.setContraceptive(aisle
				.getContraceptive());
		machineryEquipmentStateChangeRecord.setHanpenDate(date);
		machineryEquipmentStateChangeRecord.setDetail("第" + aisle.getIndex_()+ "货道补货，货物数量" + number);
		machineryEquipmentStateChangeRecord
				.setState(MachineryEquipmentState.Replenishment);
		OutStockReplenishmentRecord outStockReplenishmentRecord = null;
		if(stockout){
			try {
				outStockReplenishmentRecord = outStockReplenishmentRecordRepository
						.findCurrentOutStockRecored(aisle,new PageRequest(0, 1)).getContent().get(0);
			} catch (Exception e) {
			}
			if (outStockReplenishmentRecord != null) {
				outStockReplenishmentRecord.setReplenishmentDate(date);
				recordRepository.save(outStockReplenishmentRecord);
			}
		}
		recordRepository.save(machineryEquipmentStateChangeRecord);
	}

	@Override
	public void addOnlineOfflineRecord(MachineryEquipment me, boolean onlinedate) {
		Date date = new Date();
		MachineryEquipmentStateChangeRecord machineryEquipmentStateChangeRecord = new MachineryEquipmentStateChangeRecord();
		machineryEquipmentStateChangeRecord.setMachineryEquipment(me);
		machineryEquipmentStateChangeRecord.setHanpenDate(date);
		if (onlinedate) {
			OnlineOfflineRecord onlineOfflineRecord = new OnlineOfflineRecord();
			onlineOfflineRecord.setMachineryEquipment(me);
			onlineOfflineRecord.setOnlineDate(date);
			machineryEquipmentStateChangeRecord.setState(MachineryEquipmentState.Online);
			machineryEquipmentStateChangeRecord.setDetail("上线");
			recordRepository.save(onlineOfflineRecord);
		} else {
			machineryEquipmentStateChangeRecord.setState(MachineryEquipmentState.OffineLine);
			machineryEquipmentStateChangeRecord.setDetail("离线");
			OnlineOfflineRecord onlineOfflineRecord = null;
			try {
				onlineOfflineRecord = onlineOfflineRecordRepository
						.findCurrentOnlineOffline(me,new PageRequest(0, 1)).getContent().get(0);
			} catch (Exception e) {
			}
			if(onlineOfflineRecord != null){
				onlineOfflineRecord.setOfflineDate(date);
				recordRepository.save(onlineOfflineRecord);
			}
			
		}
		recordRepository.save(machineryEquipmentStateChangeRecord);

	}

	@Override
	public void addDoorStateChangeRecord(MachineryEquipment me,boolean doorState) {
		Date date = new Date();
		MachineryEquipmentStateChangeRecord machineryEquipmentStateChangeRecord = new MachineryEquipmentStateChangeRecord();
		machineryEquipmentStateChangeRecord.setMachineryEquipment(me);
		machineryEquipmentStateChangeRecord.setHanpenDate(date);
		if (doorState) {
			OpenDoorRecord openDoorRecord = new OpenDoorRecord();
			openDoorRecord.setOpenDoorDate(date);
			openDoorRecord.setMachineryEquipment(me);
			machineryEquipmentStateChangeRecord.setState(MachineryEquipmentState.OpenDoor);
			machineryEquipmentStateChangeRecord.setDetail("开门");
			recordRepository.save(openDoorRecord);
		} else {
			machineryEquipmentStateChangeRecord.setState(MachineryEquipmentState.CloseDoor);
			machineryEquipmentStateChangeRecord.setDetail("关门");
			OpenDoorRecord openDoorRecord = null;
			try {
				openDoorRecord = openDoorRecordRepository.findCurrentOopenDoorRecord(me,new PageRequest(0, 1)).getContent().get(0);
			} catch (Exception e) {
			}
			if(openDoorRecord != null){
				openDoorRecord.setCloseDoorDate(date);
				recordRepository.save(openDoorRecord);
			}
		}
		recordRepository.save(machineryEquipmentStateChangeRecord);
	}

	@Override
	public void addOverTemporatureRecord(MachineryEquipment me, int temporature,boolean state) {
		Date date = new Date();
		MachineryEquipmentStateChangeRecord machineryEquipmentStateChangeRecord = new MachineryEquipmentStateChangeRecord();
		machineryEquipmentStateChangeRecord.setMachineryEquipment(me);
		machineryEquipmentStateChangeRecord.setHanpenDate(date);
		if (state) {
			OverTemperatureRecord overTemperatureRecord = new OverTemperatureRecord();
			overTemperatureRecord.setOverTemperature(temporature);
			overTemperatureRecord.setMachineryEquipment(me);
			overTemperatureRecord.setOverTemperatureDate(date);
			machineryEquipmentStateChangeRecord.setState(MachineryEquipmentState.Overtemperature);
			machineryEquipmentStateChangeRecord.setDetail("超温，当前设备温度"+temporature+"℃");
			recordRepository.save(overTemperatureRecord);
		} else {
			machineryEquipmentStateChangeRecord.setState(MachineryEquipmentState.TemperatureRecovery);
			machineryEquipmentStateChangeRecord.setDetail("温度恢复，当前设备温度"+temporature+"℃");
			OverTemperatureRecord overTemperatureRecord  = null;
			try {
				overTemperatureRecord = overTemperatureRecordRepository.findCurrentoverTemperatureRecord(me,new PageRequest(0, 1)).getContent().get(0);
			} catch (Exception e) {
			}
			if(overTemperatureRecord != null){
				overTemperatureRecord.setRecoveryTemperature(temporature);
				overTemperatureRecord.setRecoveryDate(date);
				recordRepository.save(overTemperatureRecord);
			}
		}
		recordRepository.save(machineryEquipmentStateChangeRecord);
	}

	@Override
	public void addAisleFailureRecord(Aisle aisle, boolean b) {
		Date date = new Date();
		MachineryEquipmentStateChangeRecord machineryEquipmentStateChangeRecord = new MachineryEquipmentStateChangeRecord();
		machineryEquipmentStateChangeRecord.setMachineryEquipment(aisle.getMachineryEquipment());
		machineryEquipmentStateChangeRecord.setHanpenDate(date);
		if (b) {
			//故障
			AisleFailureRecord aisleFailureRecord = new AisleFailureRecord();
			aisleFailureRecord.setAisle(aisle);
			aisleFailureRecord.setFailureDate(date);
			aisleFailureRecord.setMachineryEquipment(aisle.getMachineryEquipment());
			recordRepository.save(aisleFailureRecord);
			machineryEquipmentStateChangeRecord.setState(MachineryEquipmentState.AisleFailure);
			machineryEquipmentStateChangeRecord.setDetail("第"+aisle.getIndex_()+"货道故障");
		} else {
			//恢复
			machineryEquipmentStateChangeRecord.setState(MachineryEquipmentState.AisleFailureRecovery);
			machineryEquipmentStateChangeRecord.setDetail("第"+aisle.getIndex_()+"货道故障恢复");
			AisleFailureRecord aisleFailureRecord  = null;
			try {
				aisleFailureRecord = aisleFailureRecordRepository.findCurrentoveraisleFailureRecord(aisle,new PageRequest(0, 1)).getContent().get(0);
			} catch (Exception e) {
			}
			if(aisleFailureRecord != null){
				aisleFailureRecord.setRecoveryDate(date);
				recordRepository.save(aisleFailureRecord);
			}
		}
		recordRepository.save(machineryEquipmentStateChangeRecord);
	}

	@Override
	public void addCardReaderFailureRecord(MachineryEquipment me,boolean cardReader) {
		Date date = new Date();
		MachineryEquipmentStateChangeRecord machineryEquipmentStateChangeRecord = new MachineryEquipmentStateChangeRecord();
		machineryEquipmentStateChangeRecord.setMachineryEquipment(me);
		machineryEquipmentStateChangeRecord.setHanpenDate(date);
		if (cardReader) {
			CardReaderFailureRecord cardReaderFailureRecord = new CardReaderFailureRecord();
			cardReaderFailureRecord.setFailureDate(date);
			cardReaderFailureRecord.setMachineryEquipment(me);
			machineryEquipmentStateChangeRecord.setState(MachineryEquipmentState.CardReaderFailure);
			machineryEquipmentStateChangeRecord.setDetail("读卡器故障");
			recordRepository.save(cardReaderFailureRecord);
		} else {
			machineryEquipmentStateChangeRecord.setState(MachineryEquipmentState.CardReaderFailureRecovery);
			machineryEquipmentStateChangeRecord.setDetail("读卡器故障恢复");
			CardReaderFailureRecord cardReaderFailureRecord  = null;
			try {
				cardReaderFailureRecord = cardReaderFailureRecordRepository.findCurrentcardReaderFailureRecord(me,new PageRequest(0, 1)).getContent().get(0);
			} catch (Exception e) {
			}
			if(cardReaderFailureRecord != null){
				cardReaderFailureRecord.setRecoveryDate(new Date());
				recordRepository.save(cardReaderFailureRecord);
			}
		}
		recordRepository.save(machineryEquipmentStateChangeRecord);
	}
}
