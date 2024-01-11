package com.van1164.voteshare.service

import com.van1164.voteshare.data.Question
import com.van1164.voteshare.data.Vote
import com.van1164.voteshare.dto.VoteDTO
import com.van1164.voteshare.repository.QuestionRepository
import com.van1164.voteshare.repository.VoteRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date


@Service
class VoteService : BaseService() {
    val voteRepository = VoteRepository()

    fun createVote(voteDTO: VoteDTO): Vote {
        val user = voteDTO.user
        val profileImageUrl = "testURL"
        val voteUrl = "testVoteURL"
        val title = voteDTO.title
        val subTitle = voteDTO.subTitle
        val createDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
        val updatedDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())

        val vote = Vote(title, subTitle, voteUrl, createDate, updatedDate,  user)

        tx.begin()
        voteRepository.save(vote)
        tx.commit()

        return vote
    }
}