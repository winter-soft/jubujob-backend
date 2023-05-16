package com.proseed.api.config.security

import com.proseed.api.config.jwt.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val jwtAuthFilter: JwtAuthenticationFilter,
    private val authenticationProvider: AuthenticationProvider,
){

    // 인증(Authentication) : 해당 사용자가 본인이 맞는지 확인하는 과정
    // 인가(Authorization) : 해당 사용자가 요청하는 자원을 실행할 수 있는 권한이 있는가를 확인하는 과정
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf()
            .disable() // csrf 설정을 사용하지 않는다.
            .authorizeHttpRequests() // HttpRequest를 허용하는 Matchers
            .requestMatchers("/auth/**", "/",
                "/swagger-ui/**","/swagger-ui.html", "/v3/api-docs/**") // 스웨거 관련 접근 허용
            .permitAll() // 모든 권한 허용
            .anyRequest() // 나머지 요청들은
            .authenticated() // 인증된 사용자만 (인증되지 않은 사용자는 Forbidden Error)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션기반이 아님을 선언. (JWT를 사용하기 때문에) JWT는 쿠키와 유사
            .and()
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java) // 직접 작성한 jwtAuthFilter를 UsernamePasswordAuthenticationFilter 전에 추가한다.

        return http.build()
    }
}