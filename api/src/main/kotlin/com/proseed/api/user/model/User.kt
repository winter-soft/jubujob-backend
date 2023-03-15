package com.proseed.api.user.model

import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "_user")
data class User(
    @Id @GeneratedValue
    val id: Int? = null,
    val nickName: String,
    val email: String,
    val platformId: String,
    val platformType: String,
    val profileImageUrl: String, // TODO: Default Image Url
    @Enumerated(EnumType.STRING)
    val role: Role
) : UserDetails {

    constructor() : this(null,"","","","","",Role.USER) // NoArgsConstructor

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