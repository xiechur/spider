package com.xiechur.spider.admin.templateMethod;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MethodHandlerRegister {
	
	public static Map<String, InMethodHandler> container = new HashMap<>();
	
	/**
	 * 注册模板方法解析器
	 * @param handler
	 * @return
	 */
	public void regist(InMethodHandler handler){
		container.put(handler.getName(), handler);
	}

	@SuppressWarnings("rawtypes")
	public Object process(String handlerName, List copyList) {
		return container.get(handlerName).handle(copyList);
	}
	
}
