package com.xiechur.spider.base.data.datasource.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * <pre>
 * Title:动态查询工具类，用于拼接SQL、填充pst
 * Description: 动态查询工具类，用于拼接SQL、填充pst
 * </pre>
 * 
 * @author wolfdog
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 * 修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class DynamicQuery {
	
	private static Logger logger = LoggerFactory.getLogger(DynamicQuery.class);
	private String templet = " AND %s %s ? ";
	private String baseSql;
	private ArrayList<Parameter> parameters = new ArrayList<Parameter>();

	public DynamicQuery() {

	}

	/**
	 * 要求baseSql带有where条件
	 * 
	 * @param baseSql
	 */
	public void setBaseSql(String baseSql) {
		this.baseSql = baseSql;
	}

	public void addParameter(Parameter parameter, StatementParameter param) {
		Object value = parameter.getValue();
		try {
			if (value != null) {
				parameters.add(parameter);
				param.setObject(parameter.getValue().getClass(),
						parameter.getValue());
			}
		} catch (Exception e) {
			logger.error("addParameter error: ", e);
		}

	}

	public String generateSql() {
		StringBuffer buffer = new StringBuffer(baseSql);
		for (Parameter p : parameters) {
			buffer.append(String.format(templet, p.getField(), p.getOperator()));
		}
		logger.debug(buffer.toString());
		return buffer.toString();
	}

	public void fillPreparedStatement(PreparedStatement pst)
			throws SQLException {
		int count = 1;
		for (Parameter p : parameters) {
			pst.setObject(count, p.getValue());
			count++;
		}
	}

}
