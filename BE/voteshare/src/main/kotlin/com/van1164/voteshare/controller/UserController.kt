package com.van1164.voteshare.controller

import com.van1164.voteshare.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping(value = ["/user"])
class UserController(val userService: UserService) {

    @GetMapping("/loginPage")
    fun loginPage() : String{
        return "loginPage"
    }

    @GetMapping("/login")
    fun createUser(): String {
        return "TEST"
    }

}