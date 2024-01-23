package com.van1164.voteshare.auth

import com.van1164.voteshare.JwtTokenProvider
import com.van1164.voteshare.domain.TokenInfo
import com.van1164.voteshare.service.RedisService
import com.van1164.voteshare.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.transaction.Transactional
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component(value = "authenticationSuccessHandler")
class OAuthSuccessHandler(val userService :UserService, val jwtTokenProvider: JwtTokenProvider, val redisService: RedisService) : AuthenticationSuccessHandler {
//    val userRepository = UserRepository()
//    val jwtTokenProvider = com.van1164.voteshare.JwtTokenProvider()
//    val redisRepository by lazy { RedisRepository() }
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val oAuth2User = authentication.principal as OAuth2User
        val name = oAuth2User.attributes["name"] as String
        val email = oAuth2User.attributes["email"] as String
        val user = userService.loadUserByEmail(email)
        val jwt = jwtTokenProvider.createToken(email)
        if (user == null) {
            redisService.save(jwt.accessToken,email)
            println("XXXXXXXXXXXXXXXXXXXXXXXXXXXX")
            println("TTTTTTTTTTTTTTTTT:" + redisService.loadByJwt(jwt.accessToken))
            println("XXXXXXXXXXXXXXXXXXXXXXXXXXXX")
            saveUser(email, name, jwt)
        }
        response.status = HttpServletResponse.SC_OK
        response.contentType = "application/json;charset=UTF-8"
        response.addHeader("Authorization",jwt.accessToken)
        response.sendRedirect("/user/login")
    }

    @Transactional
    fun saveUser(email: String, name: String, jwt: TokenInfo) {
        userService.save(name,email,jwt.accessToken)
    }
}