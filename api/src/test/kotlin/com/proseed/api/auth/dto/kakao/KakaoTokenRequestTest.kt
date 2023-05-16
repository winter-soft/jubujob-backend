//package com.proseed.api.auth.dto.kakao
//
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.boot.test.context.SpringBootTest
//
//@SpringBootTest
//internal class KakaoTokenRequestTest(
//    @Autowired val kakaoValueBuilder: KakaoValueBuilder
//) {
//
//    @Test
//    fun `객체를 생성했을때 code를 제외한 default 값이 들어가는지 확인`() {
//        val kakaoTokenRequest = kakaoValueBuilder.kakaoTokenRequest("abcd")
//
//        println(kakaoTokenRequest)
//
//        assertNotNull(kakaoTokenRequest.grant_type)
//        assertNotNull(kakaoTokenRequest.client_id)
//        assertNotNull(kakaoTokenRequest.redirect_uri)
//        assertNotNull(kakaoTokenRequest.code)
//        assertNotNull(kakaoTokenRequest.client_secret)
//    }
//}