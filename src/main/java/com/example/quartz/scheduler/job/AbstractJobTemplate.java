package com.example.quartz.scheduler.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.MDC;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
public abstract class AbstractJobTemplate extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        long startTime = System.currentTimeMillis();
        try {
            MDC.put("JobName", context.getJobDetail().getKey().getName());
            run(context);
        } finally {
            MDC.clear();
        }
        log.info("Total execution time: {}ms", System.currentTimeMillis() - startTime);
    }

    protected abstract void run(JobExecutionContext context) throws JobExecutionException;

}
