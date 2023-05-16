package com.proseed.api.config.swagger

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@OpenAPIDefinition(
    info = Info(
        title = "JubuJob App",
        description = "couple app api명세",
        version = "v1"
    )
)
@Configuration
class SwaggerConfig {

    @Bean
    fun chatOpenApi(): GroupedOpenApi? {
        val paths = arrayOf("/v1/**")
        return GroupedOpenApi.builder()
            .group("JubuJob API v1")
            .pathsToMatch(*paths)
            .build()
    }
}