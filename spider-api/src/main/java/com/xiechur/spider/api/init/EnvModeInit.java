package com.xiechur.spider.api.init;

import com.xiechur.spider.util.EnvUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
public class EnvModeInit {
    private final Logger logger = LoggerFactory.getLogger(EnvModeInit.class);
    @Autowired
    private Environment env;

    @PostConstruct
    public void initEnvMode() {
        logger.info("初始化环境模式...");
        String[] activeProfiles = env.getActiveProfiles();
        if (activeProfiles != null && activeProfiles.length > 0) {
            List<String> activeProfileList = Arrays.asList(activeProfiles);
            if (activeProfileList.contains(EnvUtil.ENV_MODE_PROD)) {
                logger.info("设置环境模式:prod(生产模式)");
                System.setProperty(EnvUtil.ENV_MODE_PROPERTY, EnvUtil.ENV_MODE_PROD);
            } else if (activeProfileList.contains(EnvUtil.ENV_MODE_TEST)
                    || activeProfileList.contains(EnvUtil.ENV_MODE_TEST_XJP)) {
                logger.info("设置环境模式:test(测试模式)");
                System.setProperty(EnvUtil.ENV_MODE_PROPERTY, EnvUtil.ENV_MODE_TEST);
            } else {
                logger.info("设置环境模式:dev(开发模式)");
                System.setProperty(EnvUtil.ENV_MODE_PROPERTY, EnvUtil.ENV_MODE_DEV);
            }
        } else {
            logger.info("设置环境模式:dev(开发模式)");
            System.setProperty(EnvUtil.ENV_MODE_PROPERTY, EnvUtil.ENV_MODE_DEV);
        }
    }

}
