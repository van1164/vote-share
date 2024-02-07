package com.van1164.voteshare.repository

import com.van1164.voteshare.EntityManagerObject
import com.van1164.voteshare.domain.User
import com.van1164.voteshare.domain.Vote
import jakarta.persistence.NoResultException
import jakarta.persistence.NonUniqueResultException
import org.springframework.stereotype.Repository

@Repository
class UserRepository {
    val em = EntityManagerObject.em

    fun save(user: User){
        println("유저저장")
        em.persist(user)
        println("유저저장 완료")
    }

    fun loadUserById(id: Long): User? {
        return em.find(User::class.java,id)
    }

    fun loadUserByEmail(email: String): User? {
        val userJpql = "select distinct u from User u where u.email =: email"
        return try {
            println("사용자 찾는중")
            val userDetail = em.createQuery(userJpql,User::class.java).setParameter("email",email).singleResult
            println(userDetail)
            userDetail
        }
        catch (e : NoResultException){
            println("결과 없음")
            null
        }
        catch (e : NonUniqueResultException){
            null
        }

    }

    fun addVote(user: User, vote: Vote) {
        user.voteList.add(vote)
        em.persist(user)
    }

    fun update(user: User, accessToken: String) {
        user.accessToken = accessToken
    }
}
