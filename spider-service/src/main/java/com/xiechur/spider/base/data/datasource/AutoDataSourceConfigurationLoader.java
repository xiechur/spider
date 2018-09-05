package com.xiechur.spider.base.data.datasource;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AutoDataSourceSpringBootRegister.class)
public class AutoDataSourceConfigurationLoader {

}
