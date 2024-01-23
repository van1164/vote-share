package com.van1164.voteshare.controller

import com.van1164.voteshare.domain.Vote
import com.van1164.voteshare.dto.VoteDTO
import com.van1164.voteshare.service.*
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/vote")
class VoteController(val voteService: VoteService, val s3Service: S3Service, val questionService: QuestionService,val redisService: RedisService,val userService: UserService) {

    @PostMapping("/create_vote", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    suspend fun createVote(@RequestHeader(value="Authorization") token : String,@RequestPart voteDTO: VoteDTO): Any {
        val images = voteDTO.questionList.map{it.image}
        val email = redisService.loadByJwt(token) ?: return ResponseEntity<Any>("Email Not Found",HttpStatus.BAD_REQUEST)
        val user = userService.loadUserByEmail(email) ?: return ResponseEntity<Any>("User Not Found",HttpStatus.BAD_REQUEST)
        val vote = voteService.createVote(voteDTO,user)
        s3Service.uploadMultipleImages(images)
        voteDTO.questionList.forEach{
            it.question
        }
        questionService.createQuestion(voteDTO.title,vote)
        return ResponseEntity<Any>(vote,HttpStatus.OK)
    }
}