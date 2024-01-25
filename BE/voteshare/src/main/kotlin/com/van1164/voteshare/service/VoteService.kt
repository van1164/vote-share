package com.van1164.voteshare.service

import com.van1164.voteshare.domain.User
import com.van1164.voteshare.domain.Vote
import com.van1164.voteshare.dto.VoteDTO
import com.van1164.voteshare.repository.VoteRepository
import com.van1164.voteshare.util.UuidUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.collections.HashMap


@Service
class VoteService(val voteRepository:VoteRepository,val questionService: QuestionService, val s3Service: S3Service) : BaseService() {

    suspend fun createVote(voteDTO: VoteDTO, user : User): Vote {
        val profileImageUrl = s3Service.uploadImage(voteDTO.mainImage)
        val voteUrl = UuidUtil().createUUID()
        val title = voteDTO.title
        val subTitle = voteDTO.subTitle
        val createDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
        val updatedDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
        val vote = Vote(title, subTitle, voteUrl, createDate, updatedDate,  user)
        tx.begin()
        voteRepository.save(vote)
        tx.commit()

        questionService.createQuestionList(voteDTO.questionList,vote)
        return vote
    }

    fun loadMainPageData() : ResponseEntity<Any> {
        val popularVoteList = voteRepository.loadPopularVote()
        val response = HashMap<String,Any>()
        response["popularVoteList"] = popularVoteList
        response["test"] = "TTTTTTTTTTTTTTTTTTTTTTTT"
        return ResponseEntity(response, HttpStatus.OK)
    }

}