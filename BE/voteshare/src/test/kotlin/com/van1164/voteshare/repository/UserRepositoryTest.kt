package com.van1164.voteshare.repository

import com.van1164.voteshare.EntityManagerObject.em
import com.van1164.voteshare.JwtTokenProvider
import com.van1164.voteshare.domain.User
import com.van1164.voteshare.service.RedisService
import com.van1164.voteshare.service.UserService
import com.van1164.voteshare.service.VoteService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureMockMvc
class UserRepositoryTest @Autowired constructor(
    val userRepository: UserRepository,
    val redisService: RedisService,
    val userService: UserService
){
    val testEmail = "test@test.com"
    val testJwt = JwtTokenProvider().createToken("test@test.com")
    val testName = "testName"
    @BeforeEach
    fun setUp(){
        redisService.save(testJwt.accessToken, testEmail)
        userService.save(testName, testEmail, testJwt.accessToken)
    }

    @Test
    fun loadByEmail(){
        val jpql = "select u from User u join fetch u.voteList"
            println("사용자 찾는중")
            val result= em.createQuery(jpql, User::class.java).singleResult
    }

}