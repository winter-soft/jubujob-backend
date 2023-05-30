package com.proseed.api.bookmark.model

import com.proseed.api.announce.model.Announce
import com.proseed.api.common.model.TimeZone
import com.proseed.api.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "BOOKMARK")
class Bookmark(
    @Id @GeneratedValue
    val id: Long? = null,

    // Foreign Key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announce_id")
    val announce: Announce?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User?
) : TimeZone() {

    constructor() : this(
        id = null,
        announce = null,
        user = null
        )
}