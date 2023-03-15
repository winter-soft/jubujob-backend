package com.proseed.api.auth.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.proseed.api.auth.dto.kakao.KakaoTokenRequest
import com.proseed.api.auth.dto.kakao.KakaoTokenResponse
import com.proseed.api.auth.dto.kakao.KakaoUserInfoResponse
import com.proseed.api.auth.dto.kakao.KakaoValueBuilder
import com.proseed.api.config.exception.auth.kakao.KakaoAuthorizationCodeNotFoundException
import com.proseed.api.config.exception.auth.kakao.KakaoTokenExpiredException
import com.proseed.api.user.model.Role
import com.proseed.api.user.model.User
import com.proseed.api.user.repository.UserRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.client.RestTemplate
import java.lang.RuntimeException

@AutoConfigureMockMvc
@SpringBootTest
internal class AuthServiceTest(
    @Autowired val restTemplate: RestTemplate,
    @Autowired val kakaoValueBuilder: KakaoValueBuilder,
    @Autowired val userRepository: UserRepository
) {

//    @Test
//    @DisplayName(" 카카오톡 토큰 받아오기 성공 테스트")
//    fun kakaoTokenProvider_PASS() {
//        val headers = HttpHeaders()
//        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
//
//        val kakaoTokenRequest = kakaoValueBuilder.kakaoTokenRequest("U8f6dZyocZDpfcEHiO1x-vBNKV9pGOpIRAOow6XuuOacEWPP3VrIpOIZgYoz9Tcbos7kFAo9dNsAAAGGv4-MGg")
//
//        val tokenResponse = restTemplate.postForObject(
//            "https://kauth.kakao.com/oauth/token",
//            HttpEntity(KakaoTokenRequest.of(kakaoTokenRequest), headers),
//            KakaoTokenResponse::class.java
//        )
//
//        val kakaoTokenResponse = tokenResponse
//
//        assertNotNull(kakaoTokenResponse?.access_token)
//        assertNotNull(kakaoTokenResponse?.token_type)
//        assertNotNull(kakaoTokenResponse?.refresh_token)
//        assertNotNull(kakaoTokenResponse?.id_token)
//        assertNotNull(kakaoTokenResponse?.expires_in)
//        assertNotNull(kakaoTokenResponse?.scope)
//        assertNotNull(kakaoTokenResponse?.refresh_token_expires_in)
//    }

    @Test
    @DisplayName(" 카카오톡 토큰 받아오기 실패 테스트")
    fun kakaoTokenProvider_FAIL() {
        assertThrows(RuntimeException::class.java) {
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

            val kakaoTokenRequest = kakaoValueBuilder.kakaoTokenRequest("abcd")

            restTemplate.postForObject(
                "https://kauth.kakao.com/oauth/token",
                HttpEntity(KakaoTokenRequest.of(kakaoTokenRequest), headers),
                KakaoTokenResponse::class.java
            ) ?: throw KakaoAuthorizationCodeNotFoundException()
        }
    }

    @Test
    @DisplayName("카카오톡 토큰으로 사용자 정보 가져오기 테스트")
    fun kakaoTokenTakeUserInfo() {
        // Header 설정
        val userInfoHeader = HttpHeaders()
        userInfoHeader.set("Authorization", "Bearer " + "B3KYxIlT6Mi1KUpa-YhxpEtLILP4GQ8bxG4vP8Z_Cj1zTgAAAYbDtfbH")

        val entity = HttpEntity("", userInfoHeader)

        val kakaoUserInfo = restTemplate.exchange(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.GET,
            entity,
            KakaoUserInfoResponse::class.java
        ).body ?: throw KakaoTokenExpiredException()

        assertNotNull(kakaoUserInfo.id)
        assertNotNull(kakaoUserInfo.connected_at)
        assertNotNull(kakaoUserInfo.properties)
        assertNotNull(kakaoUserInfo.kakao_account)

    }

    @Test
    @DisplayName("email이 userRepository에 존재하지 않을때 user가 생성되는지 테스트")
    fun when_userNotFound_createUser() {
        val email = "notValidEmail"

        var user = userRepository?.findByEmail(email)
            ?: userRepository.save(
                User(
                    nickName = "nickName",
                    email = email,
                    platformId = "123456789",
                    platformType = "KAKAO",
                    profileImageUrl = "",
                    role = Role.USER
                )
            )

        assertNotNull(user.id)
        assertEquals(user.nickName, "nickName")
        assertEquals(user.email, email)
        assertEquals(user.platformId, "123456789")
        assertEquals(user.platformType, "KAKAO")
        assertEquals(user.role, Role.USER)
    }
}