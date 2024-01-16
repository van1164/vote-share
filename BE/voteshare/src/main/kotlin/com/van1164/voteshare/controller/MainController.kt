package com.van1164.voteshare.controller

import com.van1164.voteshare.service.UserService
import com.van1164.voteshare.service.VoteService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
@RequestMapping("/api")
class MainController {
    val voteService = VoteService()

    @GetMapping("/main_page")
    fun mainPage() : ResponseEntity<Any> {

        return voteService.loadMainPageData()
    }

}