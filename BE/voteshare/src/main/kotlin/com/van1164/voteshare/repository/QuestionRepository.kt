package com.van1164.voteshare.repository

import com.van1164.voteshare.EntityManagerObject
import com.van1164.voteshare.domain.Question
import org.springframework.stereotype.Repository

@Repository
class QuestionRepository {
    val em = EntityManagerObject.em

    fun save(question: Question) {
        em.persist(question)
    }
}