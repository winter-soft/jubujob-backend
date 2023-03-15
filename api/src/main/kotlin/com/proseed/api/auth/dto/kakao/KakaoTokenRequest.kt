package com.proseed.api.auth.dto.kakao

import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

data class KakaoTokenRequest(
    val grant_type: String,
    val client_id: String,
    val redirect_uri: String,
    val code: String, // code는 매번 바뀐다.
    var client_secret: String
) {
    companion object {
        fun of(dto: KakaoTokenRequest): MultiValueMap<String, String> {
            return jacksonObjectMapper()
                .convertValue<Map<String, String>>(dto)
                .let { LinkedMultiValueMap<String, String>().apply { setAll(it) } }
        }
    }

}
