package com.xiechur.spider.base.data.datasource;

import org.springframework.util.ClassUtils;

import javax.sql.DataSource;

public class RedisDataSourceLoader {

	@SuppressWarnings("unchecked")
	public Class<? extends DataSource> loadDataSource(ClassLoader classLoader) {
		try {
			Class<? extends DataSource> datasource = (Class<? extends DataSource>) ClassUtils
					.forName("org.springframework.data.redis.connection.jedis.JedisConnectionFactory", classLoader);
			return datasource;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Error create datasource!");
		}
	}
}
