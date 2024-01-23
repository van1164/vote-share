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



@Component
@RequiredArgsConstructor
class JwtTokenProvider {
    @Value("\${jwt.secret}")
    private var secretKey: String = "ThisIsTestKeyThisIsTestKeyThisIsTestKeyThisIsTestKeyThasdfsdf"
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

        val authorities: Collection<GrantedAuthority> = (auth as String)
                .split(",")
                .map { SimpleGrantedAuthority(it) }

        val principal: UserDetails = User(claims.subject, "", authorities)

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