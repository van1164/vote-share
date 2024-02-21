package com.van1164.voteshare.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.van1164.voteshare.JwtTokenProvider
import com.van1164.voteshare.domain.TokenInfo
import com.van1164.voteshare.dto.UserVoteDTO
import com.van1164.voteshare.repository.RedisRepository
import com.van1164.voteshare.service.RedisService
import com.van1164.voteshare.service.UserService
import com.van1164.voteshare.service.VoteService
import org.aspectj.lang.annotation.Before
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.event.annotation.BeforeTestClass
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
    var voteService: VoteService,
    private final val jwtTokenProvider: JwtTokenProvider,
    val mapper : ObjectMapper
) {
    @Autowired
    lateinit var mockMvc: MockMvc


    val fileInputStream = FileInputStream("src/test/resources/test_image.png")
    val fileInputStream2 = FileInputStream("src/test/resources/test_image.png")
    val fileInputStream3 = FileInputStream("src/test/resources/test_image.png")
    val fileInputStream4 = FileInputStream("src/test/resources/test_image.png")
    val fileInputStream5 = FileInputStream("src/test/resources/test_image.png")



    @Test
    @WithMockUser()
    @DisplayName("vote detail 불러오는 과정")
    fun loadVoteDetail() {
        val testImage = MockMultipartFile("mainImage", "0", "png", fileInputStream)
        val testImages = MockMultipartFile("imageFiles", "1", "png", fileInputStream2)
        val testImages2 = MockMultipartFile("imageFiles", "null", "png", fileInputStream3)
        val testImages4 = MockMultipartFile("imageFiles", "null", "png", fileInputStream5)
        val testImages3 = MockMultipartFile("imageFiles", "3", "png", fileInputStream4)
        val voteDTO = MockMultipartFile(
            "data",
            "",
            "application/json",
            "{ \"title\": \"test\", \"subTitle\": \"test\", \"publicShare\": true , \"maxSelectItem\": 3 , \"questionList\": [\"sdf\",\"sdfsf\",\"sdfsf\",\"sdfsf\"]}".toByteArray()
        )

        val mvcResult = mockMvc.perform(
            multipart("/api/v1/vote/create_vote")
                .file(testImage)
                .file(testImages2)
                .file(testImages3)
                .file(testImages)
                .file(testImages4)
                .file(voteDTO)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Authorization", testJwt.grantType + " " + testJwt.accessToken)
        ).andExpect(status().isOk)
            .andExpect(request().asyncStarted())
            .andExpect { request().asyncResult("body") }
            .andReturn()

        var url : String? = null

        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk)
            .andDo {
                println("SSSSSSSSSSSSSSSs")
                println(it.response.toString())
                println(it.response.contentAsString)
                println("XXXXXXXXXXXXXXXXX")
                val gson = Gson()
                val type = object : TypeToken<Map<String?,String?>?>() {}.type
                url = gson.fromJson<Map<String,String>>(it.response.contentAsString,type)["voteUrl"]
            }
            .andExpect(jsonPath("$.voteUrl").isString)

        assert(url != null)

        mockMvc.perform (
            get("/api/v1/vote/vote_detail/$url")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",testJwt.accessToken+" "+testJwt.accessToken)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.vote.title").value("test"))
            .andDo{
                println(it.response.contentAsString)
            }
    }

    @Test
    @WithMockUser
    @DisplayName("사용자가 투표하는 과정")
    fun userVoting(){
        val testImage = MockMultipartFile("mainImage", "0", "png", fileInputStream)
        val testImages = MockMultipartFile("imageFiles", "null", "png", fileInputStream2)
        val testImages2 = MockMultipartFile("imageFiles", "null", "png", fileInputStream3)
        val testImages3 = MockMultipartFile("imageFiles", "null", "png", fileInputStream4)
        val voteDTO = MockMultipartFile(
            "data",
            "",
            "application/json",
            "{ \"title\": \"test\", \"subTitle\": \"test\", \"publicShare\": true , \"maxSelectItem\": 3 , \"questionList\": [\"sdf\",\"sdfsf\",\"sdfsf\"]}".toByteArray()
        )

        val mvcResult = mockMvc.perform(
            multipart("/api/v1/vote/create_vote")
                .file(testImage)
                .file(testImages)
                .file(testImages2)
                .file(testImages3)
                .file(voteDTO)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Authorization", testJwt.grantType + " " + testJwt.accessToken)
        ).andExpect(status().isOk)
            .andExpect(request().asyncStarted())
            .andExpect { request().asyncResult("body") }
            .andReturn()
        var url: String? =null
        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk)
            .andDo {
                println("SSSSSSSSSSSSSSSs")
                println(it.response.toString())
                println(it.response.contentAsString)
                println("XXXXXXXXXXXXXXXXX")
            }
            .andExpect(jsonPath("$.voteUrl").isString)
            .andDo{
                url = it.response.contentAsString.split(":")[1].split("\"")[1]
            }

        println(url)

        assert(url != null)

        mockMvc.perform (
            get("/api/v1/vote/vote_detail/$url")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",testJwt.accessToken+" "+testJwt.accessToken)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.vote.title").value("test"))
            .andDo{
                println(it.response.contentAsString)
            }
        val content = mapper.writeValueAsString(UserVoteDTO(1L,listOf(1L)))
        mockMvc.perform (
            post("/api/v1/vote/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",testJwt.accessToken+" "+testJwt.accessToken)
                .content(content)
        ).andExpect(status().isOk)
            .andDo{
                println(it.response.contentAsString)
            }

        mockMvc.perform (
            get("/api/v1/vote/vote_detail/$url")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",testJwt.accessToken+" "+testJwt.accessToken)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.vote.title").value("test"))
            .andExpect(jsonPath("$.userVote.voteId").value(1L))
            .andExpect(jsonPath("$.userVote.questionId").value(1L))
            .andDo{
                println(it.response.contentAsString)
            }
    }


    @Test
    @WithMockUser
    @DisplayName("디테일 조회시 사용자가 투표하지 않은 경우")
    fun userVotingWithNoVote(){
        val testImage = MockMultipartFile("mainImage", "0", "png", fileInputStream)
        val testImages = MockMultipartFile("imageFiles", "null", "png", fileInputStream2)
        val testImages2 = MockMultipartFile("imageFiles", "null", "png", fileInputStream3)
        val voteDTO = MockMultipartFile(
            "data",
            "",
            "application/json",
            "{ \"title\": \"test\", \"subTitle\": \"test\", \"publicShare\": true , \"maxSelectItem\": 3 , \"questionList\": [\"sdf\",\"sdfsf\"]}".toByteArray()
        )
        var url:String? = null
        val mvcResult = mockMvc.perform(
            multipart("/api/v1/vote/create_vote")
                .file(testImage)
                .file(testImages)
                .file(testImages2)
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
            .andDo{
                url = it.response.contentAsString.split(":")[1].split("\"")[1]
            }

        mockMvc.perform (
            get("/api/v1/vote/vote_detail/$url")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",testJwt.accessToken+" "+testJwt.accessToken)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.vote.title").value("test"))
            .andExpect(jsonPath("$.userVote").value("null"))
            .andDo{
                println(it.response.contentAsString)
            }

    }

    @Test
    @WithMockUser()
    @DisplayName("vote 생성 과정")
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

        val testImage = MockMultipartFile("mainImage", "0", "png", fileInputStream)
        val testImages = MockMultipartFile("imageFiles", "null", "png", fileInputStream2)
        val testImages2 = MockMultipartFile("imageFiles", "null", "png", fileInputStream3)
        val voteDTO = MockMultipartFile(
            "data",
            "",
            "application/json",
            "{ \"title\": \"test\", \"subTitle\": \"test\", \"publicShare\": true , \"maxSelectItem\": 3 , \"questionList\": [\"sdf\",\"sdfsf\"]}".toByteArray()
        )
        var url:String? = null
        val mvcResult = mockMvc.perform(
            multipart("/api/v1/vote/create_vote")
                .file(testImage)
                .file(testImages)
                .file(testImages2)
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
            .andDo{
                url = it.response.contentAsString.split(":")[1].split("\"")[1]
            }

        mockMvc.perform (
            get("/api/v1/user/mypage")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",testJwt.accessToken+" "+testJwt.accessToken)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.user.email").value(testEmail))
            .andExpect(jsonPath("$.user.nickName").value(testName))
            .andDo{
                Thread.sleep(30000)
            }

        mockMvc.perform (
            get("/api/v1/main_page")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",testJwt.accessToken+" "+testJwt.accessToken)
        ).andExpect(status().isOk)
            .andDo{
                println(it.response.contentAsString)
            }

    }

    companion object SetUpClass{

        const val testEmail = "test@test.com"
        lateinit var testJwt: TokenInfo
        const val testName = "testName"
        @JvmStatic
        @BeforeAll
        fun setUp(
            @Autowired
            jwtTokenProvider:JwtTokenProvider,
            @Autowired
            userService: UserService,
            @Autowired
            redisService :RedisService
        ) {
            testJwt = jwtTokenProvider.createToken("test@test.com")
            println(testJwt.accessToken)
            val testUser=  userService.save(
                testName,
                testEmail,
                testJwt.accessToken
            )
            redisService.save(testJwt.accessToken, testUser)
        }
    }

}