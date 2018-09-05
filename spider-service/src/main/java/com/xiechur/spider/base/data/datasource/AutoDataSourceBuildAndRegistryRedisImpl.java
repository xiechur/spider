package com.xiechur.spider.base.data.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.sql.DataSource;
import java.util.Map;

public class AutoDataSourceBuildAndRegistryRedisImpl {

    Logger logger = LoggerFactory.getLogger(AutoDataSourceBuildAndRegistryRedisImpl.class);

    AutoDatasourceConfigurationNode autoDatasourceConfigurationNode;
    BeanDefinitionRegistry registry;

    public AutoDataSourceBuildAndRegistryRedisImpl(AutoDatasourceConfigurationNode autoDatasourceConfigurationNode,
            BeanDefinitionRegistry registry) {
        this.autoDatasourceConfigurationNode = autoDatasourceConfigurationNode;
        this.registry = registry;
    }

    public void buildAndRegistry() {

        Map<String, Object> configProperties = autoDatasourceConfigurationNode.getConfigProperties();
        String nodeName = autoDatasourceConfigurationNode.getNodeName();
        logger.debug("redis->" + nodeName + "->" + configProperties);
        Class<? extends DataSource> dataSourceClass = new RedisDataSourceLoader().loadDataSource(this.getClass()
                .getClassLoader());

        String redisDataSourceBeanName = nodeName + "RedisDataSource";
        String redisTempleBeanName = nodeName + "RedisTemplate";

        // register DataSource
        {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(dataSourceClass);
            MutablePropertyValues properties = new MutablePropertyValues(configProperties);
            beanDefinition.getPropertyValues().addPropertyValues(properties);
            registry.registerBeanDefinition(redisDataSourceBeanName, beanDefinition);
        }

        // register redisTemple
        {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(new RedisTemplate<String, String>().getClass());
            beanDefinition.getPropertyValues().addPropertyValue("connectionFactory",
                    new RuntimeBeanReference(redisDataSourceBeanName));
            if ("session".equals(nodeName)) {
                beanDefinition.getPropertyValues().add("keySerializer", new StringRedisSerializer());
                beanDefinition.getPropertyValues().add("hashKeySerializer", new StringRedisSerializer());
            } else {
                beanDefinition.getPropertyValues().add("keySerializer", new StringRedisSerializer());
                beanDefinition.getPropertyValues().add("valueSerializer", new StringRedisSerializer());
                beanDefinition.getPropertyValues().add("hashKeySerializer", new StringRedisSerializer());
                beanDefinition.getPropertyValues().add("hashValueSerializer", new StringRedisSerializer());
            }

            registry.registerBeanDefinition(redisTempleBeanName, beanDefinition);
        }

        logger.info(":::redis:::" + redisTempleBeanName);

    }

    public void buildDefaultRegistry() {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(RedisDefaultDataSource.class);
        beanDefinition.setPrimary(true);
        registry.registerBeanDefinition("redisDefaultDataSource", beanDefinition);
    }

}
