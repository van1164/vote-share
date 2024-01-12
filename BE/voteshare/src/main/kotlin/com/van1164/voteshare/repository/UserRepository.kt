package com.van1164.voteshare.repository

import com.van1164.voteshare.EntityManagerObject
import com.van1164.voteshare.domain.User
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
}
