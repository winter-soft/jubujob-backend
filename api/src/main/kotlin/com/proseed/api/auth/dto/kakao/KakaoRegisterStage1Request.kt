package com.proseed.api.auth.dto.kakao

data class KakaoRegisterStage1Request(
    val platformId: String,
    val platformType: String,
    val nickName: String,
    val gender: String,
    val email: String,
    val phoneNumber: String,
) {
}