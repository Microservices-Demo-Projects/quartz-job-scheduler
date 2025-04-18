package com.example.quartz.scheduler.job.prime;

import com.example.quartz.scheduler.job.AbstractJobTemplate;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
public class PrintPrimeNumbers extends AbstractJobTemplate {
    private final String TOTAL_REQUIRED_NUMBERS = "TOTAL_REQUIRED_NUMBERS";

    @Override
    protected void run(JobExecutionContext context) throws JobExecutionException {
        try {
            //Reading the data from job configs.
            int totalRequiredNumbers = Integer.parseInt((String) context.getMergedJobDataMap()
                    .getOrDefault(TOTAL_REQUIRED_NUMBERS, 10));

            printPrimeNumbers(totalRequiredNumbers);
        } catch (InterruptedException e) {
            throw new JobExecutionException("Failed to complete the job.", e);
        }
    }

    public static void printPrimeNumbers(int n) throws InterruptedException {
        int count = 0;
        int num = 2;

        while (count < n) {
            if (isPrime(num)) {
                log.info("Prime Number - {}: {}", count, num);
                count++;
                Thread.sleep(3000); // 3-second delay
            }
            num++;
        }
    }

    public static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
