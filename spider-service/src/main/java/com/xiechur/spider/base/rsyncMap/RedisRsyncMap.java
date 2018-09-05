package com.xiechur.spider.base.rsyncMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 复制leopard提供的 RysncMap，目的是修复bug
 * 
 * 改进：
 * 1）新增构造函数，允许传入channel名称
 * 
 * @author lhh
 *
 * @param <K>
 * @param <V>
 */
public class RedisRsyncMap<K, V> implements Map<K, V>, MemdbRsyncQueue {
	private final static String CHANNEL_PREFIX = "rsyncmap_queue:";
	protected Log logger = LogFactory.getLog(this.getClass());

	private final Map<K, V> m; // Backing Map

	protected MemdbRsyncService memdbRsyncService;

	private String channel = "rsyncmap_queue:default";

	public RedisRsyncMap(Map<K, V> m, final Jedis redis) {
		if (m == null) {
			throw new NullPointerException();
		}
		if (redis == null) {
			throw new NullPointerException("redis对象不能为空.");
		}

		this.m = m;

		memdbRsyncService = new MemdbRsyncServiceRedisImpl(redis, this.channel, this);
		memdbRsyncService.init();
	}
	
	public RedisRsyncMap(Map<K, V> m, final Jedis redis, String channel) {
		if (m == null) {
			throw new NullPointerException();
		}
		if (redis == null) {
			throw new NullPointerException("redis对象不能为空.");
		}
		
		if(channel!=null && channel.trim().length()>0){
			this.channel = CHANNEL_PREFIX + channel;
		}

		this.m = m;

		memdbRsyncService = new MemdbRsyncServiceRedisImpl(redis, this.channel, this);
		memdbRsyncService.init();
	}
	
	

	@Override
	public int size() {
		return m.size();

	}

	@Override
	public boolean isEmpty() {
		return m.isEmpty();

	}

	@Override
	public boolean containsKey(Object key) {
		return m.containsKey(key);

	}

	@Override
	public boolean containsValue(Object value) {
		return m.containsValue(value);

	}

	@Override
	public V get(Object key) {
		return m.get(key);

	}

	@Override
	public Set<K> keySet() {
		return m.keySet();
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return m.entrySet();
	}

	@Override
	public Collection<V> values() {
		return m.values();
	}

	@Override
	public boolean equals(Object o) {
		return m.equals(o);
	}

	@Override
	public int hashCode() {
		return m.hashCode();
	}

	@Override
	public String toString() {
		return m.toString();
	}

	// ////////////////////////////

	private static final String TYPE_PUT = "put";
	private static final String TYPE_REMOVE = "remove";
	private static final String TYPE_CLEAR = "clear";

	@Override
	public V put(K key, V value) {
		V bean = m.put(key, value);
		memdbRsyncService.add(TYPE_PUT, JsonSerializer.toJson(key), JsonSerializer.toJson(value), false);
//		memdbRsyncService.add(TYPE_PUT, JsonSerializer.toJson(key), null, false);	// for test
		return bean;
	}

	@Override
	public V remove(Object key) {
		V bean = m.remove(key);
		memdbRsyncService.add(TYPE_REMOVE, JsonSerializer.toJson(key), null, false);
		return bean;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		for (Map.Entry<? extends K, ? extends V> e : map.entrySet()) {
			this.put(e.getKey(), e.getValue());
		}
	}

	@Override
	public void clear() {
		m.clear();
		memdbRsyncService.add(TYPE_CLEAR, "", null, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(String type, String key, String value, boolean isMySelf) {
		K keyObject = (K) JsonSerializer.toObject(key);
		V valueObject = (V) JsonSerializer.toObject(value);
		if (TYPE_PUT.equals(type)) {
			m.put(keyObject, valueObject);
			return true;
		}
		else if (TYPE_REMOVE.equals(type)) {
			m.remove(keyObject);
			return true;
		}
		else if (TYPE_CLEAR.equals(type)) {
			m.clear();
			return true;
		}
		else {
			IllegalArgumentException e = new IllegalArgumentException("未知消息类型[" + type + " key:" + key + "].");
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	public static <K, V> Map<K, V> rsyncMap(Map<K, V> map, Jedis redis) {
		return new RedisRsyncMap<K, V>(map, redis);
	}
	
	public static <K, V> Map<K, V> rsyncMap(Map<K, V> map, Jedis redis, String channel) {
		return new RedisRsyncMap<K, V>(map, redis, channel);
	}
}
