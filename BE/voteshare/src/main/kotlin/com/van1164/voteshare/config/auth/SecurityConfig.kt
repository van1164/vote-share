package com.van1164.voteshare.config.auth

import com.van1164.voteshare.auth.OAuthFailureHandler
import com.van1164.voteshare.auth.OAuthSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(val oAuthSuccessHandler: OAuthSuccessHandler, val oAuthFailureHandler: OAuthFailureHandler) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            httpBasic { disable() }
            csrf { disable() }
            cors { }
            authorizeRequests {
                authorize("/api/**", permitAll)
            }
            oauth2Login {
                loginPage = "/loginPage"
                userInfoEndpoint { }
                authenticationSuccessHandler = oAuthSuccessHandler
                authenticationFailureHandler = oAuthFailureHandler
            }
            //addFilterBefore<UsernamePasswordAuthenticationFilter> (JwtAuthenticationFilter(com.van1164.voteshare.JwtTokenProvider()))
        }
        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        val userDetails = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER").build()
        return InMemoryUserDetailsManager(userDetails)
    }
}