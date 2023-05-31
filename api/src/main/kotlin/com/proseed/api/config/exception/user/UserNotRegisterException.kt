package com.proseed.api.config.exception.user

import com.proseed.api.user.model.User

class UserNotRegisterException(var user: User) : RuntimeException() {

    fun errorMessage(): UserRegisterDto {
        return UserRegisterDto(
            platformId = this.user.platformId,
            platformType = this.user.platformType,
            role = this.user.role.toString(),
            nickName = this.user.nickName,
            email = this.user.email,
            profileImageUrl = this.user.profileImageUrl,
            registerStage = 0
        )
    }

    data class UserRegisterDto (
        val platformId: String,
        val platformType: String,
        val role: String,
        val nickName: String,
        val email: String,
        val profileImageUrl: String,
        val registerStage: Int
            )
}