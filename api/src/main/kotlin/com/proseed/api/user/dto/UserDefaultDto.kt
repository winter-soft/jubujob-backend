package com.proseed.api.user.dto

import com.proseed.api.user.model.User

data class UserDefaultDto(
    val user_profileImageUrl: String,
    val user_nickName: String
) {
    constructor() : this("", "")
    constructor(user: User) : this(
        user_profileImageUrl = user.profileImageUrl,
        user_nickName = user.nickName
    )
}