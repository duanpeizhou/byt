
package cn.zectec.contraceptive.management.system.repository.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 根据字段生成查询条件
 *
 */
public class SearchFilter {
	public enum BooleanOperator{
		/**或*/
		OR,
		/**并且*/
		AND
	}
	public enum Operator {
		EQ, LIKE, GT, LT, GTE, LTE,NEQ
	}
	/**
	 * fieldName 搜索的字段名称  name category.id等
	 */
	public String fieldName;
	/**
	 * operator = like > < >= <= <>
	 * 默认是相等
	 */
	public Operator operator = Operator.EQ;
	/**
	 * value 值
	 */
	public Object value;
	/**和条件关系 默认是and*/
	public BooleanOperator booleanOperator = BooleanOperator.AND;


	/**
	 * 
	 * @param fieldName 搜索的字段名称  name category.id等
	 * 
	 * @param operator = like > < >= <= <>
	 * @param value 值
	 */
	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}
	public SearchFilter(String fieldName, Operator operator, Object value,BooleanOperator booleanOperator) {
		this(fieldName,operator,value);
		this.booleanOperator = booleanOperator;
	}
	
	public SearchFilter(String fieldName, Object value) {
		this.fieldName = fieldName;
		this.value = value;
	}
	
	
	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SearchFilter [fieldName=");
		builder.append(fieldName);
		builder.append(", value=");
		builder.append(value);
		builder.append(", operator=");
		builder.append(operator);
		builder.append("]");
		return builder.toString();
	}



	/**
	 * searchParams中key的格式为operator_fieldname  EQ_name 
	 */
	public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = new HashMap<String, SearchFilter>();

		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();
			if ("".equals(value.toString().trim())) {
				continue;
			}

			// 拆分operator与filedAttribute
			String[] names = key.split("_");
			if (names.length != 2) {
				throw new IllegalArgumentException(key + " is not a valid search filter name");
			}
			String filedName = names[1];
			Operator operator = Operator.valueOf(names[0]);

			// 创建searchFilter
			SearchFilter filter = new SearchFilter(filedName, operator, value);
			filters.put(key, filter);
		}

		return filters;
	}
}