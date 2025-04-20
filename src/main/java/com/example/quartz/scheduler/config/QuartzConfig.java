package com.example.quartz.scheduler.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Slf4j
@Configuration
public class QuartzConfig {

    private final DataSource dataSource;
    private final ApplicationContext applicationContext;
    private final Environment environment;

    public QuartzConfig(@Qualifier("quartzSchedulerDataSource") DataSource dataSource,
                        ApplicationContext applicationContext, Environment environment) {
        this.dataSource = dataSource;
        this.applicationContext = applicationContext;
        this.environment = environment;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

        // Retrieve all triggers (CronTriggerFactoryBean) dynamically loaded from the XML
        String[] triggerBeanNames = applicationContext.getBeanNamesForType(CronTriggerFactoryBean.class);
        org.quartz.Trigger[] triggers = new org.quartz.Trigger[triggerBeanNames.length];
        for (int i = 0; i < triggerBeanNames.length; i++) {
            CronTriggerFactoryBean triggerFactoryBean = (CronTriggerFactoryBean) applicationContext.getBean(triggerBeanNames[i]);
            triggers[i] = triggerFactoryBean.getObject(); // Get the Quartz Trigger from the factory bean
        }
        // Set the triggers to the SchedulerFactoryBean
        schedulerFactoryBean.setTriggers(triggers);

        Properties quartzProperties = new Properties();
        quartzProperties.setProperty("org.quartz.scheduler.instanceName", environment.getProperty("quartz.scheduler.instanceName"));
        quartzProperties.setProperty("org.quartz.scheduler.instanceId", environment.getProperty("quartz.scheduler.instanceId"));
        quartzProperties.setProperty("org.quartz.scheduler.threadName", environment.getProperty("quartz.scheduler.threadName"));


        quartzProperties.setProperty("org.quartz.threadPool.class", environment.getProperty("quartz.threadPool.class"));
        quartzProperties.setProperty("org.quartz.threadPool.threadCount", environment.getProperty("quartz.threadPool.threadCount"));


        quartzProperties.setProperty("org.quartz.jobStore.class", environment.getProperty("quartz.jobStore.class"));
        quartzProperties.setProperty("org.quartz.jobStore.driverDelegateClass", environment.getProperty("quartz.jobStore.driverDelegateClass"));
        quartzProperties.setProperty("org.quartz.jobStore.tablePrefix", environment.getProperty("quartz.jobStore.tablePrefix"));
        // Quartz properties for clustering
        quartzProperties.setProperty("org.quartz.jobStore.isClustered", environment.getProperty("quartz.jobStore.isClustered"));

        schedulerFactoryBean.setQuartzProperties(quartzProperties);

         schedulerFactoryBean.setDataSource(dataSource);

        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true); // Graceful shutdown

        return schedulerFactoryBean;
    }


}
