package com.van1164.voteshare.config.auth

import JwtTokenProvider
import com.van1164.voteshare.JwtAuthenticationFilter
import com.van1164.voteshare.auth.OAuthFailureHandler
import com.van1164.voteshare.auth.OAuthSuccessHandler
import lombok.RequiredArgsConstructor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            httpBasic { disable() }
            csrf { disable() }
            cors { }
            authorizeRequests {
                authorize("/api/**", hasAuthority("ROLE_USER"))
            }
            oauth2Login {
                loginPage = "/loginPage"
                userInfoEndpoint {  }
                authenticationSuccessHandler = OAuthSuccessHandler()
                authenticationFailureHandler = OAuthFailureHandler()
            }
            //addFilterBefore<UsernamePasswordAuthenticationFilter> (JwtAuthenticationFilter(JwtTokenProvider()))
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