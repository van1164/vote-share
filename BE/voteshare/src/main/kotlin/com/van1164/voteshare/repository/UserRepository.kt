package com.van1164.voteshare.repository

import com.van1164.voteshare.EntityManagerObject
import com.van1164.voteshare.data.User
import org.springframework.stereotype.Repository

@Repository
class UserRepository {
    val em = EntityManagerObject.em

    fun save(user: User){
        em.persist(user)
    }

    fun loadUserById(id: Long): User? {
        return em.find(User::class.java,id)
    }
}
