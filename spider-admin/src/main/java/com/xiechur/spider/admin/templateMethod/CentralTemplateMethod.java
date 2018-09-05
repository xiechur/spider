package com.xiechur.spider.admin.templateMethod;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 全局唯一一个自定义模板方法 ，将具体的处理逻辑交由methodhandler 调用其他处理器 自定义模板方法调用,第一个参数必须是处理器的别名
 * 
 *
 */
@Component(value = "centralTemplateMethod")
public class CentralTemplateMethod implements TemplateMethodModelEx {
	@Autowired
	private MethodHandlerRegister register;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments == null || arguments.size() == 0) {
			return null;
		}
		List copyList = new ArrayList<>();
		for (int i = 0; i < arguments.size(); i++) {
			// 非第一个元素加入copylist 作为参数传递到解析器
			if (i != 0) {
				copyList.add(arguments.get(i));
			}
		}
		String handlerName = ((SimpleScalar) arguments.get(0)).getAsString();
		return register.process(handlerName, copyList);
	}

}
