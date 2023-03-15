package com.proseed.api.auth.dto.kakao

data class KakaoTokenResponse(
    val access_token: String,
    val token_type: String,
    val refresh_token: String,
    val id_token: String,
    val expires_in: Int,
    val scope: String,
    val refresh_token_expires_in: Int
)
