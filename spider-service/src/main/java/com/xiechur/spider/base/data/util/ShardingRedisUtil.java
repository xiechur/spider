package com.xiechur.spider.base.data.util;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.xiechur.spider.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * redis 分片工具，使用构造函数既可以
 *
 */
public class ShardingRedisUtil {
	
	private Logger logger= LoggerFactory.getLogger(ShardingRedisUtil.class);

	@SuppressWarnings("rawtypes")
	public ShardingRedisUtil(RedisTemplate... shard) {
		this.initialize(shard);
	}

	@SuppressWarnings("rawtypes")
	public RedisTemplate getShardingRedis(String redisKey) {
		ShardingRedisInfo info = getShardingRedisInfo(redisKey);
		// 如果无分片信息默认返回第一个redis
		if (info == null) {
			return redisTmptList == null ? null : redisTmptList.get(0).redisTmpt;
		} else {
			return info.redisTmpt;
		}
	}

	public ShardingRedisInfo getShardingRedisInfo(String redisKey) {
		if (nodes == null) {
			throw new CustomException("redis 分片节点为空===");
		}
		long keyHashLong = algo.hashInt(redisKey.hashCode()).asInt();
		SortedMap<Long, ShardingRedisInfo> tail = nodes.tailMap(keyHashLong);
		if (tail.isEmpty()) {
			return nodes.get(nodes.firstKey());
		} else {
			return tail.get(tail.firstKey());
		}
	}

	/**
	 * 进行初始化
	 * 
	 * @param shard
	 */
	@SuppressWarnings("rawtypes")
	public void initialize(RedisTemplate... shard) {
		if (shard == null || shard.length <= 0)
			return;

		List<ShardingRedisInfo> redisInfoList = new ArrayList<>();
		for (int i = 0; i < shard.length; i++) {
			ShardingRedisInfo one = new ShardingRedisInfo(shard[i], getShardName(i));
			redisInfoList.add(one);
		}

		nodes = new TreeMap<Long, ShardingRedisInfo>();
		redisTmptList = redisInfoList;
		for (int i = 0; i != redisInfoList.size(); ++i) {
			final ShardingRedisInfo shardInfo = redisInfoList.get(i);
			for (int n = 0; n < 160; n++) {
//				nodes.put(algo.hash(shardInfo.name + "-NODE-" + n), shardInfo);
				String hashKey = shardInfo.name + "-NODE-" + n;
				long hashLong = algo.hashInt(hashKey.hashCode()).asInt();
				logger.info("redis info === " + hashKey + "; hash value === " + hashLong);
				nodes.put(hashLong, shardInfo);
			}
		}
	}

	/**
	 * 获取分片的名字
	 * 
	 * @param i
	 * @return
	 */
	private String getShardName(int i) {
		i = i++;
		return String.format(shardName, i);
	}

	private final static String shardName = "SHARD-%d";
	private volatile TreeMap<Long, ShardingRedisInfo> nodes;
	private final static HashFunction algo = Hashing.murmur3_32();
	private List<ShardingRedisInfo> redisTmptList;

	@SuppressWarnings("rawtypes")
	public class ShardingRedisInfo {
		public RedisTemplate redisTmpt;
		public String name;

		public ShardingRedisInfo(RedisTemplate redisTmpt, String name) {
			super();
			this.redisTmpt = redisTmpt;
			this.name = name;
		}
	}

}
