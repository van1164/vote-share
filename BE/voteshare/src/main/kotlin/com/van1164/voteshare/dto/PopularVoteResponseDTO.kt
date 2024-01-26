package com.van1164.voteshare.dto

import com.van1164.voteshare.domain.Question
import com.van1164.voteshare.domain.User
import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import java.util.*

@NoArgsConstructor
@AllArgsConstructor
class PopularVoteResponseDTO constructor(

    public val title : String,

    public val voteUrl : String,

    public val id : Long? = null,

    public val mainImageUrl : String? =null,

    public val allVoteSum : Int = 0,
    )