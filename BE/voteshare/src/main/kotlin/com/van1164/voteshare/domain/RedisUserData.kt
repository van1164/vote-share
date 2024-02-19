package com.van1164.voteshare.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import lombok.ToString
import org.springframework.data.redis.core.RedisHash



data class RedisUserData(
        val id: Long? = null,
        val nickName: String,
        val email: String,
)
