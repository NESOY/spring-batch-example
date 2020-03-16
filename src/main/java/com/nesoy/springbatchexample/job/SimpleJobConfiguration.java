package com.nesoy.springbatchexample.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration // Job은 @Configuration으로 등록해서 사용
public class SimpleJobConfiguration {
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;

    public SimpleJobConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    /**
     * Job은 여러개의 Step으로 구성되어 있다.
     */
    @Bean
    public Job simpleJob() {
        return jobBuilderFactory.get("simpleJob") // Job을 생성
                .start(simpleStep1()) // 해당 스텝 선언
                .build();
    }

    @Bean
    public Step simpleStep1() {
        return stepBuilderFactory.get("simpleStep1") // Step 생성
                // Step 안에서 수행될 기능 선언
                // Step 내부의 tasklet으로 구성
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> Step1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
