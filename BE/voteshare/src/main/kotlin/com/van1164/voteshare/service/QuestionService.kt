package com.van1164.voteshare.service

import com.van1164.voteshare.domain.Question
import com.van1164.voteshare.domain.Vote
import com.van1164.voteshare.repository.QuestionRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class QuestionService(
    val questionRepository: QuestionRepository,
    val s3Service: S3Service
) : BaseService() {


    private fun createQuestion(questionTitle: String, imageUrl: String?, vote: Vote) {
        tx.begin()
        val question = Question(question = questionTitle)
        question.voteSet(vote)
        questionRepository.save(question)
        tx.commit()
    }

    @Transactional
    suspend fun createQuestionList(questionList: List<String>, questionImageList: List<MultipartFile?>, vote: Vote) {
        val imageUrls = s3Service.uploadMultipleImages(questionImageList)
        questionList.forEachIndexed {index,it ->
            createQuestion(it, imageUrls[index], vote)
        }
    }

}