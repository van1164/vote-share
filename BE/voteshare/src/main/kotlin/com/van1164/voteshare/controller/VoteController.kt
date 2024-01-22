package com.van1164.voteshare.controller

import com.van1164.voteshare.dto.VoteDTO
import com.van1164.voteshare.service.S3Service
import com.van1164.voteshare.service.VoteService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/vote")
class VoteController(val voteService: VoteService, val s3Service: S3Service) {

    @PostMapping("/create_vote", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    suspend fun createVote(@RequestPart voteDTO: VoteDTO): String {
        val images = voteDTO.questionList.map{it.image}
        s3Service.uploadMultipleImages(images)
        return voteDTO.questionList[0].question
    }
}