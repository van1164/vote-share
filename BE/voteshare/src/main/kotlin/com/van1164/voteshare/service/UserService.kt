package com.van1164.voteshare.service

import com.van1164.voteshare.EntityManagerObject
import com.van1164.voteshare.data.OAuth2Provider
import com.van1164.voteshare.data.User
import com.van1164.voteshare.repository.UserRepository
import org.springframework.stereotype.Service


@Service
class UserService:BaseService() {

    private val userRepository = UserRepository()
    fun createUser(nickName : String): User {
        val newUser = User(nickName=nickName, accessToken = " ", oAuth2Provider = OAuth2Provider.KAKAO)
        tx.begin()
        userRepository.save(newUser)
        tx.commit()
        return newUser
    }

    fun loadUserById(id: Long): User? {
        return userRepository.loadUserById(id)
    }


}