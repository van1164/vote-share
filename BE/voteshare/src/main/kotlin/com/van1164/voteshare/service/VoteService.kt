package com.van1164.voteshare.service

import com.van1164.voteshare.domain.User
import com.van1164.voteshare.domain.Vote
import com.van1164.voteshare.dto.VoteDTO
import com.van1164.voteshare.repository.UserRepository
import com.van1164.voteshare.repository.VoteRepository
import com.van1164.voteshare.util.ServiceUtil
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.collections.HashMap


@Service
class VoteService(
    val voteRepository: VoteRepository,
    val questionService: QuestionService,
    val userRepository: UserRepository,
    val s3Service: S3Service
) :
    BaseService() {

    suspend fun createVote(voteDTO: VoteDTO, mainImage: MultipartFile?, images: List<MultipartFile>, user: User): Vote {
        val profileImageUrl = mainImage?.let { s3Service.uploadImage(it) }
        val voteUrl = ServiceUtil.createUUID()
        val title = voteDTO.title
        val subTitle = voteDTO.subTitle
        val createDate = ServiceUtil.dateTimeNow()
        val updatedDate = ServiceUtil.dateTimeNow()
        val vote = Vote(
            title,
            subTitle,
            voteUrl,
            createDate,
            updatedDate,
            user,
            mainImageUrl = profileImageUrl,
            publicShare = voteDTO.publicShare
        )


        tx.begin()
        userRepository.addVote(user,vote)
        voteRepository.save(vote)
        tx.commit()

        questionService.createQuestionList(voteDTO.questionList, images, vote)
        return vote
    }

    fun loadMainPageData(): ResponseEntity<Any> {
        val popularVoteList = voteRepository.loadPopularVote()
        val response = HashMap<String, Any>()
        response["popularVoteList"] = popularVoteList
        response["test"] = "TTTTTTTTTTTTTTTTTTTTTTTT"
        return ResponseEntity(response, HttpStatus.OK)
    }

}