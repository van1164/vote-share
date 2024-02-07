package com.van1164.voteshare.auth

import com.van1164.voteshare.JwtTokenProvider
import com.van1164.voteshare.domain.TokenInfo
import com.van1164.voteshare.domain.User
import com.van1164.voteshare.repository.RedisRepository
import com.van1164.voteshare.repository.UserRepository
import com.van1164.voteshare.service.RedisService
import com.van1164.voteshare.service.UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component(value = "authenticationSuccessHandler")
class OAuthSuccessHandler(val userService: UserService,val redisService: RedisService, val jwtTokenProvider: JwtTokenProvider) : AuthenticationSuccessHandler {
//    val userService = UserService(UserRepository())
//    val jwtTokenProvider = JwtTokenProvider()
//    val redisService = RedisService(RedisRepository())
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
            println("TTTTTTTTTTTTTTTTT:" + redisService.loadByJwt(jwt.accessToken))
            saveUser(email, name, jwt)
        } else{
            updateUser(user,jwt)
        }
        response.status = HttpServletResponse.SC_OK
        response.contentType = "application/json;charset=UTF-8"
        response.addHeader("Authorization",jwt.accessToken)
        response.sendRedirect("http://localhost:3000/access/google?code="+jwt.accessToken)
    }

    @Transactional
    fun saveUser(email: String, name: String, jwt: TokenInfo) {
        userService.save(name,email,jwt.accessToken)
    }
    @Transactional
    fun updateUser(user: User, jwt: TokenInfo) {
        userService.update(user,jwt.accessToken)
    }
}