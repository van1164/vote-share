package com.van1164.voteshare.config.auth

import com.van1164.voteshare.auth.OAuthFailureHandler
import com.van1164.voteshare.auth.OAuthSuccessHandler
import com.van1164.voteshare.service.OAuth2UserService
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke
@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            httpBasic { disable() }
            csrf { disable() }
            cors { }
            authorizeRequests {
                authorize("/user/**", hasAuthority("ROLE_USER"))
            }
            oauth2Login {
                loginPage = "/loginPage"
                userInfoEndpoint {  }
                authenticationSuccessHandler = OAuthSuccessHandler()
                authenticationFailureHandler = OAuthFailureHandler()
            }
        }
        return http.build()
    }

    @Bean
    fun userDetailsService(): UserDetailsService {
        val userDetails = User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("USER")
            .build()
        return InMemoryUserDetailsManager(userDetails)
    }
}