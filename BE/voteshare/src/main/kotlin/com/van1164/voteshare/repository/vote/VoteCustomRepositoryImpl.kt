package com.van1164.voteshare.repository.vote

import com.querydsl.core.types.Projections
import com.van1164.voteshare.domain.QVote.vote
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

    override fun votting(questionId: Long) {
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
        val voteDetailList= queryFactory.select(
            Projections.constructor(
                VoteDetailDTO::class.java,
                vote.title,
                vote.subTitle,
                vote.publicShare,
                vote.maxSelectItem,
                vote.allVoteSum,
                vote.updatedDate,
                vote.mainImageUrl,
                vote.voteUrl
            )
        ).from(vote)
            .where(vote.id.`in`(popularVoteIdList))
            .orderBy(vote.allVoteSum.desc())
            .fetch()
//        val jpql =
//            "select new com.van1164.voteshare.dto.VoteDetailDTO(v.title,v.subTitle,v.publicShare,v.maxSelectItem,v.allVoteSum,v.updatedDate,v.mainImageUrl,v.voteUrl) from Vote v where v.id in (:idList) order by v.allVoteSum desc "
//        return em.createQuery(jpql, VoteDetailDTO::class.java).setParameter("idList", popularVoteIdList).resultList
        return voteDetailList
    }

    override fun loadVoteById(voteId: Long): Vote {
        return em.find(Vote::class.java, voteId)
    }

    override fun plusVoteSum(vote: Vote) {
        vote.allVoteSum = vote.allVoteSum + 1
    }

    override fun newLoadPopularVote(): MutableList<PopularVoteResponseDTO> {
        val voteList = queryFactory.select(
            Projections.constructor(
                PopularVoteResponseDTO::class.java,
                vote.title,
                vote.voteUrl,
                vote.id,
                vote.mainImageUrl,
                vote.allVoteSum
            )
        ).from(vote)
            .where(vote.publicShare.isTrue)
            .orderBy(
                vote.allVoteSum.desc()
            )
            .limit(5)
            .fetch()
        return voteList
    }
}