package com.van1164.voteshare.controller

import com.van1164.voteshare.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/user"])
class UserController(val userService: UserService) {

    @GetMapping("/loginPage")
    fun loginPage() : String{
        return "loginPage"
    }

    @GetMapping("/login")
    fun createUser(): ResponseEntity<Any> {
        return ResponseEntity("로그인성공", HttpStatus.OK)
    }

}