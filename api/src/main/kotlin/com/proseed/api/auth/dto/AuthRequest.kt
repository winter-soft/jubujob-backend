package com.proseed.api.auth.dto

data class AuthRequest(
    val email: String,
    val platformId: String
)