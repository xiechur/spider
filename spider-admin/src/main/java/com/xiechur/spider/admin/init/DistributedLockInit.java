package com.xiechur.spider.admin.init;

import com.xiechur.spider.base.data.lock.DistributedLock;
import com.xiechur.spider.base.data.lock.SimpleDistributedLockRedisImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class DistributedLockInit {

    @Bean
    @Qualifier("simpleDistributedLock")
    public DistributedLock getSimpleDistributedLock(
            @Qualifier("commonRedisTemplate") RedisTemplate<String, String> redisTemplate) {
        return new SimpleDistributedLockRedisImpl(redisTemplate, 8);
    }

}
