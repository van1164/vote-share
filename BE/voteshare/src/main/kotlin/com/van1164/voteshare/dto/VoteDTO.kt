package com.van1164.voteshare.dto

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile


data class VoteDTO(
    @Parameter(name = "투표 제목", description = "투표 제목", example = "투표 제목", required = true)
    val title: String,
    val subTitle: String,
    val publicShare: Boolean,
    val questionList: MutableList<String>
)