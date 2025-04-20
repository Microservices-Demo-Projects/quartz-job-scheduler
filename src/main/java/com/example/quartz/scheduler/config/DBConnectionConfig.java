package com.example.quartz.scheduler.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DBConnectionConfig {

    @Bean
    @ConfigurationProperties("quartz.datasource")
    public DataSourceProperties quartzSchedulerDataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean
    public DataSource quartzSchedulerDataSource() {
        return quartzSchedulerDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }
}
