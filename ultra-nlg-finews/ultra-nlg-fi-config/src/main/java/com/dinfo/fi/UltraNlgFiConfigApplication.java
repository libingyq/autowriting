package com.dinfo.fi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class UltraNlgFiConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(UltraNlgFiConfigApplication.class, args);
	}
}
