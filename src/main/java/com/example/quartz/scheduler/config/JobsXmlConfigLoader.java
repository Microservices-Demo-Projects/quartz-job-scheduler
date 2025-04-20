package com.example.quartz.scheduler.config;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@Configuration
public class JobsXmlConfigLoader implements ApplicationContextInitializer<GenericApplicationContext> {

    @Override
    public void initialize(GenericApplicationContext context) {
        // Get environment
        ConfigurableEnvironment env = context.getEnvironment();

        // Read the XML file path from properties
        String xmlPath = env.getProperty("jobs.configs.xmlPath");

        if (xmlPath != null && !xmlPath.isEmpty()) {
            XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(context);
            reader.loadBeanDefinitions(xmlPath);

        } else {
            throw new IllegalArgumentException("Property 'jobs.configs.xmlPath' is not set or empty");
        }
    }
}
