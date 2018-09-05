package com.xiechur.spider.base.data.datasource;

import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataSourceDefaultPropertiesUtil {

    final static Map<String, Object> tomcatDefault;
    final static Map<String, Object> C3p0Default;
    final static Map<String, Object> DruidDefault;

    static {

        {
            // more configuration information you can see class
            // org.apache.tomcat.jdbc.pool.PoolConfiguration.
            LinkedHashMap<String, Object> temp = new LinkedHashMap<String, Object>();
            temp.put("maxAge", 3600000);
//            temp.put("maxIdle", 20);
            temp.put("maxActive", 80);
            tomcatDefault = Collections.unmodifiableMap(temp);
        }

        {
            // more configuration information you can open this website
            // http://www.mchange.com/projects/c3p0/#basic_pool_configuration
            LinkedHashMap<String, Object> temp = new LinkedHashMap<String, Object>();
            temp.put("maxPoolSize", 80);
            temp.put("minPoolSize", 10);
            temp.put("maxIdleTime", 3600);
            temp.put("initialPoolSize", 30);
            temp.put("idleConnectionTestPeriod", 30);
            temp.put("acquireIncrement", 5);
            C3p0Default = Collections.unmodifiableMap(temp);
        }

        {
            // more configuration information you can open this website
            // https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_DruidDataSource%E5%8F%82%E8%80%83%E9%85%8D%E7%BD%AE
            LinkedHashMap<String, Object> temp = new LinkedHashMap<String, Object>();
            temp.put("minEvictableIdleTimeMillis", 300000);
//            temp.put("maxIdle", 20);
            temp.put("maxActive", 80);
            DruidDefault = Collections.unmodifiableMap(temp);
        }
    }

    public static void addDefaultValue(String dataSourceType, Map<String, Object> configProperties) {
        if (StringUtils.isEmpty(dataSourceType)) {
            dataSourceType = MysqlDataSourceLoader.defaultDataSource;
        }

        if (dataSourceType.equals("com.mchange.v2.c3p0.ComboPooledDataSource")) {
            for (String key : C3p0Default.keySet()) {
                if (!configProperties.containsKey(key)) {
                    configProperties.put(key, C3p0Default.get(key));
                }
                if (configProperties.containsKey("username")) {
                    configProperties.put("user", configProperties.get("username"));
                    configProperties.remove("username");
                }

                if (configProperties.containsKey("url")) {
                    configProperties.put("jdbcUrl", configProperties.get("url"));
                    configProperties.remove("url");
                }
            }
            configProperties.put("driverClass", "org.gjt.mm.mysql.Driver");
        }

        if (dataSourceType.equals("org.apache.tomcat.jdbc.pool.DataSource")) {
            for (String key : tomcatDefault.keySet()) {
                if (!configProperties.containsKey(key)) {
                    configProperties.put(key, tomcatDefault.get(key));
                }
            }
            configProperties.put("driverClassName", "com.mysql.jdbc.Driver");
        }

        if (dataSourceType.equals("com.alibaba.druid.pool.DruidDataSource")) {
            for (String key : DruidDefault.keySet()) {
                if (!configProperties.containsKey(key)) {
                    configProperties.put(key, DruidDefault.get(key));
                }
            }
            configProperties.put("driverClassName", "com.mysql.jdbc.Driver");
        }

    }

}
