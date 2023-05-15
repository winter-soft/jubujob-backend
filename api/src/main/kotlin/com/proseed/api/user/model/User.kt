package com.proseed.api.user.model

import com.proseed.api.common.model.TimeZone
import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

@Entity
@Table(name = "USER")
data class User(
    @Id @GeneratedValue
    val id: Long? = null,

    val platformId: String,
    val platformType: String,
    @Enumerated(EnumType.STRING)
    val role: Role,
    val nickName: String,
    @Column(unique = true)
    val email: String,
    val phoneNumber: String? = null,
    val gender: String? = "male",
    val profileImageUrl: String, // TODO: Default Image Url
    val preference: String? = null,
    val messageCheck: Boolean? = false,
    val registerStage: Int
) : UserDetails, TimeZone() {

    constructor() : this(
        id = null,
        platformId = "",
        platformType = "",
        role = Role.USER,
        nickName = "",
        email = "",
        profileImageUrl = "",
        preference = null,
        messageCheck = false,
        registerStage = 0
    ) // NoArgsConstructor

    // UserDetails Implements
    override fun getAuthorities(): List<SimpleGrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String {
        return platformId
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
    // ====

    override fun toString(): String {
        return "User(id=$id, nickName='$nickName', email='$email', platformId='$platformId', platformType='$platformType', profileImageUrl='$profileImageUrl', role=$role)"
    }
}