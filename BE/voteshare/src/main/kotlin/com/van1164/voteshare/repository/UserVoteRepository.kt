package com.van1164.voteshare.repository

import com.van1164.voteshare.domain.UserVote
import org.springframework.stereotype.Repository

@Repository
class UserVoteRepository : BaseRepository() {
    fun save(userVote : UserVote){
        em.persist(userVote)
    }
}