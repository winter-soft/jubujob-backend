package com.proseed.api.announce.model

import com.proseed.api.common.model.TimeZone
import com.proseed.api.company.model.Company
import com.proseed.api.location.model.Location
import com.proseed.api.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "ANNOUNCE")
class Announce(
    @Id @GeneratedValue
    val id: Long? = null,

    // Foreign Key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User?, // Not null
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    var location: Location? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    val company: Company?, // Not null

    // Column
    @Enumerated(EnumType.STRING)
    val type: AnnounceType,
    var title: String,
    @Lob
    var detail: String,
    var imageUrl: String
) : TimeZone() {

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