package com.xiechur.spider.base.data.datasource;

import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

public class MysqlDataSourceLoader {

//    public final static String defaultDataSource = "org.apache.tomcat.jdbc.pool.DataSource";
    public final static String defaultDataSource = "com.alibaba.druid.pool.DruidDataSource";

    /**
     * <p>
     * support datasource list
     * </p>
     * org.apache.tomcat.jdbc.pool.DataSource <br>
     * com.zaxxer.hikari.HikariDataSource<br>
     * org.apache.commons.dbcp.BasicDataSource <br>
     * org.apache.commons.dbcp2.BasicDataSource <br>
     * com.mchange.v2.c3p0.ComboPooledDataSource <br>
     * com.alibaba.druid.pool.DruidDataSource <br>
     */

    @SuppressWarnings("unchecked")
    public Class<? extends DataSource> loadDataSource(String dataSourceType, ClassLoader classLoader) {
        if (StringUtils.isEmpty(dataSourceType)) {
            dataSourceType = MysqlDataSourceLoader.defaultDataSource;
        }
        try {
            Class<? extends DataSource> datasource = (Class<? extends DataSource>) ClassUtils
                    .forName(dataSourceType, classLoader);
            return datasource;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Error create datasource!");
        }
    }

}
