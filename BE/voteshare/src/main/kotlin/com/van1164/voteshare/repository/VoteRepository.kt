package com.van1164.voteshare.repository

import com.van1164.voteshare.domain.Question
import com.van1164.voteshare.domain.User
import com.van1164.voteshare.domain.Vote
import com.van1164.voteshare.dto.PopularVoteResponseDTO
import com.van1164.voteshare.dto.VoteDetailDTO
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
class VoteRepository : BaseRepository() {

    @Transactional
    fun save(vote : Vote){
        em.persist(vote)
    }

    @Transactional
    fun loadPopularVote(): MutableList<PopularVoteResponseDTO> {
        val jpql = "select new com.van1164.voteshare.dto.PopularVoteResponseDTO(v.title,v.voteUrl, v.id,v.mainImageUrl,v.allVoteSum) from Vote v where v.publicShare =true  order by allVoteSum DESC"
        val voteList = em.createQuery(jpql,PopularVoteResponseDTO::class.java).resultList
        return if (voteList.size<5){
            voteList
        }
        else{
            voteList.subList(0,5)
        }
    }

    fun vote(voteId: Long, questionId: Long) {
        val vote = em.find(Vote::class.java,voteId)
        vote.allVoteSum = vote.allVoteSum+1
        val question = em.find(Question::class.java,questionId)
        question.voteNum = question.voteNum +1
    }

    fun loadVoteDetailByVoteUrl(voteUrl: String): VoteDetailDTO {
        val jpql = "select new com.van1164.voteshare.dto.VoteDetailDTO(v.title,v.subTitle,v.publicShare,v.maxSelectItem,v.allVoteSum,v.updatedDate,v.mainImageUrl) from Vote v where v.voteUrl =: voteUrl"
        return em.createQuery(jpql, VoteDetailDTO::class.java).setParameter("voteUrl",voteUrl).singleResult
    }

    fun loadQuestionList(voteUrl: String): List<Question> {
        val jpql = "select q from Question q join q.vote v where v.voteUrl =: voteUrl"
        return em.createQuery(jpql, Question::class.java).setParameter("voteUrl",voteUrl).resultList
    }

    fun loadVoteListByUserId(id: Long): MutableList<VoteDetailDTO> {
        val jpql = "select new com.van1164.voteshare.dto.VoteDetailDTO(v.title,v.subTitle,v.publicShare,v.maxSelectItem,v.allVoteSum,v.updatedDate,v.mainImageUrl) from Vote v join v.user u where u.id =: userId"
        return em.createQuery(jpql, VoteDetailDTO::class.java).setParameter("userId",id).resultList
    }

    @Transactional
    fun addVote(user: User, vote: Vote) {
        user.voteList.add(vote)
        em.persist(user)
    }

    fun loadVoteListById(popularVoteIdList: List<Long?>): Any {
        val jpql = "select new com.van1164.voteshare.dto.VoteDetailDTO(v.title,v.subTitle,v.publicShare,v.maxSelectItem,v.allVoteSum,v.updatedDate,v.mainImageUrl) from Vote v where v.id in (:idList) order by v.allVoteSum desc "
        return em.createQuery(jpql,VoteDetailDTO::class.java).resultList
    }
}