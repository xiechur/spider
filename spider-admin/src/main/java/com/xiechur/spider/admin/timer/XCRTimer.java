package com.xiechur.spider.admin.timer;

import com.xiechur.spider.admin.init.ThreadPoolConfig;
import com.xiechur.spider.base.data.lock.DistributedLock;
import com.xiechur.spider.constants.Constant;
import com.xiechur.spider.model.Singer;
import com.xiechur.spider.service.SingerService;
import com.xiechur.spider.util.MiscUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author xiechurong
 * @Date 2018/8/30
 */
@Component
public class XCRTimer {
    protected static final Logger logger = LoggerFactory.getLogger(XCRTimer.class);

    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Resource(name = "commonRedisTemplate")
    private RedisTemplate<String, String> commonRedisTemplate;

    @Autowired
    @Qualifier("simpleDistributedLock")
    private DistributedLock distributedLock;

    @Autowired
    private SingerService singerService;

    private Long lastLogTime = null;

    @PostConstruct
    public void start() {
        logger.info("开始启动消息发送任务计划...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                monitor();
            }
        }).start();
        logger.info("启动消息发送任务计划完成。");
    }

    protected void monitor() {
        try {
            while (true) {
                // 线程池队列过大
                if (threadPoolTaskExecutor.getActiveCount() >= ThreadPoolConfig.MAX_QUEUE_CAPACITY) {
                    logger.warn("线程池任务数达到: {}个，达到处理能力上限！", ThreadPoolConfig.MAX_QUEUE_CAPACITY);
                    sleep();
                    continue;
                }

                // 每5分钟打印一次日志,以方法开发跟踪定时器是否在正常执行
                if (lastLogTime == null || (System.currentTimeMillis() - lastLogTime) >= 1000 * 60 * 2) {
                    logger.info("message sender is running.");
                    lastLogTime = System.currentTimeMillis();
                }
                try {
                    // 阻塞30秒,直到获得一个数据
                    String msgStr = commonRedisTemplate.opsForList().rightPop(Constant.MQ_SINGER_ID, 30,
                            TimeUnit.SECONDS);
                    if (msgStr == null) {
                        sleep();
                        continue;
                    }
                    threadPoolTaskExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            logger.info("监听到消息，开始发送... msg:{}", msgStr);
                            long beginTimeMillis = System.currentTimeMillis();
                            try {
                                handler(msgStr);
                            } catch (Exception e) {
                                logger.error("发送消息失败！msg:{}, 原因:", msgStr, e);
                            } finally {
                                logger.info("发送消息完成！耗时:{}ms, msg:{}", (System.currentTimeMillis() - beginTimeMillis),
                                        msgStr);
                            }
                        }
                    });
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


    /**
     * 处理消息
     *
     * @param msgStr
     */
    private void handler(String msgStr) {
        logger.info("msgStr:"+msgStr);
        if (org.apache.commons.lang3.StringUtils.isNotBlank(msgStr)){
            if (MiscUtil.parseInt(msgStr,0) > 0) {
                Singer singer = new Singer();
                singer.setSingerId(msgStr);
                if (!singerService.exist(msgStr)){
                    boolean b = singerService.add(singer);
                    if (!b){
                        logger.error("singerService.add.failed:"+singer.toString());
                    }
                }
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
