package com.proseed.api.auth.controller

import com.proseed.api.auth.dto.AuthRequest
import com.proseed.api.auth.dto.AuthResponse
import com.proseed.api.auth.dto.kakao.KakaoLoginPageResponse
import com.proseed.api.auth.dto.kakao.KakaoRegisterStage1Request
import com.proseed.api.auth.dto.kakao.KakaoRegisterStage2Request
import com.proseed.api.auth.dto.kakao.KakaoRegisterStage3Request
import com.proseed.api.auth.service.AuthService
import com.proseed.api.config.exception.user.UserNotFoundException
import com.proseed.api.user.dto.UserResponse
import com.proseed.api.user.model.User
import com.proseed.api.user.repository.UserRepository
import org.springframework.http.ResponseEntity
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

    // 회원가입 단계

    @PostMapping("/kakao/register/stage1")
    fun registerStage1(
        @RequestBody(required = true) request: KakaoRegisterStage1Request
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(authService.registerStage1(request))
    }

    @PostMapping("/kakao/register/stage2")
    fun registerStage2(
        @RequestBody(required = true) request: KakaoRegisterStage2Request
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(authService.registerStage2(request))
    }

    @PostMapping("/kakao/register/stage3")
    fun registerStage3(
        @RequestBody(required = true) request: KakaoRegisterStage3Request
    ): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authService.registerStage3(request))
    }

    @PostMapping("/authenticate")
    fun authenticate(
        @RequestBody request: AuthRequest
    ): ResponseEntity<AuthResponse> {

        return ResponseEntity.ok(authService.authenticate(request))
    }

    @GetMapping("/valid")
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