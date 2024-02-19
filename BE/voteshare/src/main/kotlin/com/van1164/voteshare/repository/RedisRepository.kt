package com.van1164.voteshare.repository

import com.fasterxml.jackson.databind.ObjectMapper
import com.van1164.voteshare.config.redis.RedisConfig
import com.van1164.voteshare.domain.RedisUserData
import com.van1164.voteshare.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisRepository(val redisTemplate: RedisTemplate<String, String>, val mapper:ObjectMapper) {

    //val redisTemplate by lazy { RedisConfig().redisTemplate() }

    fun save(jwt: String, user: User) {
        val redisUserData = RedisUserData(user.id, user.nickName, user.email)
        redisTemplate.opsForValue().set(jwt, mapper.writeValueAsString(redisUserData))
    }

    fun loadByJwt(jwt: String): RedisUserData? {
        return mapper.readValue(redisTemplate.opsForValue().get(jwt),RedisUserData::class.java)
    }

}