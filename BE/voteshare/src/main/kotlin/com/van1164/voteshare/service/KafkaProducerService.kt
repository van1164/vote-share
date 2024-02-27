package com.van1164.voteshare.service

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducerService
    (
    val kafkaTemplate : KafkaTemplate<String,Any>
) {

    val testTopic = "testTopic"

    fun pub(msg : String){
        kafkaTemplate.send(testTopic,msg)
    }


}