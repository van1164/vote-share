package com.van1164.voteshare.repository

import com.van1164.voteshare.domain.Question
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
class QuestionRepository : BaseRepository() {
    @Transactional
    fun save(question: Question) {
        em.persist(question)
    }
}