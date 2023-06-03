package com.proseed.api.bookmark.model

import com.proseed.api.announce.model.Announce
import com.proseed.api.common.model.TimeZone
import com.proseed.api.user.model.User
import jakarta.persistence.*
import org.hibernate.annotations.LazyToOne
import org.hibernate.annotations.LazyToOneOption

@Entity
@Table(name = "BOOKMARK")
class Bookmark(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    // Foreign Key
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.DETACH, CascadeType.MERGE])
    @JoinColumn(name = "announce_id")
    @LazyToOne(LazyToOneOption.NO_PROXY)
    val announce: Announce? = null,
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.DETACH, CascadeType.MERGE])
    @JoinColumn(name = "user_id")
    val user: User? = null
) : TimeZone() {

    constructor() : this(
        id = null,
        announce = null,
        user = null
        )
}