package com.proseed.api.user

import com.proseed.api.user.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {

    fun findByEmail(email: String): User?
    fun findByPlatformId(platformId: String): User?
    fun findByEmailAndPlatformId(email: String, platformId: String): User?
    fun findByEmailAndMessageCheckAndRegisterStage(email: String, messageCheck: Boolean, registerStage: Int): User?
}