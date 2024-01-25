package com.van1164.voteshare.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

object ServiceUtil {

    fun createUUID(): String {
        return UUID.randomUUID().toString()
    }

    fun dateTimeNow(): Date {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())
    }
}