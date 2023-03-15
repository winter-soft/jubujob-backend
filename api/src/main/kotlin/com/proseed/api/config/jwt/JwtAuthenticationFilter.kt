package com.proseed.api.config.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.proseed.api.config.exception.ExceptionDto
import com.proseed.api.utils.logger
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService,
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {

    private val logger = logger<JwtAuthenticationFilter>()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        logger.info("====JWT Filter Running====")

        try {
            val authHeader: String? = request.getHeader("Authorization") // 헤더에서 Authorization의 값을 가져온다.
            var jwt: String
            var userEmail: String

            if (authHeader == null || !authHeader.startsWith("Bearer ")) { // Authorization의 값을 가져오지 못했거나, Bearer로 시작하지 않을 경우
                filterChain.doFilter(request, response) // filterChain을 그대로 다음으로 넘겨버린다. (Authentication이 존재하지 않으면 어차피 시큐리티에서 걸리기 때문에)
                return
            }

            jwt = authHeader.substring(7) // "Bearer "가 7글자니까 이후 문자열을 가져온다.
            userEmail = jwtService.extractUsername(jwt) // todo extract the userEmail from JWT token

            if (userEmail != null && SecurityContextHolder.getContext().authentication == null) { // userEmail을 찾지 못했거나 SecurityContext에 authentication이 등록되어 있지 않으면
                var userDetails: UserDetails = this.userDetailsService.loadUserByUsername(userEmail) // DB에서 userEmail을 찾아서 UserDetails 객체로 받아온다. (User는 UserDetails의 자식이기 때문에 가능)
                if (jwtService.isTokenValid(jwt, userDetails)) { // 토큰정보가 일치하는지 확인한다.
                    // 토큰정보가 일치할 경우
                    var authToken = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    ) // UsernamePasswordAuthenticationToken을 발급한다.
                    /**
                     * (credentials가 null인 이유는 Spring Security에서 Username을 Principle, *
                     * Password를 credentials라고 하는데 우리는 UserDetailsPasswordService가 아닌 *
                     * UserDetailsService를 사용하기 때문에) *
                     */

                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request) // request 정보를 토큰에 저장

                    SecurityContextHolder.getContext().authentication = authToken // SecurityContext에 Authentication을 등록 UsernamePasswordAuthenticationToken에 Authentication의 인터페이스가 구현되어 있다.
                }
            }
            filterChain.doFilter(request, response) // 다음 필터로 넘어가기
        }catch (e: ExpiredJwtException){
            logger.error("Could not set user authentication in security context {}" , e);
            jwtExceptionHandler(response, e.message?: "ExpiredJwtException");
        }catch (e: UnsupportedJwtException){
            logger.error("Could not set user authentication in security context {}" , e);
            jwtExceptionHandler(response, e.message?: "UnsupportedJwtException");
        }catch (e: MalformedJwtException){
            logger.error("Could not set user authentication in security context {}" , e);
            jwtExceptionHandler(response, e.message?: "MalformedJwtException");
        }catch (e: SignatureException){
            logger.error("Could not set user authentication in security context {}" , e);
            jwtExceptionHandler(response, e.message?: "SignatureException");
        }catch (e: IllegalArgumentException ){
            logger.error("Could not set user authentication in security context {}" , e);
            jwtExceptionHandler(response, e.message?: "IllegalArgumentException");
        }
    }

    private fun jwtExceptionHandler(response: HttpServletResponse, message: String) {
        val dto = ExceptionDto(message = message)
        response.status = HttpStatus.FORBIDDEN.value()
        response.characterEncoding = "UTF-8"
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        objectMapper.writeValue(response.writer, dto)
    }
}