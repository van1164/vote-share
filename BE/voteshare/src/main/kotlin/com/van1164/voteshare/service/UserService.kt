package com.van1164.voteshare.service

import com.van1164.voteshare.EntityManagerObject
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

    fun loadUserByEmail(email: String): User? {
        return userRepository.loadUserByEmail(email)
    }

    fun save(name: String, email: String, accessToken: String) {
        val newUser = User(
            nickName = name,
            email = email,
            accessToken = accessToken,
            role = Role.USER,
            oAuth2Provider = OAuth2Provider.GOOGLE
        )
        EntityManagerObject.tx.begin()
        userRepository.save(newUser)
        EntityManagerObject.tx.commit()
    }


}