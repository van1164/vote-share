package com.van1164.voteshare

import com.van1164.voteshare.domain.Role
import com.van1164.voteshare.domain.TokenInfo
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap


@Component
@RequiredArgsConstructor
class JwtTokenProvider(
    @Value("\${jwt.secret}")
    var secretKey: String
) {

    val EXPIRATION_MILLISECONDS: Long = 1000 * 60 * 30
    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)) }


    fun createToken(email: String): TokenInfo {
        val claims = Jwts.claims().apply {
            subject = email
            put("roles", Role.entries)
        }
        val now = Date()
        val accessExpiration = Date(now.time + EXPIRATION_MILLISECONDS)

        println("XXXXXXXXXXXXXXXXXXXXXXXXX " + secretKey)
        val accessToken = Jwts
                .builder()
                .claim("auth", claims)
                .setIssuedAt(now)
                .setExpiration(accessExpiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact()

        return TokenInfo("Bearer", accessToken)
    }

    fun getAuthentication(token: String): Authentication {
        val claims = getClaims(token)
        val auth = claims["auth"] ?: throw RuntimeException("잘못된 토큰입니다.")
        val email = (auth as LinkedHashMap<*, *>)["sub"] as String
        val authorities: Collection<GrantedAuthority> = (auth.toString())
                .split(",")
                .map { SimpleGrantedAuthority(it) }

        val principal: UserDetails = User(email, "", authorities)
        println(principal)
        println(email)  //redis에서 토큰 있는지도 한번 더 체크 구현해야함
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    fun validateToken(token: String): Boolean {
        try {
            getClaims(token)
            return true
        } catch (e: Exception) {
            println(e.message)
        }
        return false
    }

    private fun getClaims(token: String): Claims {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .body
    }
}