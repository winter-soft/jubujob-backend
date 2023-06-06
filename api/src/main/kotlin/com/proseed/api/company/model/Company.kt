package com.proseed.api.company.model

import com.proseed.api.common.model.TimeZone
import com.proseed.api.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "COMPANY")
class Company(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    // Parameter
    val name: String,
    val imageUrl: String,
    val introduction: String
) : TimeZone() {

    constructor() : this(
        id = null,
        name = "",
        imageUrl = "",
        introduction = ""
        )
}