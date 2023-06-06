package com.proseed.api.announce.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.proseed.api.common.model.TimeZone
import com.proseed.api.company.model.Company
import com.proseed.api.location.model.Location
import com.proseed.api.user.model.User
import jakarta.persistence.*
import org.hibernate.annotations.LazyToOne
import org.hibernate.annotations.LazyToOneOption
import java.time.LocalDateTime

@Entity
@Table(name = "ANNOUNCE")
class Announce(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    // Foreign Key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User?, // Not null
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    var location: Location?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    val company: Company?, // Not null

    // Column
    @Enumerated(EnumType.STRING)
    val type: AnnounceType,
    var title: String,
    @Lob
    var detail: String,
    var imageUrl: String,
    var startDay: LocalDateTime = LocalDateTime.now(),
    var endDay: LocalDateTime = LocalDateTime.now().plusDays(7L)
) : TimeZone() {
    // 29536-6380552201a2145d872c
    constructor() : this(
        id = null,
        user = null,
        location = null,
        company = null,
        type = AnnounceType.RECRUIT,
        title = "",
        detail = "",
        imageUrl = ""
        )
}