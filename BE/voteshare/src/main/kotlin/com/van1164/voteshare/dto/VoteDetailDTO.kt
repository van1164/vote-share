package com.van1164.voteshare.dto

import com.van1164.voteshare.domain.Question
import io.swagger.v3.oas.annotations.Parameter
import java.util.*

data class VoteDetailDTO constructor(
    val title: String,
    val subTitle: String,
    val publicShare: Boolean,
    val maxSelectItem : Int,
    val allVoteSum : Int,
    val updatedDate : Date,
    val mainImageUrl: String?,
    val voteUrl : String
)