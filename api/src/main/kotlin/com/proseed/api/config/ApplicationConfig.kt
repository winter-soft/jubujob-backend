package com.proseed.api.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.proseed.api.config.exception.user.UserNotFoundException
import com.proseed.api.user.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.client.RestTemplate

@Configuration
class ApplicationConfig(
    private val userRepository: UserRepository
) {
    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().registerModule(
            KotlinModule.Builder()
                .withReflectionCacheSize(512)
                .configure(KotlinFeature.NullToEmptyCollection, false)
                .configure(KotlinFeature.NullToEmptyMap, false)
                .configure(KotlinFeature.NullIsSameAsDefault, false)
                .configure(KotlinFeature.SingletonSupport, false)
                .configure(KotlinFeature.StrictNullChecks, false)
                .build()
        )
    }

    @Bean
    fun userDetailsService(): UserDetailsService { // UserDetailsService 빈 등록
        return UserDetailsService { // SAM(Single Abstract Method) 객체 구현
            userRepository.findByEmail(it) ?: throw UserNotFoundException()
        }
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider() // AuthenticationProvider 객체 생성
        authProvider.setUserDetailsService(userDetailsService()) // DetailsService 넣어주고
        authProvider.setPasswordEncoder(passwordEncoder()) // passwordEncoder도 넣어준다.

        return authProvider
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager // AuthenticationManger 빈 등록
    }
}