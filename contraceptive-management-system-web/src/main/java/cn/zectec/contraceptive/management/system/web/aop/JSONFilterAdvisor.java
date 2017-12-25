package cn.zectec.contraceptive.management.system.web.aop;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;

import cn.zectec.contraceptive.management.system.utils.BeanCreateFactory;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilter;
import cn.zectec.contraceptive.management.system.web.annotation.JsonFilters;
import cn.zectec.contraceptive.management.system.web.aop.JsonObjectMapper;
import cn.zectec.contraceptive.management.system.web.aop.JsonView;

public class JSONFilterAdvisor implements MethodInterceptor {

	private Logger logger = Logger.getLogger(JSONFilterAdvisor.class);

	public enum JsonFilterType {
		NULL, Empty_Array
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Method method = invocation.getMethod();
		Object target = invocation.proceed();
		// 把所有的JSONFilter放到List中
		List<JsonFilter> filter = new ArrayList<JsonFilter>();
		if (null != method.getAnnotation(JsonFilter.class))
			filter.add(method.getAnnotation(JsonFilter.class));
		if (null != method.getAnnotation(JsonFilters.class)) {
			JsonFilters jfss = method.getAnnotation(JsonFilters.class);
			JsonFilter[] jfs = jfss.value();
			for (JsonFilter jf : jfs)
				filter.add(jf);
		}

		// 如果List为空，返回
		if (filter.isEmpty())
			return target;

		ModelAndView mav = new ModelAndView(new JsonView());
		if (null == target) {
			mav.addObject(JsonFilterType.NULL);
			return mav;
		}
		if (ModelAndView.class.isAssignableFrom(target.getClass()))
			return target;

		Object result = null;
		if (isCollection(target)) {
			Collection<?> c = (Collection<?>) target;
			if (c.size() == 0)
				result = JsonFilterType.Empty_Array;
			else
				result = jsonFilter(target, filter);
		} else {
			result = jsonFilter(target, filter);
		}

		logger.info(method + "被Json过滤器过滤到了");
		logger.info("过滤后的jackson数据为："
				+ new JsonObjectMapper().writeValueAsString(result));

		mav.addObject(result);
		return mav;
	}

	private Object jsonFilter(Object target, List<JsonFilter> filter) {
		if (null != target) {
			if (isCollection(target)) {
				return handleCollection(target, filter);
			} else if (isArray(target)) {
				return handleArray(target, filter);
			} else if (isMap(target)) {
				return handleMap(target, filter);
			} else if (isDate(target)) {
				return handleDate(target, filter);
			} else if (isSimpleType(target)) {
				return handleSimpleType(target, filter);
			} else if (isEnumType(target)) {
				return target;
			} else {
				return handleUserDefinedType(target, filter);
			}
		}

		return null;
	}

	private boolean isEnumType(Object target) {
		return target instanceof Enum;
	}

	private Object handleDate(Object target, List<JsonFilter> filter) {
		if (null != target) {
			Date d = (Date) target;
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			return format.format(d);
		}

		return null;
	}

	private boolean isDate(Object target) {
		return Date.class.isAssignableFrom(target.getClass());
	}

	@SuppressWarnings("unchecked")
	private Object handleMap(Object target, List<JsonFilter> filter) {
		if (null != target) {
			Map<String, Object> map = new HashMap<String, Object>();
			Map<Object, Object> m = (Map<Object, Object>) target;
			Iterator<Entry<Object, Object>> itor = m.entrySet().iterator();
			while (itor.hasNext()) {
				Entry<Object, Object> entry = itor.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				if (isSimpleType(key)) {
					map.put(key.toString(), jsonFilter(value, filter));
				} else {
					map.put(key.getClass().getSimpleName(),
							jsonFilter(value, filter));
				}
			}
			return map;
		}

		return null;
	}

	private boolean isMap(Object target) {
		return Map.class.isAssignableFrom(target.getClass());
	}

	private Object handleSimpleType(Object target, List<JsonFilter> filter) {
		return target;
	}

	private Object handleUserDefinedType(Object target, List<JsonFilter> filter) {
		if (null != target) {
			try {
				boolean isTargetClass = false;
				for (JsonFilter jf : filter) {
					// com.zectec.medicaltest.model.UploadFile_$$_javassist_17
					// 如果该对象是注解要处理的对象
					if (target.getClass() == jf.pojo()
							|| (target.getClass().toString()
									.contains(jf.pojo().getSimpleName()) && target
									.getClass().toString()
									.contains("javassist"))) {
						isTargetClass = true;

						BeanCreateFactory factory = new BeanCreateFactory();
						// 处理允许的属性
						if (jf.allow().length > 0) {
							for (String pro : jf.allow()) {
								String property = pro;
								if (property.startsWith("is")) {
									property = property.substring(2);
								}
								PropertyDescriptor pd = new PropertyDescriptor(
										property, target.getClass());
								factory.addProperty(property, Object.class);
								factory.setValue(
										property,
										jsonFilter(
												pd.getReadMethod().invoke(
														target), filter));
							}
						} else if (jf.ignore().length > 0) {
							PropertyDescriptor[] descriptors = Introspector
									.getBeanInfo(target.getClass())
									.getPropertyDescriptors();
							for (PropertyDescriptor pd : descriptors) {
								boolean isIgnore = false;
								for (String ignore : jf.ignore()) {
									if (pd.getName().equals("class")
											|| pd.getName().equals(ignore)) {
										isIgnore = true;
										break;
									}
								}

								if (!isIgnore) {
									factory.addProperty(pd.getName(),
											Object.class);
									factory.setValue(
											pd.getName(),
											jsonFilter(pd.getReadMethod()
													.invoke(target), filter));
								}
							}
						} else {
							return target;
						}
						return factory.createObject();
					}
				}

				// 说明该类不是要处理的类，则判断其属性是不是要处理的类
				if (!isTargetClass) {
					BeanCreateFactory factory = new BeanCreateFactory();
					PropertyDescriptor[] descriptors = Introspector
							.getBeanInfo(target.getClass())
							.getPropertyDescriptors();
					for (PropertyDescriptor pd : descriptors) {
						if (!"class".equals(pd.getName())) {
							factory.addProperty(pd.getName(), Object.class);
							factory.setValue(
									pd.getName(),
									jsonFilter(pd.getReadMethod()
											.invoke(target), filter));
						}
					}
					return factory.createObject();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return target;
		}

		return null;
	}

	private boolean isSimpleType(Object target) {
		return BeanUtils.isSimpleValueType(target.getClass());
	}

	private Object handleArray(Object target, List<JsonFilter> filter) {
		if (null != target) {
			Collection<Object> c = new ArrayList<Object>();
			for (Object obj : (Object[]) target) {
				c.add(jsonFilter(obj, filter));
			}

			return c;
		}

		return null;
	}

	private boolean isArray(Object target) {
		return target.getClass().isArray();
	}

	@SuppressWarnings("unchecked")
	private Object handleCollection(Object target, List<JsonFilter> filter) {
		if (null != target) {
			Collection<Object> c = new ArrayList<Object>();
			for (Object obj : ((Collection<Object>) target)) {
				c.add(jsonFilter(obj, filter));
			}

			return c;
		}

		return null;
	}

	private boolean isCollection(Object target) {
		return Collection.class.isAssignableFrom(target.getClass());
	}
}
