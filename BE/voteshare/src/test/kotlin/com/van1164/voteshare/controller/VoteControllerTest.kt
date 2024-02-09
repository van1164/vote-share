package com.van1164.voteshare.controller

import com.van1164.voteshare.JwtTokenProvider
import com.van1164.voteshare.service.RedisService
import com.van1164.voteshare.service.UserService
import com.van1164.voteshare.service.VoteService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.*
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.io.FileInputStream


@SpringBootTest
@AutoConfigureMockMvc
class VoteControllerTest @Autowired constructor(
    var redisService: RedisService,
    var userService: UserService,
    var voteService: VoteService
) {
    @Autowired
    lateinit var mockMvc: MockMvc

    val testEmail = "test@test.com"
    val testJwt = JwtTokenProvider().createToken("test@test.com")
    val testName = "testName"
    val fileInputStream = FileInputStream("src/test/resources/test_image.png")
    val fileInputStream2 = FileInputStream("src/test/resources/test_image.png")

    @BeforeEach
    fun setUp() {
        redisService.save(testJwt.accessToken, testEmail)
        userService.save(testName, testEmail, testJwt.accessToken)
    }


    @Test
    @WithMockUser()
    fun createVote() {
        println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxxx")
        println(userService.loadUserByEmail(testEmail))
        println("YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY")
        mockMvc.perform(
            get("/api/v1/user/mypage")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", testJwt.grantType + " " + testJwt.accessToken)

        ).andExpect(
            jsonPath("$.user.email").value(testEmail)
        )

        val testImage = MockMultipartFile("mainImage", "1", "png", fileInputStream)
        val testImages = MockMultipartFile("imageFiles", "2", "png", fileInputStream2)
        val voteDTO = MockMultipartFile(
            "data",
            "",
            "application/json",
            "{ \"title\": \"test\", \"subTitle\": \"test\", \"publicShare\": true , \"maxSelectItem\": 3 , \"questionList\": [\"sdf\",\"sdfsf\"]}".toByteArray()
        )

        val mvcResult = mockMvc.perform(
            multipart("/api/v1/vote/create_vote")
                .file(testImage)
                .file(testImages)
                .file(voteDTO)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Authorization", testJwt.grantType + " " + testJwt.accessToken)
            ).andExpect(status().isOk)
            .andExpect(request().asyncStarted())
            .andExpect { request().asyncResult("body") }
            .andReturn()


        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk)
            .andDo {
                println("SSSSSSSSSSSSSSSs")
                println(it.response.toString())
                println(it.response.contentAsString)
                println("XXXXXXXXXXXXXXXXX")
            }
            .andExpect(jsonPath("$.voteUrl").isString)

        mockMvc.perform (
            get("/api/v1/user/mypage")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",testJwt.accessToken+" "+testJwt.accessToken)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.user.email").value(testEmail))
            .andExpect(jsonPath("$.user.accessToken").value(testJwt.accessToken))
            .andExpect(jsonPath("$.user.nickName").value(testName))
    }

}