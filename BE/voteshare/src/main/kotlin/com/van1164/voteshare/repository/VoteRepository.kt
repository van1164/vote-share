package com.van1164.voteshare.repository

import com.van1164.voteshare.EntityManagerObject
import com.van1164.voteshare.domain.Vote
import com.van1164.voteshare.dto.PopularVoteResponseDTO
import org.springframework.stereotype.Repository

@Repository
class VoteRepository {
    val em = EntityManagerObject.em

    fun save(vote : Vote){
        em.persist(vote)
    }

    fun loadPopularVote(): MutableList<PopularVoteResponseDTO> {
        val jpql = "select PopularVoteResponseDTO(v.title,v.voteUrl, v.id,v.mainImageUrl,v.allVoteSum)  from Vote v where v.publicShare =true  order by allVoteSum DESC"
        val voteList = em.createQuery(jpql,PopularVoteResponseDTO::class.java).resultList
        return if (voteList.size<5){
            voteList
        }
        else{
            voteList.subList(0,5)
        }
    }
}