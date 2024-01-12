package com.van1164.voteshare.service

import com.van1164.voteshare.EntityManagerObject
import com.van1164.voteshare.domain.OAuth2Provider
import com.van1164.voteshare.domain.Role
import com.van1164.voteshare.domain.User
import com.van1164.voteshare.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class OAuth2UserService : DefaultOAuth2UserService() {
    private val userRepository = UserRepository()
    private val tx = EntityManagerObject.tx
    @Transactional
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val user = super.loadUser(userRequest)
        println("OAuthUser = " + user.attributes)
        val name = user.attributes["name"] as String
        val email = user.attributes["email"] as String
        val newUser = User(
            nickName = name,
            email = email,
            accessToken = "tempToken",
            role = Role.USER,
            oAuth2Provider = OAuth2Provider.GOOGLE
        )
        tx.begin()
        userRepository.save(newUser)
        tx.commit()
        return super.loadUser(userRequest)
    }


}