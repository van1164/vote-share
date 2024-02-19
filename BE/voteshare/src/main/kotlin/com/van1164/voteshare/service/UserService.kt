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

    @Transactional
    fun save(name: String, email: String, accessToken: String): User {
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
        return newUser
    }

    fun update(user: User, accessToken: String) {
        userRepository.update(user,accessToken)


    }


}