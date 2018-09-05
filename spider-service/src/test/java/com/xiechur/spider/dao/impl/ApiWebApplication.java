package com.xiechur.spider.dao.impl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(scanBasePackages = { "com.huya.server.ms.pixel" })
public class ApiWebApplication {
	public static void main(String[] args){
		new SpringApplication(ApiWebApplication.class).run(args);
	}
}
