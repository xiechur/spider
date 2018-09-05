package com.xiechur.spider.base.data.lock;

import org.springframework.data.redis.core.RedisTemplate;

public class SimpleDistributedLockRedisImpl extends DistributedLockRedisImpl {

    public SimpleDistributedLockRedisImpl(RedisTemplate<String, String> redisTemplate) {
        super(redisTemplate, 24 * 60 * 60, 0);
    }

    public SimpleDistributedLockRedisImpl(RedisTemplate<String, String> redisTemplate, int keyLockTotalTime) {
        super(redisTemplate, keyLockTotalTime, 0);
    }

    @Override
    public boolean lock(String key) {
        try {
            return super.lock(key);
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public boolean lock(String key, int keyLockTotalTime) {
        try {
            return super.lock(key, keyLockTotalTime, 0);
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public boolean lock(String key, int keyLockTotalTime, int lockMaxLoopCount) {
        try {
            return super.lock(key, keyLockTotalTime, lockMaxLoopCount);
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public boolean unlock(String key) {
        try {
            return super.unlock(key);
        } catch (Throwable e) {
            return false;
        }
    }

}
