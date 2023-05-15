package com.proseed.api.auth.dto.kakao

data class KakaoRegisterStage2Request(
    val platformId: String,
    val platformType: String,
    val email: String,
    val preference: String
) {
}