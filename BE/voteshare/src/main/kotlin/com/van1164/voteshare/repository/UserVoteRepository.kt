package com.van1164.voteshare.repository

import com.van1164.voteshare.domain.UserVote
import jakarta.persistence.NoResultException
import org.springframework.stereotype.Repository

@Repository
class UserVoteRepository : BaseRepository() {
    fun save(userVote: UserVote) {
        em.persist(userVote)
    }

    fun loadUserVoteByVoteId(voteId: Long?, userId: Long?): Any {
        val jpql = "select u from UserVote u where u.voteId = :voteId and u.userId = :userId"
        return try {
            em.createQuery(jpql, UserVote::class.java).setParameter("voteId", voteId)
                .setParameter("userId", userId).singleResult
        } catch (e: NoResultException){
            "null"
        }
    }
}