package com.van1164.voteshare.repository

import com.van1164.voteshare.EntityManagerObject
import com.van1164.voteshare.data.Vote
import org.springframework.stereotype.Repository

@Repository
class VoteRepository {
    val em = EntityManagerObject.em

    fun save(vote : Vote){
        em.persist(vote)
    }
}