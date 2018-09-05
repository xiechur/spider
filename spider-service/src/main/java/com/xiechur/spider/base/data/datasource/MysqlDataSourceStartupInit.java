package com.xiechur.spider.base.data.datasource;

import com.xiechur.spider.base.data.datasource.jdbc.Jdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MysqlDataSourceStartupInit implements ApplicationListener<ApplicationReadyEvent> {

    Logger logger = LoggerFactory.getLogger(MysqlDataSourceStartupInit.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        String[] beanNames = applicationContext.getBeanNamesForType(Jdbc.class);
        for (String beanName : beanNames) {
            String message = "base framework init datasource:>>>" + beanName;
            Jdbc rhinoJdbcTemplate = (Jdbc) applicationContext.getBean(beanName);
            try {
                Integer initResult = rhinoJdbcTemplate.queryForInt("select 1;");
                message += " execute SQL:select 1; successfully: " + initResult;
            } catch (Exception e) {
                message += "execute SQL failure.";
                logger.info("init error:", e);
            }
            logger.info(message);
        }

    }

}
