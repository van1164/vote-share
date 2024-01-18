package com.van1164.voteshare

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@PropertySource(value = ["classpath:application.properties"])
class VoteshareApplication
fun main(args: Array<String>) {
	runApplication<VoteshareApplication>(*args)
}
