package com.van1164.voteshare.repository.vote

import com.van1164.voteshare.domain.Question
import com.van1164.voteshare.domain.User
import com.van1164.voteshare.domain.Vote
import com.van1164.voteshare.dto.PopularVoteResponseDTO
import com.van1164.voteshare.dto.VoteDetailDTO
import jakarta.transaction.Transactional

interface VoteCustomRepository  {

//    @Transactional
//    fun save(vote: Vote)

    @Transactional
    fun loadPopularVote(): MutableList<PopularVoteResponseDTO>

    fun votting(questionId: Long)
    fun loadVoteDetailByVoteUrl(voteUrl: String): Vote

    fun loadQuestionList(voteUrl: String): List<Question>
    fun loadVoteListByUserId(id: Long): MutableList<VoteDetailDTO>

    @Transactional
    fun addVote(user: User, vote: Vote)

    fun loadVoteListById(popularVoteIdList: List<Long?>): Any
    fun loadVoteById(voteId: Long): Vote
    fun plusVoteSum(vote: Vote)

    fun newLoadPopularVote() :MutableList<PopularVoteResponseDTO>
}