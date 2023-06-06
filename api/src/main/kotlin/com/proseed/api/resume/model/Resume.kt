package com.proseed.api.resume.model

import com.proseed.api.announce.model.Announce
import com.proseed.api.company.model.Company
import com.proseed.api.job.model.Job
import com.proseed.api.user.model.User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "RESUME")
class Resume(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    // Foreign Key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    var job: Job?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    var company: Company?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "announce_id")
    val announce: Announce? = null,

    // Parameter
    var title: String,
    var detail: String,
    var address: String,
    var certificationCheck: Boolean ? = false,
    var updateCheck: Boolean? = false,
    var startDay: LocalDateTime = LocalDateTime.now(),
    var endDay: LocalDateTime = LocalDateTime.now().plusDays(7L)
    ) {
    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        "",
        "",
        ""
    )
}