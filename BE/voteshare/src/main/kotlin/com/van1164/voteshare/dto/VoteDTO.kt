package com.van1164.voteshare.dto

import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile


data class VoteDTO(
        val title: String,
        val subTitle : String,
        @RequestPart val mainImage : MultipartFile,
        val publicShare : Boolean,
        val questionList : MutableList<QuestionDTO>
        )