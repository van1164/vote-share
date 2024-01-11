package com.van1164.voteshare.dto

import com.van1164.voteshare.data.User


data class VoteDTO(
        val user : User,
        val title: String,
        val subTitle : String,
        val allVoteSum :Int = 0
        )