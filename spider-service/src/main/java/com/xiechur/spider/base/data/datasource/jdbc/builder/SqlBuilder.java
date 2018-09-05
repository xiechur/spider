package com.xiechur.spider.base.data.datasource.jdbc.builder;

import com.xiechur.spider.base.data.datasource.jdbc.StatementParameter;

public interface SqlBuilder {

	/**
	 * 获取SQL语句.
	 * @return
	 */
	String getSql();

	/**
	 * 获取参数
	 * @return
	 */
	StatementParameter getParam();
}
