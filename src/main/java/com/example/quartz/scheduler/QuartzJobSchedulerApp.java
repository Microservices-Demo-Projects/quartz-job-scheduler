package com.example.quartz.scheduler;

import com.example.quartz.scheduler.config.JobsXmlConfigLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class QuartzJobSchedulerApp {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(QuartzJobSchedulerApp.class);
        app.addInitializers(new JobsXmlConfigLoader());
        app.run(args);
    }
}
