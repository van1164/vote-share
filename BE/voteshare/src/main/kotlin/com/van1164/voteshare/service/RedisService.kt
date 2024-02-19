package com.van1164.voteshare.service

import com.van1164.voteshare.domain.RedisUserData
import com.van1164.voteshare.domain.User
import com.van1164.voteshare.repository.RedisRepository
import org.springframework.stereotype.Service


@Service
class RedisService(val redisRepository: RedisRepository) {
    fun save(accessToken: String, user: User) {
        redisRepository.save(accessToken,user)
    }

    fun loadByJwt(accessToken: String): RedisUserData? {
        return redisRepository.loadByJwt(accessToken)
    }


}