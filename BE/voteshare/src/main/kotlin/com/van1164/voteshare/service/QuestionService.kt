package com.van1164.voteshare.service

import com.van1164.voteshare.domain.Question
import com.van1164.voteshare.domain.Vote
import com.van1164.voteshare.repository.QuestionRepository
import org.springframework.stereotype.Service

@Service
class QuestionService : BaseService() {
    val questionRepository = QuestionRepository()

    fun createQuestion(questionTitle : String, vote: Vote){
        tx.begin()
        val question = Question(question = questionTitle,vote = vote)
        questionRepository.save(question)
        tx.commit()
    }

}