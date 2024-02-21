package com.van1164.voteshare.config.auth

import com.van1164.voteshare.JwtAuthenticationFilter
import com.van1164.voteshare.JwtTokenProvider
import com.van1164.voteshare.auth.OAuthFailureHandler
import com.van1164.voteshare.auth.OAuthSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(val oAuthSuccessHandler: OAuthSuccessHandler, val oAuthFailureHandler: OAuthFailureHandler,val jwtAuthenticationFilter: JwtAuthenticationFilter) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            httpBasic { disable() }
            csrf { disable() }
            cors { }
            authorizeRequests {
                authorize("/api/v1/vote/detail_page/**", permitAll)
                authorize("/api/v1/main_page", permitAll)
                authorize("/api/**", authenticated)
            }
            oauth2Login {
                loginPage = "/loginPage"
                userInfoEndpoint {
                }
                authenticationSuccessHandler = oAuthSuccessHandler
                authenticationFailureHandler = oAuthFailureHandler
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(jwtAuthenticationFilter)
        }
        return http.build()
    }


}