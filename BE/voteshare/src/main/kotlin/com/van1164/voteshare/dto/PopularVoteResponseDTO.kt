package com.van1164.voteshare.dto

import com.van1164.voteshare.domain.Question
import com.van1164.voteshare.domain.User
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import java.util.*

@NoArgsConstructor
@AllArgsConstructor
data class PopularVoteResponseDTO(

    val title : String,

    val voteUrl : String,

    val id : Long? = null,

    val mainImageUrl : String? =null,

    val allVoteSum : Int = 0,

    )