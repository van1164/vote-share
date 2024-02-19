package com.van1164.voteshare.repository

import com.van1164.voteshare.domain.PopularVote
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository


@Repository
class PopularVoteRepository: BaseRepository() {

    @Transactional
    fun resetAndSave(popularVoteList: List<Long?>){
        val resetJpql = "delete from PopularVote"
        em.apply{
            createQuery(resetJpql).executeUpdate()
            clear()
        }

        popularVoteList.forEach{
            em.persist(PopularVote(it))
        }

    }

    @Transactional
    fun getPopularVoteIdList(): List<Long?> {
        val findAllJpql = "select p from PopularVote p"
        return em.createQuery(findAllJpql, PopularVote::class.java).resultList.map{it.voteId}
    }

}