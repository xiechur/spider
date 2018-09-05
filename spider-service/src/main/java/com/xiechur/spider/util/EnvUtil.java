package com.xiechur.spider.util;

import org.apache.commons.lang.StringUtils;

public class EnvUtil {
    public static final String ENV_MODE_PROPERTY = "xiechur.envMode";
    public static final String ENV_MODE_PROD = "prod";
    public static final String ENV_MODE_TEST = "test";
    public static final String ENV_MODE_TEST_XJP = "test_xjp";
    public static final String ENV_MODE_DEV = "dev";

    public static String getEnvMode() {
        String envMode = System.getProperty(ENV_MODE_PROPERTY);
        if (StringUtils.isBlank(envMode)) {
            return ENV_MODE_DEV;
        }

        return envMode;
    }

    public static boolean isProd() {
        return ENV_MODE_PROD.equalsIgnoreCase(System.getProperty(ENV_MODE_PROPERTY));
    }

    public static boolean isTest() {
        return ENV_MODE_TEST.equalsIgnoreCase(System.getProperty(ENV_MODE_PROPERTY));
    }

    public static boolean isDev() {
        String envMode = System.getProperty(ENV_MODE_PROPERTY);
        return StringUtils.isBlank(envMode) || ENV_MODE_DEV.equalsIgnoreCase(envMode);
    }
}
