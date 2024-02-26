package com.van1164.voteshare

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@PropertySource(value = ["classpath:application.properties"])
class VoteshareApplication
fun main(args: Array<String>) {
	runApplication<VoteshareApplication>(*args)
}
