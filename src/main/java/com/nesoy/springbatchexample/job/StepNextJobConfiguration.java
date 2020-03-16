package com.nesoy.springbatchexample.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StepNextJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job stepNextJob() {
        return jobBuilderFactory.get("stepNextJob")
                .start(step1())
                .next(step2()) // step 연결
                .next(step3())
                .build();

    }

    /**
     * 꼭 Bean이 아니여도 된다.
     */
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(((contribution, chunkContext) -> {
                    // StepContribution, ChunkContext
                    log.info(">>>>> STEP 1 ");
                    return RepeatStatus.FINISHED; // Indicates that processing is finished (either successful or unsuccessful)
                    // return RepeatStatus.CONTINUABLE; // Indicates that processing can continue.
                })).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet(((contribution, chunkContext) -> {
                    log.info(">>>>> STEP 2 ");
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet(((contribution, chunkContext) -> {
                    log.info(">>>>> STEP 3 ");
                    return RepeatStatus.FINISHED;
                })).build();
    }
}
