package com.van1164.voteshare.service

import org.junit.jupiter.api.Test

import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest {
    val service = UserService()
    private val log = LoggerFactory.getLogger(javaClass)

    @Test
    fun createUser() {
        service.createUser("TEST")
    }

    @Test
    fun loadUser() {
        createUser()
        val user = service.loadUserById(1)
        assert(user?.nickName == "TEST")
    }
}
