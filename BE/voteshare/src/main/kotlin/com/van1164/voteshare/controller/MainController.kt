package com.van1164.voteshare.controller

import com.van1164.voteshare.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
class MainController {

    @GetMapping("/loginPage")
    fun loginPage() : String{
        return "loginPage"
    }

    @GetMapping("/")
    fun mainPage() : String{
        return "mainPage"
    }

}