package com.van1164.voteshare.domain

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash


@RedisHash(value = "jwt")
data class JwtRedisData(
        @Id
        val jwt : String,

        val email : String
)
