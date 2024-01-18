package com.van1164.voteshare.controller

import com.van1164.voteshare.dto.VoteDTO
import com.van1164.voteshare.service.VoteService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/vote")
class VoteController {
    val voteService = VoteService()


    @PostMapping("/create_vote", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createVote(@RequestPart voteDTO: VoteDTO): String {
        return voteDTO.questionList[0].question
    }
}