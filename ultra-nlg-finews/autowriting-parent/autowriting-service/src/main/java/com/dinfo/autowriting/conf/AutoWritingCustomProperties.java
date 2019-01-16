package com.dinfo.autowriting.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangxf
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "app.autowriting.custom")
public class AutoWritingCustomProperties {

    private int workerId = 1;
    
    private String[] indexBasePackages = {"com.dinfo.autowriting.index"};
    
}
