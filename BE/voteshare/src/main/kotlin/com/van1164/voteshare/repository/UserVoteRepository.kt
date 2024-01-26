package com.van1164.voteshare.repository

import com.van1164.voteshare.EntityManagerObject
import com.van1164.voteshare.domain.UserVote
import org.springframework.stereotype.Repository

@Repository
class UserVoteRepository {
    val em = EntityManagerObject.em
    fun save(userVote : UserVote){
        em.persist(userVote)
    }
}