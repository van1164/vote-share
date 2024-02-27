package com.van1164.voteshare.service

import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumerService {
    private val log = KotlinLogging.logger {  }
    @KafkaListener(topics= ["testTopic"], groupId = "kafkaTest")
    fun consumer(msg: String) {
        log.info { "KafkaConsumer: $msg" }
    }

    @KafkaListener(topics= ["vote"], groupId = "kafkaTest")
    fun voteConsumer(msg: String) {
        log.info { "KafkaConsumer TestStream: $msg" }
    }
}