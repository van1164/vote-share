package com.van1164.voteshare.config.batch

import com.van1164.voteshare.repository.PopularVoteRepository
import com.van1164.voteshare.repository.VoteRepository
import mu.KotlinLogging
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

class CreateMockVoteTasklet (
    val voteRepository: VoteRepository,
    val popularVoteRepository: PopularVoteRepository
    ): Tasklet {
        val log = KotlinLogging.logger{}
        override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus {
            log.info { "tasklet start" }



            //read
            val popularVoteList = voteRepository.loadPopularVote()


            //process
            val popularVoteIdList = popularVoteList.map{it.id}


            //write
            popularVoteRepository.resetAndSave(popularVoteIdList)

            return RepeatStatus.FINISHED
        }
    }