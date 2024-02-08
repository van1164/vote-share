package com.van1164.voteshare.controller

import com.van1164.voteshare.domain.User
import com.van1164.voteshare.domain.Vote
import com.van1164.voteshare.service.RedisService
import com.van1164.voteshare.service.UserService
import com.van1164.voteshare.service.VoteService
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["api/v1/user"])
@PreAuthorize("isAuthenticated()")
class UserController(val userService: UserService, val redisService: RedisService, val voteService: VoteService) {

    @GetMapping("/loginPage")
    fun loginPage(): String {
        return "loginPage"
    }

    @GetMapping("/login")
    fun createUser(): ResponseEntity<Any> {
        return ResponseEntity("로그인성공", HttpStatus.OK)
    }

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "게시글 조회 성공",
                content = [Content(schema = Schema(implementation = User::class)), Content(schema = Schema(implementation = Vote::class))]
            ),
        ]
    )
    @GetMapping("/mypage")
    fun getMyPage(
        @RequestHeader(value = "Authorization") token: String
    ): ResponseEntity<Any> {
        val email =
            redisService.loadByJwt(token.split(" ")[1]) ?: return ResponseEntity<Any>(
                "Email Not Found",
                HttpStatus.BAD_REQUEST
            )
        val user =
            userService.loadUserByEmail(email) ?: return ResponseEntity<Any>("User Not Found", HttpStatus.BAD_REQUEST)
        val vote = voteService.loadVoteListByUserId(user.id!!)
        val response = hashMapOf<String,Any>()
        response["user"] = user
        response["vote"] = vote
        return ResponseEntity(user, HttpStatus.OK)
    }


}