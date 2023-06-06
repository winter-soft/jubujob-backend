package com.proseed.api.company.model

import com.proseed.api.common.model.TimeZone
import com.proseed.api.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "COMPANYMEMBER")
class CompanyMember(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User?, // Not null
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    val company: Company?, // Not null
) : TimeZone() {
    constructor() : this(
        id = null,
        user = null,
        company = null
    )
}