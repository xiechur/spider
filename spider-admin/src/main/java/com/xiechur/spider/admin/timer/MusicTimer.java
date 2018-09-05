package com.xiechur.spider.admin.timer;

import com.xiechur.spider.admin.crawl.Crawl;
import com.xiechur.spider.base.data.lock.DistributedLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author xiechurong
 * @Date 2018/9/5
 */
@Component
public class MusicTimer {
    protected static final Logger logger = LoggerFactory.getLogger(MusicTimer.class);
    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Resource(name = "commonRedisTemplate")
    private RedisTemplate<String, String> commonRedisTemplate;

    @Autowired
    @Qualifier("simpleDistributedLock")
    private DistributedLock distributedLock;

    @Autowired
    @Qualifier("crawlImpl")
    private Crawl crawl;

    @PostConstruct
    public void start() {
        logger.info("开始爬取");
        new Thread(new Runnable() {
            @Override
            public void run() {
                crawl.getAllSinger();
            }
        }).start();
        logger.info("爬取完成。");
    }

}
