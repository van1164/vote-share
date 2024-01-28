package com.van1164.voteshare.controller

import com.van1164.voteshare.service.RedisService
import com.van1164.voteshare.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["api/v1/user"])
class UserController(val userService: UserService, val redisService: RedisService) {

    @GetMapping("/loginPage")
    fun loginPage(): String {
        return "loginPage"
    }

    @GetMapping("/login")
    fun createUser(): ResponseEntity<Any> {
        return ResponseEntity("로그인성공", HttpStatus.OK)
    }

    @GetMapping("/mypage")
    fun createUser(
        @RequestHeader(value = "Authorization") token: String
    ): ResponseEntity<Any> {
        val email =
            redisService.loadByJwt(token) ?: return ResponseEntity<Any>("Email Not Found", HttpStatus.BAD_REQUEST)
        userService.loadUserByEmail(email)
        val user =
            userService.loadUserByEmail(email) ?: return ResponseEntity<Any>("User Not Found", HttpStatus.BAD_REQUEST)

        return ResponseEntity(user, HttpStatus.OK)
    }


}