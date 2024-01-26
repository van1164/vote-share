package com.van1164.voteshare.domain

import jakarta.persistence.*
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor
@Table(name = "USERVOTE")
data class UserVote(

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "question_id")
    val questionId: Long,

    @Column(name = "vote_id")
    val voteId: Long,

    @Id
    @GeneratedValue
    val id: Long? = null,
)