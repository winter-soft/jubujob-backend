package com.proseed.api.recruitment.model

import com.proseed.api.announce.model.Announce
import com.proseed.api.common.model.TimeZone
import com.proseed.api.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "RECRUITMENT")
class Recruitment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    // Foreign Key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announce_id")
    val announce: Announce?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User?,

    // Parameter
    @Enumerated(EnumType.STRING)
    var status: RecruitmentStatus? = RecruitmentStatus.RECEPTION
) : TimeZone() {

    constructor() : this(
        id = null,
        announce = null,
        user = null,
        )
}