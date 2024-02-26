package com.van1164.voteshare.controller

import com.van1164.voteshare.service.KafkaProducerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class KafkaTestController(
    val kafkaProducerService: KafkaProducerService
) {

    @GetMapping("/kafka")
    fun kafkaConsumerController(@RequestParam(name = "msg") msg : String){
        kafkaProducerService.pub(msg)
    }
}