package com.xiechur.spider.service.impl;

import com.xiechur.spider.constants.Constant;
import com.xiechur.spider.service.MmsReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xiechurong
 * @Date 2018/8/31
 */

@Service
public class MmsReportServiceImpl implements MmsReportService {
    private Logger logger = LoggerFactory.getLogger(MmsReportServiceImpl.class);

    @Resource(name = "commonRedisTemplate")
    private RedisTemplate<String, String> commonRedisTemplate;
    @Override
    public void sendSingerId(Integer singerId) {
        send(singerId+"");
    }

    private void send(String s){
        try {
            commonRedisTemplate.opsForList().leftPush(Constant.MQ_SINGER_ID, s);
            logger.info("send MmsReport to redis mq: {}", s);
        }catch (Exception e){
            logger.info("send MmsReport to redis mq: {}", e);
        }
        finally {

        }
    }
}
