package com.van1164.voteshare.repository.vote

import com.van1164.voteshare.domain.Question
import com.van1164.voteshare.domain.User
import com.van1164.voteshare.domain.Vote
import com.van1164.voteshare.dto.PopularVoteResponseDTO
import com.van1164.voteshare.dto.VoteDetailDTO
import com.van1164.voteshare.repository.BaseRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
class VoteCustomRepositoryImpl : VoteCustomRepository, BaseRepository() {

//    @Transactional
//    override fun save(vote: Vote) {
//        em.persist(vote)
//    }

    @Transactional
    override fun loadPopularVote(): MutableList<PopularVoteResponseDTO> {
        val jpql =
            "select new com.van1164.voteshare.dto.PopularVoteResponseDTO(v.title,v.voteUrl, v.id,v.mainImageUrl,v.allVoteSum) from Vote v where v.publicShare =true  order by allVoteSum DESC"
        val voteList = em.createQuery(jpql, PopularVoteResponseDTO::class.java).resultList
        return if (voteList.size < 5) {
            voteList
        } else {
            voteList.subList(0, 5)
        }
    }

    override fun vote(questionId: Long) {
        val question = em.find(Question::class.java, questionId)
        question.voteNum = question.voteNum + 1
    }

    override fun loadVoteDetailByVoteUrl(voteUrl: String): Vote {
        val jpql = "select v from Vote v where v.voteUrl =: voteUrl"
        return em.createQuery(jpql, Vote::class.java).setParameter("voteUrl", voteUrl).singleResult
    }

    override fun loadQuestionList(voteUrl: String): List<Question> {
        val jpql = "select q from Question q join q.vote v where v.voteUrl =: voteUrl"
        return em.createQuery(jpql, Question::class.java).setParameter("voteUrl", voteUrl).resultList
    }

    override fun loadVoteListByUserId(id: Long): MutableList<VoteDetailDTO> {
        val jpql =
            "select new com.van1164.voteshare.dto.VoteDetailDTO(v.title,v.subTitle,v.publicShare,v.maxSelectItem,v.allVoteSum,v.updatedDate,v.mainImageUrl,v.voteUrl) from Vote v join v.user u where u.id =: userId"
        return em.createQuery(jpql, VoteDetailDTO::class.java).setParameter("userId", id).resultList
    }

    @Transactional
    override fun addVote(user: User, vote: Vote) {
        user.voteList.add(vote)
        em.persist(user)
    }

    override fun loadVoteListById(popularVoteIdList: List<Long?>): Any {
        val jpql =
            "select new com.van1164.voteshare.dto.VoteDetailDTO(v.title,v.subTitle,v.publicShare,v.maxSelectItem,v.allVoteSum,v.updatedDate,v.mainImageUrl,v.voteUrl) from Vote v where v.id in (:idList) order by v.allVoteSum desc "
        return em.createQuery(jpql, VoteDetailDTO::class.java).setParameter("idList", popularVoteIdList).resultList
    }

    override fun loadVoteById(voteId: Long): Vote {
        return em.find(Vote::class.java, voteId)
    }

    override fun plusVoteSum(vote: Vote) {
        vote.allVoteSum = vote.allVoteSum + 1
    }
}