package com.example.helloworldbatch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class HWJobExecutionListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Before the Job - Job name: " + jobExecution.getJobInstance().getJobName());
        System.out.println("Before the Job: " + jobExecution.getExecutionContext().toString());
        jobExecution.getExecutionContext().put("job", "HW Job is running");
        System.out.println("Before the Job - setting Execution Context: " + jobExecution.getExecutionContext().toString());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("After the job: " + jobExecution.getExecutionContext().toString());
    }
}
