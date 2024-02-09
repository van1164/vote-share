package com.van1164.voteshare.service

import com.van1164.voteshare.domain.OAuth2Provider
import com.van1164.voteshare.domain.Role
import com.van1164.voteshare.domain.User
import com.van1164.voteshare.domain.Vote
import com.van1164.voteshare.repository.UserRepository
import com.van1164.voteshare.vo.UserDetailVO
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service


@Service
@Transactional
class UserService(val userRepository: UserRepository):BaseService() {
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
            oAuth2Provider = OAuth2Provider.GOOGLE,
            voteList = mutableListOf<Vote>()
        )
//        tx.begin()
        userRepository.save(newUser)
//        tx.commit()
    }

    fun update(user: User, accessToken: String) {
        userRepository.update(user,accessToken)


    }


}