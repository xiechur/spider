package com.xiechur.spider.base.data.datasource;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;

public class RedisDefaultDataSource implements InitializingBean, DisposableBean, RedisConnectionFactory {

	@Override
	public DataAccessException translateExceptionIfPossible(RuntimeException arg0) {
		throw arg0;
	}

	@Override
	public RedisConnection getConnection() {
		throw new RuntimeException("UnImplementRedisDataSource");
	}

	@Override
	public RedisClusterConnection getClusterConnection() {
		throw new RuntimeException("UnImplementRedisDataSource");
	}

	@Override
	public boolean getConvertPipelineAndTxResults() {
		throw new RuntimeException("UnImplementRedisDataSource");
	}

	@Override
	public RedisSentinelConnection getSentinelConnection() {
		throw new RuntimeException("UnImplementRedisDataSource");
	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
	}

}
