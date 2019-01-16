package com.dinfo.autowriting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author yangxf
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AutoWritingApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(AutoWritingApplication.class, args);
    }
    
}
