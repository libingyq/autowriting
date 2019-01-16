package com.dinfo.fi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class UltraNlgFiFuncApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(UltraNlgFiFuncApplication.class, args);
		SpringUtil.setApplicationContext(run);
	}
}
