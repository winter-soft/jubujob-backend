package com.proseed.api.auth.dto.kakao

data class KakaoRegisterStage0Response(
    val platformId: String,
    val platformType: String,
    val role: String,
    val nickName: String,
    val email: String,
    val profileImageUrl: String,
    val registerStage: Int
) {
}