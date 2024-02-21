package com.van1164.voteshare.controller

import com.van1164.voteshare.domain.User
import com.van1164.voteshare.dto.PopularVoteResponseDTO
import com.van1164.voteshare.service.UserService
import com.van1164.voteshare.service.VoteService
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class MainController(val voteService : VoteService) {

    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "게시글 조회 성공",
                content = [Content(schema = Schema(implementation = PopularVoteResponseDTO::class))]
            ),
        ]
    )
    @GetMapping("/main_page")
    fun mainPage() : ResponseEntity<Any> {
        return voteService.loadMainPageData()
    }

}