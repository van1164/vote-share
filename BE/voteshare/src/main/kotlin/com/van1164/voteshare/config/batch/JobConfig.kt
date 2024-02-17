package com.van1164.voteshare.config.batch

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
@EnableBatchProcessing
class JobConfig(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val tasklet: VoteTasklet
) {
    @Bean
    fun job(): Job {
        return JobBuilder("job", jobRepository)
            .start(step())
            .build()
    }
    @Bean
    fun step(): Step {
        return StepBuilder("step", jobRepository)
            .tasklet(tasklet, transactionManager)
            .build()
    }
}