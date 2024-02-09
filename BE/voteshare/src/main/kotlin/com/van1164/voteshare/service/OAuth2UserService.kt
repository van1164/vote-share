package com.van1164.voteshare.service

import com.van1164.voteshare.JwtTokenProvider
import com.van1164.voteshare.EntityManagerObject
import com.van1164.voteshare.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service


@Service
class OAuth2UserService : DefaultOAuth2UserService() {
    @Transactional
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
//        val user = super.loadUser(userRequest)
//        println("OAuthUser = " + user.attributes)
//        val name = user.attributes["name"] as String
//        val email = user.attributes["email"] as String
//        val testUser = userRepository.loadUserByEmail(email)
//        if (testUser == null) {
//            val jwt = jwtTokenProvider.createToken(email)
//            println(jwt.accessToken)
//            val newUser = User(
//                    nickName = name,
//                    email = email,
//                    accessToken = jwt.accessToken,
//                    role = Role.USER,
//                    oAuth2Provider = OAuth2Provider.GOOGLE
//            )
//            tx.begin()
//            userRepository.save(newUser)
//            tx.commit()
//
//        }
        return super.loadUser(userRequest)
    }


}