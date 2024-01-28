package com.van1164.voteshare.repository

import com.van1164.voteshare.config.redis.RedisConfig
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository

@Repository
class RedisRepository(val redisTemplate: RedisTemplate<String,String>) {

    //val redisTemplate by lazy { RedisConfig().redisTemplate() }

    fun save(jwt : String, email : String){
        redisTemplate.opsForValue().set(jwt,email)
    }

    fun loadByJwt(jwt : String): String? {
        return redisTemplate.opsForValue().get(jwt)
    }

}