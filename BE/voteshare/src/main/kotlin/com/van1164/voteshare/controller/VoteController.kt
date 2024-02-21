package com.van1164.voteshare.controller

import com.van1164.voteshare.dto.PopularVoteResponseDTO
import com.van1164.voteshare.dto.UserVoteDTO
import com.van1164.voteshare.dto.VoteDTO
import com.van1164.voteshare.service.*
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("api/v1/vote")
class VoteController(
    val voteService: VoteService,
    val questionService: QuestionService,
    val redisService: RedisService,
    val userService: UserService
) {

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "게시글 조회 성공",
                content = [Content(schema = Schema(implementation = PopularVoteResponseDTO::class))],

            ),
        ]
    )
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create_vote", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE])
    suspend fun createVote(
        @RequestHeader(value = "Authorization") token: String,
        @RequestPart(value = "mainImage") mainImage: MultipartFile?,
        @RequestPart(value = "imageFiles") imageFiles: List<MultipartFile>,
        @Schema(name = "data",
            description = """
            투표에 대한 정보(json)
            title*(String): 투표 제목
            subTitle*(String): 부제목
            publicShare(Boolean): 공개, 비공개
            maxSelectItem(String): 투표 최대 개수
            questionList(List<String>): 질문 리스트
            passWord*(String): 비밀번호
        """,
            required = true,
            example = """
            {"title": "투표 제목","subTitle":"부제목", "publicShare": true, "maxSelectItem": 2, "questionList": {"A질문","B질문"}, "passWord" : null}
        """
        )
        @RequestPart(value = "data") voteDTO: VoteDTO
    ): Any {
        val redisUser =
            redisService.loadByJwt(token.split(" ")[1]) ?: return ResponseEntity<Any>("Email Not Found", HttpStatus.BAD_REQUEST)
        val user =
            userService.loadUserByEmail(redisUser.email) ?: return ResponseEntity<Any>("User Not Found", HttpStatus.BAD_REQUEST)


        val vote = voteService.createVote(voteDTO, mainImage, imageFiles, user)
        return ResponseEntity<Any>(hashMapOf(Pair("voteUrl", vote.voteUrl)), HttpStatus.OK)
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/vote")
    fun userVote(
        @RequestHeader(value = "Authorization") token: String,
        @RequestBody userVoteDTO : UserVoteDTO
    ): Any {
        val redisUser =
            redisService.loadByJwt(token.split(" ")[1]) ?: return ResponseEntity<Any>("Email Not Found", HttpStatus.BAD_REQUEST)
        voteService.userVote(userVoteDTO.voteId,userVoteDTO.questionIdList, redisUser.id!!)
        return ResponseEntity<Any>("성공", HttpStatus.OK)
    }

    @GetMapping("/vote_detail/{vote_url}")
    fun viewVote(@PathVariable(value = "vote_url") voteUrl : String) : Any{
        return voteService.voteDetail(voteUrl)
    }
    
}