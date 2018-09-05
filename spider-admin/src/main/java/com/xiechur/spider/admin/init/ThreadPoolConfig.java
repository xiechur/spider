package com.xiechur.spider.admin.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 配置线程池
 *
 */
@Configuration
public class ThreadPoolConfig {

    public static final int MAX_QUEUE_CAPACITY = 100;

    @Bean(name = "threadPoolTaskExecutor")
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int coreSize = Runtime.getRuntime().availableProcessors();
        executor.setCorePoolSize(coreSize);
        executor.setMaxPoolSize(coreSize * 2 + 2);
        executor.setQueueCapacity(MAX_QUEUE_CAPACITY);
        executor.initialize();
        return executor;
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        return threadPoolTaskScheduler;
    }
}
