package com.xiechur.spider.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.xiechur.spider"})
public class AdminWebApplication {
	public static void main(String[] args){
		new SpringApplication(AdminWebApplication.class).run(args);
	}
}
