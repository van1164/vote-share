package com.van1164.voteshare.controller

import com.van1164.voteshare.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.Mapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/user"])
class UserController {

    val userService = UserService()
    @PostMapping("/")
    fun createUser(){
    }

}