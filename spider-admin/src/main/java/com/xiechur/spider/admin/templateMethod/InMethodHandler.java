package com.xiechur.spider.admin.templateMethod;

import java.util.List;

/**
 * 解决模板方法的接口 只要实现该类就可以增加freemarker 模板方法了
 *
 */
public interface InMethodHandler {

	/**
	 * 处理器的名字 唯一标志 作为模板中${central(name,arg1,arg2,arg...)}第一个参数传入
	 * @return
	 */
	String getName();

	/**
	 * 具体处理逻辑
	 * @param copyList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Object handle(List copyList);

}
