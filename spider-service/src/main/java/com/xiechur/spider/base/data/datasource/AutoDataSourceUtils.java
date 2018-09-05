package com.xiechur.spider.base.data.datasource;

import org.springframework.util.StringUtils;

public class AutoDataSourceUtils {

	public static String evalString(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	public static String getWhichDataSourceType(String key) {
		if (key.startsWith("mysql.")) {
			return "mysql";
		} else if (key.startsWith("redis.")) {
			return "redis";
		}
		return "";
	}

	public static String getDataSourceRootNode(String str, String keyword) {
		str = str.replaceFirst(keyword + "\\.", "");
		int index = str.indexOf(".");
		if (index > 1) {
			String node = StringUtils.trimAllWhitespace(str.substring(0, index));
			if (!StringUtils.isEmpty(node)) {
				return node;
			}
		}
		return null;
	}

//	public static String getRiseEnviromentProperty(String key, boolean required) {
//		String result = System.getProperty(key, System.getenv(key));
//		if (StringUtils.isEmpty(result) && required) {
//			throw new RuntimeException(String.format("[RISEDEV]升龙数据源初始失败,没有环境变量：%s", key));
//		}
//		return result;
//	}

//	public static String[] getRiseMysqlProperty(String nodeName, boolean slave) {
//		String hostKey = nodeName + (slave ? "_slave_host" : "_host");
//		String portKey = null;
//		String userKey = null;
//		String passKey = null;
//		String dbKey = null;
//
//		String host = getRiseEnviromentProperty(hostKey, false);
//		String port = null;
//		String user = null;
//		String pass = null;
//		String db = null;
//
//		if (!StringUtils.isEmpty(host)) {
//			portKey = nodeName + "_port";
//			userKey = nodeName + "_user";
//			passKey = nodeName + "_password";
//			dbKey = nodeName + "_default_db";
//
//			port = getRiseEnviromentProperty(portKey, true);
//			user = getRiseEnviromentProperty(userKey, true);
//			pass = getRiseEnviromentProperty(passKey, true);
//			db = getRiseEnviromentProperty(dbKey, true);
//		} else {
//			throw new RuntimeException("没有发现升龙数据源");
//		}
//		return new String[] { host, port, user, pass, db };
//	}

//	static String[] getRiseRedisProperty(String nodeName) {
//		String hostKey = nodeName + "_host";
//		String portKey = null;
//		String passKey = null;
//
//		String host = getRiseEnviromentProperty(hostKey, false);
//		String port = null;
//		String pass = null;
//
//		if (!StringUtils.isEmpty(host)) {
//			portKey = nodeName + "_port";
//			passKey = nodeName + "_password";
//
//			port = getRiseEnviromentProperty(portKey, true);
//			pass = getRiseEnviromentProperty(passKey, false);
//		}
//		return new String[] { host, port, pass };
//	}

}
