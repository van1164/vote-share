package com.van1164.voteshare.config.redis

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisServer
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.io.IOException


@Configuration(value = "redisConfig")
@EnableRedisRepositories
@RequiredArgsConstructor
class RedisConfig {

    @Value("\${spring.data.redis.host}")
    lateinit var host : String

    @Value("\${spring.data.redis.port}")
    var port : Int = 6379


    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory? {
        val lettuceConnectionFactory = LettuceConnectionFactory(host, port)
        lettuceConnectionFactory.start()
        return lettuceConnectionFactory
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, String> {
        val redisTemplate = RedisTemplate<String, String>()
        redisTemplate.connectionFactory = redisConnectionFactory()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()
        redisTemplate.afterPropertiesSet()
        println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
        return redisTemplate
    }
}