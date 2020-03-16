package com.nesoy.springbatchexample.support;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

/**
 * Custom ExitStatus Example
 * -> 등록방법은? Bean? or Setting?
 *
 * .start(step1())
 *     .on("FAILED")
 *     .end()
 * .from(step1())
 *     .on("COMPLETED WITH SKIPS")
 *     .to(errorPrint1())
 *     .end()
 * .from(step1())
 *     .on("*")
 *     .to(step2())
 *     .end()
 */
public class SkipCheckingListener extends StepExecutionListenerSupport {
    public ExitStatus afterStep(StepExecution stepExecution) {
        String exitCode = stepExecution.getExitStatus().getExitCode();
        if (!exitCode.equals(ExitStatus.FAILED.getExitCode()) &&
                stepExecution.getSkipCount() > 0) {
            return new ExitStatus("COMPLETED WITH SKIPS");
        }
        else {
            return null;
        }
    }
}