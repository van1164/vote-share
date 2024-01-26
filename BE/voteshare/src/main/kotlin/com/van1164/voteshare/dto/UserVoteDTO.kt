package com.van1164.voteshare.dto

data class UserVoteDTO(
    val voteId : Long,
    val questionIdList : List<Long>,
)
