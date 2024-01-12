package com.van1164.voteshare.service

import com.van1164.voteshare.domain.OAuth2Provider
import com.van1164.voteshare.domain.Role
import com.van1164.voteshare.domain.User
import com.van1164.voteshare.repository.UserRepository
import org.springframework.stereotype.Service


@Service
class UserService:BaseService() {
    val userRepository = UserRepository()
    fun loadUserById(id: Long): User? {
        return userRepository.loadUserById(id)
    }


}