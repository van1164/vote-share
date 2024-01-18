package com.van1164.voteshare.controller

import com.van1164.voteshare.dto.VoteDTO
import com.van1164.voteshare.service.VoteService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/vote")
class VoteController {
    val voteService = VoteService()


    @PostMapping("/create_vote")
    fun createVote(voteDTO: VoteDTO): String {
        return voteDTO.questionList[0].question
    }
}