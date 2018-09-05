package com.xiechur.spider.base.data.datasource.jdbc.builder;

import com.xiechur.spider.base.data.datasource.jdbc.StatementParameter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class AbstractSqlBuilder implements SqlBuilder {
	protected StatementParameter statementParameter = new StatementParameter(true);
	protected List<String> fieldList = new ArrayList<String>();

	/**
	 * 设置String类型参数.
	 * 
	 * @param fieldName
	 *            参数名
	 * @param value
	 *            参数值
	 */
	public void setString(String fieldName, String value) {
		fieldList.add(fieldName);
		statementParameter.setString(value);
	}

	/**
	 * 设置Timestamp类型参数.
	 * 
	 * @param fieldName
	 *            参数名
	 * @param value
	 *            参数值
	 */
	public void setTimestamp(String fieldName, Timestamp value) {
		fieldList.add(fieldName);
		statementParameter.setTimestamp(value);
	}

	/**
	 * 设置boolean类型参数.
	 * 
	 * @param fieldName
	 *            参数名
	 * @param value
	 *            参数值
	 */
	public void setBool(String fieldName, Boolean value) {
		fieldList.add(fieldName);
		statementParameter.setBool(value);
	}

	/**
	 * 设置int类型参数.
	 * 
	 * @param fieldName
	 *            参数名
	 * @param value
	 *            参数值
	 */
	public void setInt(String fieldName, Integer value) {
		fieldList.add(fieldName);
		statementParameter.setInt(value);
	}

	/**
	 * 设置Date类型参数.
	 * 
	 * @param fieldName
	 *            参数名
	 * @param value
	 *            参数值
	 */
	public void setDate(String fieldName, Date value) {
		fieldList.add(fieldName);
		statementParameter.setDate(value);
	}

	/**
	 * 设置long类型参数.
	 * 
	 * @param fieldName
	 *            参数名
	 * @param value
	 *            参数值
	 */
	public void setLong(String fieldName, Long value) {
		fieldList.add(fieldName);
		statementParameter.setLong(value);
	}

	/**
	 * 设置float类型参数.
	 * 
	 * @param fieldName
	 *            参数名
	 * @param value
	 *            参数值
	 */
	public void setFloat(String fieldName, Float value) {
		fieldList.add(fieldName);
		statementParameter.setFloat(value);
	}

	/**
	 * 设置double类型参数.
	 * 
	 * @param fieldName
	 *            参数名
	 * @param value
	 *            参数值
	 */
	public void setDouble(String fieldName, Double value) {
		fieldList.add(fieldName);
		statementParameter.setDouble(value);
	}

	@Override
	/**
	 * @see com.duowan.leopard.data.jdbc.builder.SqlBuilder#getParam()
	 */
	public StatementParameter getParam() {
		return statementParameter;
	}

}
