package com.proseed.api.auth.dto

data class AuthRegisterRequest(
    val nickName: String,
    val email: String,
    val platformId: String,
    val platformType: String
)