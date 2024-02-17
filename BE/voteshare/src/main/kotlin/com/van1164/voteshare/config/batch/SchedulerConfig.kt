package com.van1164.voteshare.config.batch

import mu.KotlinLogging
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersInvalidException
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException
import org.springframework.batch.core.repository.JobRestartException
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component


@Component
class SchedulerConfig(
    val jobLauncher: JobLauncher,
    val jobConfig: JobConfig
) {
    val log = KotlinLogging.logger {  }
    @Scheduled(fixedRate  = 100000) //임시로 10초마다 생성
    fun popularVoteRenew(){
        log.info{"popularVoteRenew Start"}

        try {
            jobLauncher.run(jobConfig.job(),JobParameters())
        } catch (e: JobExecutionAlreadyRunningException) {
            log.error(e.message)
        } catch (e: JobInstanceAlreadyCompleteException) {
            log.error(e.message)
        } catch (e: JobParametersInvalidException) {
            log.error(e.message)
        } catch (e: JobRestartException) {
            log.error(e.message)
        }
        log.info{"popularVoteRenew End"}
    }
}