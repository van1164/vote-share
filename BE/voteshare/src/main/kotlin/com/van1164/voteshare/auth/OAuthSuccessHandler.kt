package com.van1164.voteshare.auth

import JwtTokenProvider
import com.van1164.voteshare.EntityManagerObject.tx
import com.van1164.voteshare.domain.OAuth2Provider
import com.van1164.voteshare.domain.Role
import com.van1164.voteshare.domain.TokenInfo
import com.van1164.voteshare.domain.User
import com.van1164.voteshare.repository.UserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.transaction.Transactional
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component(value = "authenticationSuccessHandler")
class OAuthSuccessHandler : AuthenticationSuccessHandler {
    val userRepository = UserRepository()
    val jwtTokenProvider = JwtTokenProvider()
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val oAuth2User = authentication.principal as OAuth2User
        val name = oAuth2User.attributes["name"] as String
        val email = oAuth2User.attributes["email"] as String
        val user = userRepository.loadUserByEmail(email)
        val jwt = jwtTokenProvider.createToken(email)
        if (user == null) {
            saveUser(email, name, jwt)
        }
        response.status = HttpServletResponse.SC_OK
        response.contentType = "application/json;charset=UTF-8"
        response.sendRedirect("/login?access-code=${jwt.accessToken}")
    }

    @Transactional
    fun saveUser(email: String, name: String, jwt: TokenInfo) {
        val newUser = User(
            nickName = name,
            email = email,
            accessToken = jwt.accessToken,
            role = Role.USER,
            oAuth2Provider = OAuth2Provider.GOOGLE
        )
        tx.begin()
        userRepository.save(newUser)
        tx.commit()
    }
}