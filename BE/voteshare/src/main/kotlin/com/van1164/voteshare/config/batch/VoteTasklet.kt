package com.van1164.voteshare.config.batch

import com.van1164.voteshare.repository.PopularVoteRepository
import com.van1164.voteshare.repository.vote.VoteRepository
import mu.KotlinLogging
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@StepScope
@Component
class VoteTasklet(
    val voteRepository: VoteRepository,
    val popularVoteRepository: PopularVoteRepository
): Tasklet {
    val log = KotlinLogging.logger{}
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        log.info { "tasklet start" }

        //read
        val popularVoteList = voteRepository.newLoadPopularVote()


        //process
        val popularVoteIdList = popularVoteList.map{it.id}


        //write
        popularVoteRepository.resetAndSave(popularVoteIdList)

        log.info { "tasklet end" }
        return RepeatStatus.FINISHED
    }
}