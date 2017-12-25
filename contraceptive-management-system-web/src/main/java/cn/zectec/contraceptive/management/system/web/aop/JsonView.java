package cn.zectec.contraceptive.management.system.web.aop;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.servlet.View;

import cn.zectec.contraceptive.management.system.web.aop.JSONFilterAdvisor.JsonFilterType;

public class JsonView implements View {
	private ObjectMapper objectMapper = new JsonObjectMapper();

	@Override
	public String getContentType() {
		return "application/json";
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Object target = null;

		Iterator<String> itor = model.keySet().iterator();
		while (itor.hasNext()) {
			String key = itor.next();
			if (!BeanPropertyBindingResult.class.isAssignableFrom(model
					.get(key).getClass()))
				target = model.get(key);
		}

		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		if (target == JsonFilterType.NULL)
			response.getWriter().write("null");
		else if (target == JsonFilterType.Empty_Array)
			response.getWriter().write("[]");
		else
			response.getWriter().write(objectMapper.writeValueAsString(target));
		response.getWriter().close();
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

}