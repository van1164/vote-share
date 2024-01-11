package com.van1164.voteshare.service

import com.van1164.voteshare.EntityManagerObject
import com.van1164.voteshare.data.OAuth2Provider
import com.van1164.voteshare.data.User
import com.van1164.voteshare.repository.UserRepository
import org.springframework.stereotype.Service


@Service
class MainService {

    private val userRepository = UserRepository()
    private val tx = EntityManagerObject.tx
    fun createUser(nickName : String){
        val newUser = User(nickName=nickName, accessToken = " ", oAuth2Provider = OAuth2Provider.KAKAO)
        tx.begin()
        userRepository.save(newUser)
        tx.commit()
    }

    fun loadUser(id: Long): User? {
        return userRepository.loadUser(id)
    }


}