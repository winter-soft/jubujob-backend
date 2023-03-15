package com.proseed.api.auth.dto.kakao

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service

@Service
class KakaoValueBuilder(
    @Value("\${spring.security.oauth2.client.provider.authorization-uri}") var authorization_uri: String,
    @Value("\${spring.security.oauth2.client.provider.token-uri}") var token_uri: String,
    @Value("\${spring.security.oauth2.client.provider.user-info-uri}") var user_info_uri: String,
    @Value("\${spring.security.oauth2.client.kakao.client-id}") var client_id: String,
    @Value("\${spring.security.oauth2.client.kakao.client-secret}") var client_secret: String,
    @Value("\${spring.security.oauth2.client.kakao.redirect-uri}") var redirect_uri: String,
    @Value("\${spring.security.oauth2.client.kakao.scope}") var scope: String,
    @Value("\${spring.security.oauth2.client.kakao.nonce}") var nonce: String,
    @Value("\${spring.security.oauth2.client.kakao.authorization-grant-type}") val grant_type: String,
) {

    fun kakaoLoginPageResponse(): KakaoLoginPageResponse {
        return KakaoLoginPageResponse(
            loginPage = "${authorization_uri}?" +
                    "response_type=code&" +
                    "client_id=${client_id}&" +
                    "redirect_uri=${redirect_uri}&" +
                    "client_secret=${client_secret}&" +
                    "scope=${scope}&" +
                    "nonce=${nonce}"
        )
    }

    // 싱글톤일 필요가 없으므로 Bean 제거
    fun kakaoTokenRequest(code: String): KakaoTokenRequest {
        return KakaoTokenRequest(
            grant_type = grant_type,
            client_id = client_id,
            redirect_uri = redirect_uri,
            code = code,
            client_secret = client_secret
        )
    }

}