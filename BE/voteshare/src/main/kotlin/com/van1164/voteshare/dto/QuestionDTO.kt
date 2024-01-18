package com.van1164.voteshare.dto

import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile

data class QuestionDTO(
    @RequestPart val image : MultipartFile,
    val question : String,
)
