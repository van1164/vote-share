package com.van1164.voteshare.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "PopularVote")
data class PopularVote(
    @Id
    val voteId : Long?
)