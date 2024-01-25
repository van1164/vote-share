package com.van1164.voteshare.util

import java.util.*

class UuidUtil {

    fun createUUID(): String {
        return UUID.randomUUID().toString()
    }
}