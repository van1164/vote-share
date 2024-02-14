package com.van1164.voteshare.service

import com.van1164.voteshare.domain.Question
import com.van1164.voteshare.domain.Vote
import com.van1164.voteshare.repository.QuestionRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class QuestionService(
    val questionRepository: QuestionRepository,
    val s3Service: S3Service,
    @Value("\${aws.s3.bucketUrl}")
    val bucketUrl : String
) : BaseService() {

    @Transactional
    fun createQuestion(questionTitle: String, imageUrl: String?, vote: Vote) {
        val question = Question(question = questionTitle, voteImageUrl = bucketUrl + imageUrl)
        question.voteSet(vote)
        questionRepository.save(question)
    }

    @Transactional
    suspend fun createQuestionList(questionList: List<String>, questionImageList: List<MultipartFile>, vote: Vote) {
        val imageUrls = s3Service.uploadMultipleImages(questionImageList)
        val existImageIndex =  questionImageList.filter{
            it.originalFilename!! != "null"
        }.map{it.originalFilename!!.toInt()}

        questionList.forEachIndexed {index,it ->
            if (index in existImageIndex){
                createQuestion(it, imageUrls[existImageIndex.indexOf(index)], vote)
            }
            else{
                createQuestion(it, null, vote)
            }

        }
    }

}