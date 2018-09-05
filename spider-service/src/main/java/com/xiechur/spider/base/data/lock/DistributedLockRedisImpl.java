package com.xiechur.spider.base.data.lock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.connection.jedis.JedisConverters;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;

public class DistributedLockRedisImpl implements DistributedLock {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
    RedisTemplate<String, String> redisTemplate;

    private int lockMaxLoopCount = 10;
    private int keyLockTotalTime = 8;
    private int lockIntervalSleepMillis = 1;

    public int getLockMaxLoopCount() {
        return lockMaxLoopCount;
    }

    public void setLockMaxLoopCount(int lockMaxLoopCount) {
        this.lockMaxLoopCount = lockMaxLoopCount;
    }

    public int getKeyLockTotalTime() {
        return keyLockTotalTime;
    }

    public void setKeyLockTotalTime(int keyLockTotalTime) {
        this.keyLockTotalTime = keyLockTotalTime;
    }

    public int getLockIntervalSleepMillis() {
        return lockIntervalSleepMillis;
    }

    public void setLockIntervalSleepMillis(int lockIntervalSleepMillis) {
        this.lockIntervalSleepMillis = lockIntervalSleepMillis;
    }

    public DistributedLockRedisImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public DistributedLockRedisImpl(RedisTemplate<String, String> redisTemplate, int keyLockTotalTime,
            int lockMaxLoopCount) {
        this.redisTemplate = redisTemplate;
        this.keyLockTotalTime = keyLockTotalTime;
        this.lockMaxLoopCount = lockMaxLoopCount;
    }

    @Override
    public boolean lock(String key) throws DistributedLockException {
        return this.lock(key, this.keyLockTotalTime, this.lockMaxLoopCount);
    }

    @Override
    public boolean lock(String key, int keyUnLockTotalTime) {
        return this.lock(key, keyUnLockTotalTime, this.lockMaxLoopCount);
    }
    
    @Override
    public boolean isLocked(String key) {
    	return this.redisTemplate.hasKey(key);
    }

    public boolean lock(String key, int keyLockTotalTime, int lockMaxLoopCount) throws DistributedLockException {
        int i = 0;
        while (true) {
            i++;
            boolean lock = this.innerLock(key, keyLockTotalTime);
            if (lock) {
                return lock;
            }
            try {
                if (lockMaxLoopCount > 0) {
                    Thread.sleep(this.lockIntervalSleepMillis);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i > lockMaxLoopCount) {
            	logger.error("OUT_OF_LOCK_MAX_LOOP_COUNT:" + key);
//                throw new DistributedLockException("OUT_OF_LOCK_MAX_LOOP_COUNT:" + key);
            	return false;
            }
        }
    }

    @Override
    public boolean unlock(String key) {
        return this.innerUnlock(key);
    }

    private Boolean set(RedisConnection redisConnection, byte[] key, byte[] value, Expiration expiration,
            SetOption option) {

        try {

            Jedis jedis = (Jedis) redisConnection.getNativeConnection();

            byte[] nxxx = JedisConverters.toSetCommandNxXxArgument(option);
            byte[] expx = JedisConverters.toSetCommandExPxArgument(expiration);
            
            String result = jedis.set(key, value, nxxx, expx, expiration.getExpirationTime());

            result = StringUtils.trimToEmpty(result).toLowerCase();

            return "ok".equals(result);

        } catch (Exception e) {
            throw new RuntimeException("reids操作锁失败:" + key, e);
        }
    }

    private boolean innerLock(final String key, final int keyLockTotalTime) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {

            final byte[] rawKey = new StringRedisSerializer().serialize(key);
            final byte[] value = new StringRedisSerializer().serialize(String.valueOf(System.currentTimeMillis()));

            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {

                Expiration expiration = Expiration.seconds(keyLockTotalTime);
                return set(connection, rawKey, value, expiration, SetOption.SET_IF_ABSENT);

            }
        });

    }

    private boolean innerUnlock(String key) {
        redisTemplate.delete(key);
        return true;
    }

}
