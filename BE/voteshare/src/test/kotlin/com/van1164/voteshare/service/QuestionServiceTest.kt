package com.van1164.voteshare.service

import com.van1164.voteshare.dto.VoteDTO
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class QuestionServiceTest {
    val userService = UserService()
    val voteService = VoteService()
    val questionService = QuestionService()

    @Test
    fun createQuestionTest(){
        val testUser = userService.createUser("TEST1")
        val voteDTO = VoteDTO(testUser,"testTitle","testSub")
        val vote = voteService.createVote(voteDTO)

        questionService.createQuestion("첫번쨰 질문입니다.",vote)
    }
}