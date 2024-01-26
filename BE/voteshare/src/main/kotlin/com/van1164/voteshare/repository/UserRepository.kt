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
        val jpql = "select u from User u where u.email =: email"
        return try {
            em.createQuery(jpql,User::class.java).setParameter("email",email).singleResult
        }
        catch (e : NoResultException){
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
}
