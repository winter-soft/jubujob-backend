package com.proseed.api.auth.controller

import com.fasterxml.jackson.databind.ObjectMapper
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
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService,
    private val userRepository: UserRepository
) {
    // OAuth 2.0 Get kakao loginPage
    @Operation(summary = "OAuth 2.0 Get kakao loginPage", description = "로그인 페이지 url 얻기")
    @GetMapping("/kakao/loginPage")
    fun kakaoLoginPage(): ResponseEntity<KakaoLoginPageResponse> {
        return ResponseEntity.ok(authService.kakaoLoginPage())
    }

    // OAuth 2.0 Token Provider
    @Operation(summary = "OAuth 2.0 Token Provide", description = "카카오 로그인 후 롤백을 받는 api [프론트에서 신경 쓸 필요 없음]")
    @GetMapping("/kakao/callback")
    fun kakaoTokenProvider(
        @Parameter(name = "code", required = true, example = "asklcjkdlsaasfjklasjfklsafa") @RequestParam("code") code: String
    ): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authService.kakaoTokenProvider(code))
    }

    // 회원가입 단계
    @Operation(summary = "register Stage 1", description = "회원가입 1단계 [platformId, platformType, nickName, gender, email, phoneNumer]가 필요하다.")
    @PostMapping("/kakao/register/stage1")
    fun registerStage1(
        @Parameter(name = "kakao_register_stage1_request", required = true) @RequestBody(required = true) request: KakaoRegisterStage1Request
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(authService.registerStage1(request))
    }

    @Operation(summary = "register Stage 2", description = "회원가입 2단계 [platformId, platformType, email, preference]가 필요하다.")
    @PostMapping("/kakao/register/stage2")
    fun registerStage2(
        @Parameter(name = "kakao_register_stage2_request", required = true) @RequestBody(required = true) request: KakaoRegisterStage2Request
    ): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(authService.registerStage2(request))
    }

    @Operation(summary = "register Stage 3", description = "회원가입 3단계 [platformId, platformType, email]가 필요하다.")
    @PostMapping("/kakao/register/stage3")
    fun registerStage3(
        @Parameter(name = "kakao_register_stage3_request", required = true) @RequestBody(required = true) request: KakaoRegisterStage3Request
    ): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authService.registerStage3(request))
    }

    // Test API //
    @Operation(hidden = true)
    @PostMapping("/authenticate")
    fun authenticate(
        @RequestBody request: AuthRequest
    ): ResponseEntity<AuthResponse> {

        return ResponseEntity.ok(authService.authenticate(request))
    }

    @Operation(hidden = true)
    @GetMapping("/valid")
    fun isValidToken(
        @AuthenticationPrincipal user: User
    ): UserResponse {
        return UserResponse(user)
    }

    @Operation(hidden = true)
    @PostMapping("/test")
    fun errorTest(
        @RequestBody request: AuthRequest
    ): ResponseEntity<AuthResponse> {
        var user = userRepository.findByPlatformId(request.platformId) ?: throw UserNotFoundException()
        return ResponseEntity.ok(AuthResponse("ok"))
    }
    ////////////////
}