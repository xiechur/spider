package com.xiechur.spider.base.rsyncMap;

import com.xiechur.spider.util.JsonUtil;
import redis.clients.jedis.JedisPubSub;

public class QueueListener extends JedisPubSub {

	// private Log logger = LogFactory.getLog(QueueListener.class);

	private final MemdbRsyncQueue memdbRsyncQueue;

	private final String sender;

	public QueueListener(MemdbRsyncQueue memdbRsyncQueue, String sender) {
		this.memdbRsyncQueue = memdbRsyncQueue;
		this.sender = sender;
	}

	@Override
	public void onMessage(String channel, String message) {
		QueueBean queueBean = JsonUtil.toObject(message, QueueBean.class);

		boolean isMySelf = this.sender.equals(queueBean.getSender());
	
		this.memdbRsyncQueue.add(queueBean.getType(), queueBean.getKey(), queueBean.getValue(), isMySelf);
	
	}

	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
	
	}

	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		// System.out.println(channel + "=" + subscribedChannels);
	}

	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {
		// System.out.println(pattern + "=" + subscribedChannels);
	}

	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {
		// System.out.println(pattern + "=" + subscribedChannels);
	}


	@Override
	public void onPMessage(String pattern, String channel, String message) {
		System.out.println(pattern + "=" + channel + "=" + message);
	}

}
