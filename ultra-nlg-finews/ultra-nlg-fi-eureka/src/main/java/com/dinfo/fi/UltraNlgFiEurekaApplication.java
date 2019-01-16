package com.dinfo.fi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class UltraNlgFiEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(UltraNlgFiEurekaApplication.class, args);
	}
}
