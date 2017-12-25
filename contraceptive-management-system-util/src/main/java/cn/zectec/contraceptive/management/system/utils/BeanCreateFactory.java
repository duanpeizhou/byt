package cn.zectec.contraceptive.management.system.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

public class BeanCreateFactory {
	private Map<String, Class<?>> propertyMap = new HashMap<String, Class<?>>();
	private Map<String, Object> valueMap = new HashMap<String, Object>();

	public BeanCreateFactory()
	{
	}

	public void addProperty(String property, Class<?> propertyType)
	{
		propertyMap.put(property, propertyType);
	}

	public void addProperties(Map<String, Class<?>> properties)
	{
		propertyMap.putAll(properties);
	}

	public void setValue(String property, Object value)
	{
		valueMap.put(property, value);
	}

	public void setValues(Map<String, Object> values)
	{
		valueMap.putAll(values);
	}

	public Object createObject()
	{
		BeanGenerator generator = new BeanGenerator();
		generator.setSuperclass(CglibRootBean.class);
		Iterator<String> itor = this.propertyMap.keySet().iterator();
		while (itor.hasNext())
		{
			String propertyKey = itor.next();
			generator.addProperty(propertyKey,
					this.propertyMap.get(propertyKey));
		}

		Object obj = generator.create();

		BeanMap beanMap = BeanMap.create(obj);

		Iterator<String> valueItor = this.valueMap.keySet().iterator();
		while (valueItor.hasNext())
		{
			String valueKey = valueItor.next();
			beanMap.put(valueKey, this.valueMap.get(valueKey));
		}

		return obj;
	}

}
class CglibRootBean implements Serializable{

}
