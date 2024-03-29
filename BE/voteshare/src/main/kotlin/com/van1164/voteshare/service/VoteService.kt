package com.van1164.voteshare.service

import com.van1164.voteshare.domain.User
import com.van1164.voteshare.domain.UserVote
import com.van1164.voteshare.domain.Vote
import com.van1164.voteshare.dto.VoteDTO
import com.van1164.voteshare.dto.VoteDetailDTO
import com.van1164.voteshare.repository.PopularVoteRepository
import com.van1164.voteshare.repository.UserVoteRepository
import com.van1164.voteshare.repository.vote.VoteRepository
import com.van1164.voteshare.util.ServiceUtil
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import kotlin.collections.HashMap


@Service
@Transactional
class VoteService(
    val voteRepository: VoteRepository,
    val questionService: QuestionService,
    val s3Service: S3Service,
    val userVoteRepository: UserVoteRepository,
    @Value("\${aws.s3.bucketUrl}")
    val bucketUrl : String,
    val popularVoteRepository: PopularVoteRepository
) :
    BaseService() {

    @Transactional
    suspend fun createVote(voteDTO: VoteDTO, mainImage: MultipartFile?, images: List<MultipartFile>, user: User): Vote {
        val vote = voteDtoToVote(mainImage, voteDTO, user)

        voteRepository.addVote(user,vote)
        voteRepository.save(vote)
        questionService.createQuestionList(voteDTO.questionList, images, vote)
        return vote
    }

    private fun voteDtoToVote(
        mainImage: MultipartFile?,
        voteDTO: VoteDTO,
        user: User
    ): Vote {
        val profileImageUrl = mainImage?.let { bucketUrl+s3Service.uploadImage(it) }
        val voteUrl = ServiceUtil.createUUID()
        val title = voteDTO.title
        val subTitle = voteDTO.subTitle
        val createDate = ServiceUtil.dateTimeNow()
        val updatedDate = ServiceUtil.dateTimeNow()
        return Vote(
            title,
            subTitle,
            voteUrl,
            createDate,
            updatedDate,
            user,
            maxSelectItem = voteDTO.maxSelectItem,
            mainImageUrl = profileImageUrl,
            publicShare = voteDTO.publicShare,
            passWord = voteDTO.passWord
        )
    }
    @Transactional
    fun loadMainPageData(): ResponseEntity<Any> {

        val popularVoteList = voteRepository.loadVoteListById(popularVoteRepository.getPopularVoteIdList())
        val response = HashMap<String, Any>().apply{
            put("popularVoteList",popularVoteList)
        }
        return ResponseEntity(response, HttpStatus.OK)
    }
    @Transactional
    fun userVote(voteId: Long, questionIdList: List<Long>,userId:Long) {
        val vote = voteRepository.loadVoteById(voteId)
        voteRepository.plusVoteSum(vote)
        questionIdList.forEach {
            voteRepository.votting(it)
            userVoteRepository.save(UserVote(userId,it,voteId))
        }
    }
    @Transactional
    fun voteDetail(voteUrl: String): ResponseEntity<Any> {
        return try{
            val vote = voteRepository.loadVoteDetailByVoteUrl(voteUrl)
            val response = HashMap<String,Any>().apply {
                put("vote",vote)
                put("questionList",voteRepository.loadQuestionList(voteUrl))
                put("userVote",userVoteRepository.loadUserVoteByVoteId(vote.id,vote.user.id))
            }
            ResponseEntity(response,HttpStatus.OK)
        } catch (e : Exception ){
            ResponseEntity(HttpStatus.BAD_REQUEST)
        }

    }
    @Transactional
    fun loadVoteListByUserId(id: Long): MutableList<VoteDetailDTO> {
        return voteRepository.loadVoteListByUserId(id)
    }

}