package cn.zectec.contraceptive.management.system.sender.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.repository.IGetMedicineRecordRepository;
import cn.zectec.contraceptive.management.system.sender.service.IGetMedicineService;

@Service
public class GetMedicineServiceImpl implements IGetMedicineService{
	
	@Autowired
	IGetMedicineRecordRepository getMedicineRecordRepository;

	@Override
	public List<GetMedicineRecord> findNotSentRecords() {
		// TODO Auto-generated method stub
		return getMedicineRecordRepository.findNotSendRecords();
	}

	@Override
	public boolean saveGetMedicineRecords(List<GetMedicineRecord> records) {
		// TODO Auto-generated method stub
		if(getMedicineRecordRepository.save(records)!=null)
			return true;
		return false;
	}

	@Override
	public List<GetMedicineRecord> find10NotSentRecords() {
		// TODO Auto-generated method stub
		Pageable pageable = new Pageable() {
			
			@Override
			public Sort getSort() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getPageSize() {
				// TODO Auto-generated method stub
				return 10;
			}
			
			@Override
			public int getPageNumber() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getOffset() {
				// TODO Auto-generated method stub
				return 0;
			}
		};
		
		return getMedicineRecordRepository.find10NotSendRecords(pageable);
	}

}
