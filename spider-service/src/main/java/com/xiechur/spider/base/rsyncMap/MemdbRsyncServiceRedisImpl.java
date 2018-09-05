package com.xiechur.spider.base.rsyncMap;

import com.xiechur.spider.util.JsonUtil;
import redis.clients.jedis.Jedis;

public class MemdbRsyncServiceRedisImpl implements Context, MemdbRsyncService {

	 
    private Jedis redis;

	private final String channel;

	private final String sender;

	private final QueueListener queueListener;

	public MemdbRsyncServiceRedisImpl(Jedis redis, String channel, MemdbRsyncQueue memdbRsyncQueue) {
		this.redis = redis;
		this.channel = channel;
		this.sender = Integer.toString(redis.hashCode());
		queueListener = new QueueListener(memdbRsyncQueue, sender);
	}

	@Override
	public boolean add(String type, String key, String value, boolean isMySelf) {
		QueueBean queueBean = new QueueBean();
		queueBean.setType(type);
		queueBean.setKey(key);
		queueBean.setValue(value);
		queueBean.setSender(sender);
		redis.publish(channel, JsonUtil.toJson(queueBean));
		return true;
	}

	@Override
	public void init() {
		new Thread() {
			@Override
			public void run() {
				redis.subscribe(queueListener, channel); 
			};
		}.start();
	}

	@Override
	public void destroy() {

	}

}

