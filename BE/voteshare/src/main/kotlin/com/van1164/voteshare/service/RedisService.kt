package com.van1164.voteshare.service

import com.van1164.voteshare.repository.RedisRepository
import org.springframework.stereotype.Service


@Service
class RedisService(val redisRepository: RedisRepository) {
    fun save(accessToken: String, email: String) {
        redisRepository.save(accessToken,email)
    }

    fun loadByJwt(accessToken: String): String? {
        return redisRepository.loadByJwt(accessToken)
    }


}