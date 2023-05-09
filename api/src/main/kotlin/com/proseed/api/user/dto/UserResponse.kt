package com.proseed.api.user.dto

import com.proseed.api.user.model.Role
import com.proseed.api.user.model.User

data class UserResponse(
    val email: String,
    val nickName: String,
    val platformType: String,
    val preference: String? = null,
    val role: Role
) {
    constructor(user: User) : this(user.email, user.nickName, user.platformType, user.preference, user.role)
}