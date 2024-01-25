package com.van1164.voteshare.service

import com.van1164.voteshare.domain.Question
import com.van1164.voteshare.domain.Vote
import com.van1164.voteshare.dto.QuestionDTO
import com.van1164.voteshare.repository.QuestionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class QuestionService(
        val questionRepository : QuestionRepository,
        val s3Service: S3Service
) : BaseService() {


    private fun createQuestion(questionTitle : String, imageUrl : String, vote: Vote){
        tx.begin()
        val question = Question(question = questionTitle)
        question.voteSet(vote)
        questionRepository.save(question)
        tx.commit()
    }

    @Transactional
    fun createQuestionList(questionList : List<QuestionDTO>,vote :Vote) {
        questionList.forEach{
            val imageUrl = s3Service.uploadImage(it.image)
            createQuestion(it.question,imageUrl,vote)
        }
    }

}