package com.van1164.voteshare.dto

import com.van1164.voteshare.domain.Question
import com.van1164.voteshare.domain.User
import jakarta.persistence.*
import java.util.*

data class PopularVoteResponseDTO(

    val title : String,

    val voteUrl : String,

    val user: User,

    val id : Long? = null,

    val mainImageUrl : String? =null,

    val allVoteSum : Int = 0,

    val publicShare : Boolean = true,

    )