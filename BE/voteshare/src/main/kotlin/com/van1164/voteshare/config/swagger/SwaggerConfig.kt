package com.van1164.voteshare.config.swagger

import com.van1164.voteshare.JwtTokenProvider
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.servlet.config.annotation.EnableWebMvc


@OpenAPIDefinition(
    info = Info(
        title = "Vote-Share API 명세서",
        description = "Vote-Share에 사용되는 API 명세서",
        version = "v1"
    )
)
@Configuration
class SwaggerConfig {

    private val BEARER_TOKEN_PREFIX = "Bearer";

    @Bean
    fun openAPI() : OpenAPI {
        val jwtSchemeName = BEARER_TOKEN_PREFIX;
        val securityRequirement = SecurityRequirement().addList(jwtSchemeName);
        val components =  Components()
            .addSecuritySchemes(jwtSchemeName, SecurityScheme()
                .name(jwtSchemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme(BEARER_TOKEN_PREFIX)
                .bearerFormat(BEARER_TOKEN_PREFIX));

        // Swagger UI 접속 후, 딱 한 번만 accessToken을 입력해주면 모든 API에 토큰 인증 작업이 적용됩니다.
        return OpenAPI()
            .addSecurityItem(securityRequirement)
            .components(components);
    }
}
