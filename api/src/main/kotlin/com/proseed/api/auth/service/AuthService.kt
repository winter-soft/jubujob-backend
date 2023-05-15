package com.proseed.api.auth.service

import com.proseed.api.auth.dto.AuthRegisterRequest
import com.proseed.api.auth.dto.AuthRequest
import com.proseed.api.auth.dto.AuthResponse
import com.proseed.api.auth.dto.kakao.*
import com.proseed.api.config.exception.auth.kakao.KakaoAuthorizationCodeNotFoundException
import com.proseed.api.config.exception.auth.kakao.KakaoTokenExpiredException
import com.proseed.api.config.jwt.JwtService
import com.proseed.api.config.exception.user.UserNotFoundException
import com.proseed.api.config.exception.user.UserNotRegisterException
import com.proseed.api.config.exception.user.UserRegisterStageInValidException
import com.proseed.api.config.exception.user.UserUnAuthorizationMessageCheckException
import com.proseed.api.user.dto.UserResponse
import com.proseed.api.user.model.Role
import com.proseed.api.user.model.User
import com.proseed.api.user.repository.UserRepository
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate

@Service
@Transactional(readOnly = true)
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager,
    private val kakaoValueBuilder: KakaoValueBuilder,
    private val restTemplate: RestTemplate
) {

    fun authenticate(request: AuthRequest): AuthResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.email,
                request.platformId
            )
        )
        var user = userRepository.findByEmail(request.email) ?: throw UserNotFoundException()

        if(!passwordEncoder.matches(request.platformId, user.platformId))
            throw UserNotFoundException()

        var jwtToken = jwtService.generateToken(user)

        return AuthResponse(jwtToken)
    }

    fun kakaoLoginPage(): KakaoLoginPageResponse {
        return kakaoValueBuilder.kakaoLoginPageResponse()
    }

    // 토큰 발급 시스템
    fun kakaoTokenProvider(code: String): AuthResponse {
        // Header 설정
        val tokenRequestHeader = HttpHeaders()
        tokenRequestHeader.contentType = MediaType.APPLICATION_FORM_URLENCODED

        // kakaoTokenRequest 객체 생성
        val kakaoTokenRequest = kakaoValueBuilder.kakaoTokenRequest(code)

        val kakaoTokenResponse = restTemplate.postForObject(
            "https://kauth.kakao.com/oauth/token",
            HttpEntity(KakaoTokenRequest.of(kakaoTokenRequest), tokenRequestHeader),
            KakaoTokenResponse::class.java
        ) ?: throw KakaoAuthorizationCodeNotFoundException()

        // Header 설정
        val userInfoHeader = HttpHeaders()
        userInfoHeader.set("Authorization", "Bearer " + kakaoTokenResponse.access_token)

        val entity = HttpEntity("", userInfoHeader)

        val kakaoUserInfo = restTemplate.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.GET,
            entity,
            KakaoUserInfoResponse::class.java
        ).body ?: throw KakaoTokenExpiredException()

        var email = kakaoUserInfo.kakao_account.email

        // User 테이블에서 platformId를 찾는다.
        // 만약 이미 존재한다면 user정보를 가져오고 jwt토큰 생성
        // user 정보가 존재하지 않는다면, user정보를 등록하고 jwt토큰 생성

        var user = userRepository.findByEmailAndMessageCheckAndRegisterStage(email, true, 3)

        if (user == null) { // 회원가입
            user = userRepository.save(
                User(
                    platformId = passwordEncoder.encode(kakaoUserInfo.id),
                    platformType = "KAKAO",
                    role = Role.USER,
                    nickName = kakaoUserInfo.properties.nickname,
                    email = email,
                    profileImageUrl = kakaoUserInfo.properties.profile_image,
                    registerStage = 0
                ))

            throw UserNotRegisterException(user)
        } else  { // 닉네임, 프로필 사진 업데이트
            user.copy(
                nickName = kakaoUserInfo.properties.nickname,
                profileImageUrl = kakaoUserInfo.properties.profile_image
            )
            user = userRepository.save(user)
        }

        val jwtToken = jwtService.generateToken(user)

        return AuthResponse(jwtToken)
    }

    // stage1
    fun registerStage1(request: KakaoRegisterStage1Request): UserResponse {
        // messageCheck = 1 검증
        val user: User = userRepository?.findByEmailAndPlatformId(request.email, request.platformId) ?: throw UserNotFoundException()

        if (user.messageCheck == false) {
            throw UserUnAuthorizationMessageCheckException()
        }

        // 사용자 정보 업데이트 registerStage = 1로 변경
        user.copy(
            nickName = request.nickName,
            gender = request.gender,
            phoneNumber = request.phoneNumber,
            registerStage = 1
        )
        // 내용 저장
        val savedUser = userRepository.save(user)

        // 회원가입 결과 반환
        return UserResponse(savedUser)
    }

    // stage2
    fun registerStage2(request: KakaoRegisterStage2Request): UserResponse {
        // messageCheck = 1, registerStage = 1 검증
        val user: User = userRepository?.findByEmailAndPlatformId(request.email, request.platformId) ?: throw UserNotFoundException()

        if (user.messageCheck == false) {
            throw UserUnAuthorizationMessageCheckException()
        } else if (user.registerStage != 1) {
            throw UserRegisterStageInValidException()
        }

        // userPreference, registerStage = 2 로 업데이트
        user.copy(
            preference = request.preference,
            registerStage = 2
        )

        // 내용 저장
        val savedUser = userRepository.save(user)

        // 회원가입 결과 반환
        return UserResponse(savedUser)
    }

    // stage3
    fun registerStage3(request: KakaoRegisterStage3Request): AuthResponse {
        // messageCheck = 1, registerStage = 2 검증
        val user: User = userRepository?.findByEmailAndPlatformId(request.email, request.platformId) ?: throw UserNotFoundException()

        if (user.messageCheck == false) {
            throw UserUnAuthorizationMessageCheckException()
        } else if (user.registerStage != 2) {
            throw UserRegisterStageInValidException()
        }

        // userPreference, registerStage = 3 로 업데이트
        user.copy(
            registerStage = 3
        )

        // 내용 저장
        val savedUser = userRepository.save(user)

        // jwt 토큰 발급
        val jwtToken = jwtService.generateToken(savedUser)

        // 회원가입 결과 반환
        return AuthResponse(jwtToken)
    }
}
