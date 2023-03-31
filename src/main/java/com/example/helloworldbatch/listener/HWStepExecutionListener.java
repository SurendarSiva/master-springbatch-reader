package com.example.helloworldbatch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class HWStepExecutionListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("Before Step Execution: "+ stepExecution.getJobExecution().getExecutionContext());
        System.out.println("Job Parameters: " + stepExecution.getJobExecution().getJobParameters());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("After Step Execution: "+ stepExecution.getJobExecution().getExecutionContext());
        return null;
    }
}
