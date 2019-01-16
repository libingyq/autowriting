package com.dinfo.autowriting.conf;

import com.dinfo.autowriting.core.id.IdGenerator;
import com.dinfo.autowriting.core.id.SnowflakeIdGenerator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author yangxf
 */
@Configuration
@MapperScan(basePackages = "com.dinfo.autowriting.mapper")
public class DatabaseConfiguration {

    @Resource
    private AutoWritingCustomProperties autoWritingCustomProperties;

    @Bean
    public IdGenerator idGenerator() {
        return new SnowflakeIdGenerator(autoWritingCustomProperties.getWorkerId());
    }
    
    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
