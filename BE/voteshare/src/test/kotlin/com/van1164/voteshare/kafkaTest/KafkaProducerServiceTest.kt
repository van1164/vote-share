package com.van1164.voteshare.kafkaTest

import com.van1164.voteshare.service.KafkaProducerService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class KafkaProducerServiceTest @Autowired constructor(
    val kafkaProducerService: KafkaProducerService
) {

    @Test
    fun pub() {
        kafkaProducerService.pub("testMSG")
    }
}