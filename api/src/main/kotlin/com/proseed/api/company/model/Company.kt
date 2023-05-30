package com.proseed.api.company.model

import com.proseed.api.common.model.TimeZone
import com.proseed.api.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "COMPANY")
class Company(
    @Id @GeneratedValue
    val id: Long? = null,

    // Foreign Key
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User?, // Not null

    // Parameter
    val name: String,
    val imageUrl: String,
    val introduction: String
) : TimeZone() {

    constructor() : this(
        id = null,
        user = null,
        name = "",
        imageUrl = "",
        introduction = ""
        )
}