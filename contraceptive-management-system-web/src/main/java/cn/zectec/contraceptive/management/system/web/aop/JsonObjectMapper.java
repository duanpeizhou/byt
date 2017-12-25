package cn.zectec.contraceptive.management.system.web.aop;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class JsonObjectMapper extends ObjectMapper {
	public JsonObjectMapper() {
		this.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		this.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE,false);
	}

}