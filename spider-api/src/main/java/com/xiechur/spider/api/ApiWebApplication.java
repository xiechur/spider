package com.xiechur.spider.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.xiechur.spider"})
public class ApiWebApplication {
	public static void main(String[] args){
		new SpringApplication(ApiWebApplication.class).run(args);
	}
}
