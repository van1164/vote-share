package com.van1164.voteshare.repository

import com.van1164.voteshare.EntityManagerObject
import com.van1164.voteshare.domain.Vote
import org.springframework.stereotype.Repository

@Repository
class VoteRepository {
    val em = EntityManagerObject.em

    fun save(vote : Vote){
        em.persist(vote)
    }

    fun loadPopularVote(): MutableList<Vote> {
        val jpql = "select v.title, v.mainImageUrl, v.voteUrl from Vote v where v.publicShare =true  order by allVoteSum DESC"
        val voteList = em.createQuery(jpql,Vote::class.java).resultList
        return if (voteList.size<5){
            voteList
        }
        else{
            voteList.subList(0,5)
        }
    }
}