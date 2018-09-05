package com.xiechur.spider.base.data.util;

import com.xiechur.spider.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具
 *
 */
public class RedisUtil {
	
	private static Logger logger= LoggerFactory.getLogger(RedisUtil.class);
	
	public final static String REDIS_EVAL_NULL_VALUE = "-1";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isRedisDefaultValue(String redisKey, RedisTemplate redis){
		if(redis.opsForZSet().size(redisKey).intValue() == 1 ){
			Set<String> set = redis.opsForZSet().range(redisKey, 0, 1);
			return (set != null && !set.isEmpty() && set.iterator().next().equals(REDIS_EVAL_NULL_VALUE));
		}
		return false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void checkAndDelRedisDefaultValue(String redisKey, RedisTemplate redis){
		if(isRedisDefaultValue(redisKey, redis)){
			redis.opsForZSet().remove(redisKey, REDIS_EVAL_NULL_VALUE);
		}
	}
	
	// 数据带缓冲的缓存时间
	private static int API_REDIS_DATA_TIME = 60 * 5;
	// 数据进入缓冲的时间，即缓存数据过了4分钟后，就需要重新加载数据
	private static int API_REDIS_BUFFER_TIME = 60 * 4;
	// 加锁的情况下，设置锁值的KEY
	private static String API_REDIS_DATA_LOCK_KEY = "%s_lock";
	private static String API_REDIS_DATA_BUFFER_KEY = "%s_buffer";
	private static String API_REDIS_BUFFER_LOG_PATTERN = "API接口惊群现象日志：%s";
	
	/**
	 * API数据缓存5分钟，但当进入第4分钟，就需要加锁，然后发起一个请求，获取新数据.
	 * @param key
	 * @param object
	 */
	protected static void putRedis(RedisTemplate<String, String> redis, String key, String object) {
		putRedis(redis, key, object, API_REDIS_DATA_TIME, API_REDIS_BUFFER_TIME);
	}
	
	protected static void putRedis(RedisTemplate<String, String> redis, String key, String object, Integer minutes) {
		int redisDataTime = 60 * minutes;
		int redisBufferTime = 60 * (minutes - 1);
		putRedis(redis, key, object, redisDataTime, redisBufferTime);
	}
	
	private static void putRedis(RedisTemplate<String, String> appDataRedis, String key, String object, int redisDataTime, int redisBufferTime) {
		String bufferKey = String.format(API_REDIS_DATA_BUFFER_KEY, key);
		String lockKey = String.format(API_REDIS_DATA_LOCK_KEY, key);
		long curTime = DateUtil.getTime().getTime();
		long bufferTime = curTime + redisBufferTime * 1000;
		if (!appDataRedis.hasKey(key)) {
			// 原本就不存在缓存
			appDataRedis.opsForValue().set(key, object, redisDataTime, TimeUnit.SECONDS);
		} else {
			// 已存在缓存
		}
		// 设置缓存值时，把锁去掉
		if (appDataRedis.hasKey(lockKey)) {
			appDataRedis.delete(lockKey);
		}
		// 重新设置当前时间后面的4分钟为缓冲值
		appDataRedis.opsForValue().set(bufferKey, String.valueOf(bufferTime), redisDataTime, TimeUnit.SECONDS);
		// 重新设置5分钟后失效
		appDataRedis.opsForValue().set(key, object, redisDataTime, TimeUnit.SECONDS);
	}

	protected static String getRedis(RedisTemplate<String, String> appDataRedis, String key) {
		if (appDataRedis.hasKey(key)) {
			String bufferKey = String.format(API_REDIS_DATA_BUFFER_KEY, key);
			String lockKey = String.format(API_REDIS_DATA_LOCK_KEY, key);
			// 如果存在缓存值，还要判断是否达到缓冲时间点，如果达到缓冲时间点，则进行加锁，并返回null
			// 后面的请求进来，在缓冲开始时间到真正失效时间之间，判断是否存在锁，存在，则直接返回缓存值，不存在，则取锁
			if (appDataRedis.hasKey(bufferKey)) {
				String bufferValue = appDataRedis.opsForValue().get(bufferKey);
				if (StringUtils.isNotBlank(bufferValue)) {
//					logger.info(String.format(API_REDIS_BUFFER_LOG_PATTERN, "get " + key + "-->存在缓冲的KEY： " + bufferKey + ", value:" + bufferValue));
					long bufferTime = Long.parseLong(bufferValue);
					long currTime = DateUtil.getTime().getTime();
					if (currTime > bufferTime) {
						// 进入缓冲时间点
//						logger.info(String.format(API_REDIS_BUFFER_LOG_PATTERN, "get " + key + "-->进入缓冲时间点"));
						if (!appDataRedis.hasKey(lockKey)) {
							// 如果之前没有请求加锁，则加锁，并且返回null，让取缓存值的请求从DB中查，去重新加载缓存值
							appDataRedis.opsForValue().increment(lockKey, 1);
							logger.info(String.format(API_REDIS_BUFFER_LOG_PATTERN, "get " + key + "-->进入缓冲时间点，并且获取锁"));
							return null;
						} else {
							logger.info(String.format(API_REDIS_BUFFER_LOG_PATTERN, "get " + key + "-->进入缓冲时间点，但锁已经被其他请求获取了"));
						}
					}
				}
			}
			return appDataRedis.opsForValue().get(key);
		} else {
			return null;
		}
	}

}
