package cn.zectec.contraceptive.management.system.security.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.zectec.contraceptive.management.system.model.Aisle;
import cn.zectec.contraceptive.management.system.model.Area;
import cn.zectec.contraceptive.management.system.model.GetMedicineRecord;
import cn.zectec.contraceptive.management.system.model.MachineryEquipment;
import cn.zectec.contraceptive.management.system.model.Manager;
import cn.zectec.contraceptive.management.system.model.Record;
import cn.zectec.contraceptive.management.system.repository.util.SearchFilter;
import cn.zectec.contraceptive.management.system.service.IAreaService;

public class DataFilter implements MethodInterceptor {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(DataFilter.class);
	@Autowired
	private IAreaService areaService;

	@Override
	@SuppressWarnings("rawtypes")
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Class<?> clazz = null;
		try {
			Type type = invocation.getThis().getClass().getGenericSuperclass();
			Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
			clazz = (Class) trueType;
		} catch (Exception e) {
			e.printStackTrace();
			return invocation.proceed();
		}
		if (Record.class.isAssignableFrom(clazz)) {
			return doFilterRecord(invocation);
		} else if (clazz.equals(MachineryEquipment.class)) {
			return doFilterMachineryEquipment(invocation);
		} else if (clazz.equals(Aisle.class)) {
			return doFilterAisle(invocation);
		} else if(GetMedicineRecord.class.equals(clazz)){
			return doFilterRecord(invocation);
		}/*
		 * else if (clazz.isAssignableFrom(Statistical.class)) { return
		 * doFilterStatistical(invocation); }
		 */else {
			return invocation.proceed();
		}
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private Object doFilterStatistical(MethodInvocation invocation)
			throws Throwable {
		Manager manager = SecurityContext.getCurrentManager();
		if (manager == null) {
			return invocation.proceed();
		}
		try {
			if (manager.getTownshipStreet() != null) {
				Object[] parameter = invocation.getArguments();
				if (parameter[0].getClass().equals(List.class)) {
					List<SearchFilter> searchFilters = (List<SearchFilter>) parameter[0];
					if (searchFilters == null) {
						searchFilters = new ArrayList<SearchFilter>();
						SearchFilter s = new SearchFilter("area.parentArea",
								manager.getTownshipStreet());
						searchFilters.add(s);
					} else {
						boolean flag = false;
						for (SearchFilter s : searchFilters) {
							if (s.fieldName.equals("area.parentArea.id")) {
								Area area = areaService.getAreaById(Long
										.parseLong(s.value.toString()));
								switch (area.getLevel()) {
								case Province:
								case City:
								case County:
								case TownshipStreet:
									searchFilters.remove(s);
									SearchFilter s_ = new SearchFilter(
											"area.parentArea",
											manager.getTownshipStreet());
									searchFilters.add(s_);
									break;
								default:
									break;
								}
								flag = true;
								break;
							}
						}
						if (!flag) {
							SearchFilter s = new SearchFilter(
									"area.parentArea",
									manager.getTownshipStreet());
							searchFilters.add(s);
						}
					}
					parameter[0] = searchFilters;
					return invocation.proceed();
				} else {
					return invocation.proceed();
				}
			} else if (manager.getCounty() != null) {
				if (manager.getCounty() != null) {
					Object[] parameter = invocation.getArguments();
					if (parameter[0].getClass().equals(List.class)) {
						List<SearchFilter> searchFilters = (List<SearchFilter>) parameter[0];
						if (searchFilters == null) {
							searchFilters = new ArrayList<SearchFilter>();
							SearchFilter s = new SearchFilter(
									"area.parentArea", manager.getCounty());
							searchFilters.add(s);
						} else {
							boolean flag = false;
							for (SearchFilter s : searchFilters) {
								if (s.fieldName.equals("area.parentArea.id")) {
									Area area = areaService.getAreaById(Long
											.parseLong(s.value.toString()));
									switch (area.getLevel()) {
									case Province:
									case City:
									case County:
										searchFilters.remove(s);
										SearchFilter s_ = new SearchFilter(
												"area.parentArea",
												manager.getCounty());
										searchFilters.add(s_);
										break;
									default:
										break;
									}
									flag = true;
									break;
								}
							}
							if (!flag) {
								SearchFilter s = new SearchFilter(
										"area.parentArea", manager.getCounty());
								searchFilters.add(s);
							}
						}
						parameter[0] = searchFilters;
						return invocation.proceed();

					} else {
						return invocation.proceed();
					}
				}
			} else {
				return invocation.proceed();
			}
		} catch (Throwable e) {
			throw e;
		}
		return invocation.proceed();
	}

	private Object doFilterAisle(MethodInvocation invocation) throws Throwable {
		return baseFilte("machineryEquipment.area.parentArea",
				"machineryEquipment.area.parentArea.parentArea", invocation);
	}

	private Object doFilterMachineryEquipment(MethodInvocation invocation)
			throws Throwable {
		return baseFilte("area.parentArea", "area.parentArea.parentArea",
				invocation);
	}

	private Object doFilterRecord(MethodInvocation invocation) throws Throwable {
		return baseFilte("machineryEquipment.area.parentArea",
				"machineryEquipment.area.parentArea.parentArea", invocation);
	}

	@SuppressWarnings("unchecked")
	private Object baseFilte(String str1, String str2,
			MethodInvocation invocation) throws Throwable {
		Manager manager = SecurityContext.getCurrentManager();
		if (manager == null) {
			return invocation.proceed();
		}
		try {
			if (manager.getTownshipStreet() != null) {
				Object[] parameter = invocation.getArguments();
				if (parameter[0]==null || List.class.isAssignableFrom(parameter[0].getClass())) {
					List<SearchFilter> searchFilters = (List<SearchFilter>) parameter[0];
					if (searchFilters == null) {
						searchFilters = new ArrayList<SearchFilter>();
					}
					SearchFilter s = new SearchFilter(str1,
							manager.getTownshipStreet());
					searchFilters.add(s);
					parameter[0] = searchFilters;
					return invocation.proceed();
				} else {
					return invocation.proceed();
				}
			} else if (manager.getCounty() != null) {
				if (manager.getCounty() != null) {
					Object[] parameter = invocation.getArguments();
					if (parameter[0]==null || List.class.isAssignableFrom(parameter[0].getClass())) {
						List<SearchFilter> searchFilters = (List<SearchFilter>) parameter[0];
						if (searchFilters == null) {
							searchFilters = new ArrayList<SearchFilter>();
						}
						SearchFilter s = new SearchFilter(str2,
								manager.getCounty());
						searchFilters.add(s);
						parameter[0] = searchFilters;
						return invocation.proceed();
					} else {
						return invocation.proceed();
					}
				}
			} else {
				return invocation.proceed();
			}
		} catch (Throwable e) {
			throw e;
		}
		return invocation.proceed();
	}

}
