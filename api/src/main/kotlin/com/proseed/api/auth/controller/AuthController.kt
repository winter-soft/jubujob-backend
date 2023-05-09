package com.proseed.api.auth.controller

import com.proseed.api.auth.dto.AuthRegisterRequest
import com.proseed.api.auth.dto.AuthRequest
import com.proseed.api.auth.dto.AuthResponse
import com.proseed.api.auth.dto.kakao.KakaoLoginPageResponse
import com.proseed.api.auth.dto.kakao.KakaoTokenResponse
import com.proseed.api.auth.service.AuthService
import com.proseed.api.config.exception.user.UserNotFoundException
import com.proseed.api.user.dto.UserResponse
import com.proseed.api.user.model.User
import com.proseed.api.user.repository.UserRepository
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
    private val userRepository: UserRepository
) {
    // OAuth 2.0 Get kakao loginPage
    @GetMapping("/kakao/loginPage")
    fun kakaoLoginPage(): ResponseEntity<KakaoLoginPageResponse> {
        return ResponseEntity.ok(authService.kakaoLoginPage())
    }

    // OAuth 2.0 Token Provider
    @GetMapping("/kakao/callback")
    fun kakaoTokenProvider(
        @RequestParam("code") code: String
    ): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authService.kakaoTokenProvider(code))
    }


    @PostMapping("/register")
    fun register(
        @RequestBody request: AuthRegisterRequest
    ): ResponseEntity<AuthResponse> {

        return ResponseEntity.ok(authService.register(request))
    }

    @PostMapping("/authenticate")
    fun authenticate(
        @RequestBody request: AuthRequest
    ): ResponseEntity<AuthResponse> {

        return ResponseEntity.ok(authService.authenticate(request))
    }

    @GetMapping("/valid")
    @PreAuthorize("hasRole('USER')")
    fun isValidToken(
        @AuthenticationPrincipal user: User
    ): UserResponse {
        return UserResponse(user)
    }

    @PostMapping("/test")
    fun errorTest(
        @RequestBody request: AuthRequest
    ): ResponseEntity<AuthResponse> {
        var user = userRepository.findByPlatformId(request.platformId) ?: throw UserNotFoundException()
        return ResponseEntity.ok(AuthResponse("ok"))
    }
}