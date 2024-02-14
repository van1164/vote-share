package com.van1164.voteshare.config.batch

import lombok.extern.slf4j.Slf4j
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.builder.TaskletStepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.AbstractPlatformTransactionManager

@Configuration
class JobConfig {
//    @Bean
//    fun job(jobRepository: JobRepository): Job {
//        return JobBuilder("job",jobRepository)
//            .start(step)
//            .build()
//    }
//
//
//    @Bean
//    fun step(jobRepository: JobRepository,platformTransactionManager: PlatformTransactionManager) : Step{
//        return StepBuilder("step",jobRepository)
//            .tasklet()
//    }
}
