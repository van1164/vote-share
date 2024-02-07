package com.van1164.voteshare.controller


import com.van1164.voteshare.JwtTokenProvider
import com.van1164.voteshare.service.RedisService
import com.van1164.voteshare.service.UserService
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest @Autowired constructor(
    var redisService: RedisService,
    var userService: UserService
) {
    @Autowired
    lateinit var mockMvc: MockMvc

    val testEmail = "test@test.com"
    val testJwt = JwtTokenProvider().createToken("test@test.com")
    val testName = "testName"
    @BeforeEach
    fun setUp(){
        redisService.save(testJwt.accessToken, testEmail)
        userService.save(testName, testEmail, testJwt.accessToken)
    }


    @Test
    @WithMockUser()
    fun getMyPage() {
        mockMvc.perform (
            get("/api/v1/user/mypage")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",testJwt.accessToken+" "+testJwt.accessToken)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.email").value(testEmail))
            .andExpect(jsonPath("$.accessToken").value(testJwt))
            .andExpect(jsonPath("$.nickName").value(testName))

    }


}