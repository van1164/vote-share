package com.van1164.voteshare.controller

import com.van1164.voteshare.dto.UserVoteDTO
import com.van1164.voteshare.dto.VoteDTO
import com.van1164.voteshare.service.*
import io.swagger.v3.oas.annotations.parameters.RequestBody
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping("api/v1/vote")
class VoteController(
    val voteService: VoteService,
    val questionService: QuestionService,
    val redisService: RedisService,
    val userService: UserService
) {

    @PostMapping("/create_vote", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE])
    suspend fun createVote(
        @RequestHeader(value = "Authorization") token: String,
        @RequestPart(value = "mainImage") mainImage: MultipartFile,
        @RequestPart(value = "imageFiles") imageFiles: List<MultipartFile>,
        @RequestPart(value = "data") voteDTO: VoteDTO
    ): Any {
        val email =
            redisService.loadByJwt(token.split(" ")[1]) ?: return ResponseEntity<Any>("Email Not Found", HttpStatus.BAD_REQUEST)
        val user =
            userService.loadUserByEmail(email) ?: return ResponseEntity<Any>("User Not Found", HttpStatus.BAD_REQUEST)
        val vote = voteService.createVote(voteDTO, mainImage, imageFiles, user)
        return ResponseEntity<Any>(hashMapOf(Pair("voteUrl", vote.voteUrl)), HttpStatus.OK)
    }

    @PostMapping("/vote")
    suspend fun createVote(
        @RequestHeader(value = "Authorization") token: String,
        @RequestBody userVoteDTO : UserVoteDTO
    ): Any {
        val email =
            redisService.loadByJwt(token.split(" ")[1]) ?: return ResponseEntity<Any>("Email Not Found", HttpStatus.BAD_REQUEST)
        val user =
            userService.loadUserByEmail(email) ?: return ResponseEntity<Any>("User Not Found", HttpStatus.BAD_REQUEST)
        voteService.userVote(userVoteDTO.voteId,userVoteDTO.questionIdList, user.id!!)
        return ResponseEntity<Any>("성공", HttpStatus.OK)
    }

    @GetMapping("/vote_detail/{vote_url}")
    fun viewVote(@PathVariable(value = "vote_url") voteUrl : String) : Any{
        return voteService.voteDetail(voteUrl)
    }

}