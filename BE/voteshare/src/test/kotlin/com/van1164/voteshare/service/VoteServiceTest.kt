package com.van1164.voteshare.service

import com.van1164.voteshare.dto.VoteDTO
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class VoteServiceTest {
    val service = VoteService()
    val userService = UserService()
    @Test
    fun createVoteTest(){
        val testUser = userService.createUser("TEST1")
        val voteDTO = VoteDTO(testUser,"testTitle","testSub")
        service.createVote(voteDTO)
    }

}