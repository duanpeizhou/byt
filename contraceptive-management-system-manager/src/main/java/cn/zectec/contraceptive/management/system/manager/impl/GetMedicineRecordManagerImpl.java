package cn.zectec.contraceptive.management.system.manager.impl;


import cn.zectec.contraceptive.management.system.manager.IGetMedicineRecordManager;
import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.repository.IGetMedicineRecordRepository;
import cn.zectec.contraceptive.management.system.repository.util.DynamicSpecifications;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class GetMedicineRecordManagerImpl extends SimpleBaseManagerImpl<GetMedicineRecord> implements IGetMedicineRecordManager {
	private IGetMedicineRecordRepository getMedicineRecordRepository;
	@Autowired
	public GetMedicineRecordManagerImpl(
			IGetMedicineRecordRepository baseRepository) {
		super(baseRepository);
		this.getMedicineRecordRepository=baseRepository;
	}
	@Override
	public long getCounts(Date startTime, Date endTime,String areaName,Area area) {
		List<SearchFilter> filters=new ArrayList<SearchFilter>();
		SearchFilter startTImeFilter=new SearchFilter("addDate", Operator.GTE,startTime);
		SearchFilter endTimeFilter=new SearchFilter("addDate",Operator.LTE,endTime);
		if(areaName!=null&&areaName.length()>0){
			SearchFilter areaFilter=new SearchFilter(areaName,area);
			filters.add(areaFilter);
		}
		filters.add(startTImeFilter);
		filters.add(endTimeFilter);
		Specification<GetMedicineRecord> spec=DynamicSpecifications.bySearchFilters(filters);
		return getMedicineRecordRepository.count(spec);
	}
	@Override
	public void updateSendStatus(boolean b, long id) {
		getMedicineRecordRepository.updateSendStatus(b, id);
	}
	@Override
	public List<GetMedicineRecord> findNotSendRecords() {
		return getMedicineRecordRepository.findNotSendRecords();
	}

	@Override
	public List<GetMedicineRecord> find10NotSendRecords() {
		Pageable pageable = new Pageable() {
			@Override
			public Sort getSort() {
				return null;
			}

			@Override
			public int getPageSize() {
				return 10;
			}

			@Override
			public int getPageNumber() {
				return 0;
			}

			@Override
			public int getOffset() {
				return 0;
			}
		};
		return getMedicineRecordRepository.find10NotSendRecords(pageable);
	}
}
