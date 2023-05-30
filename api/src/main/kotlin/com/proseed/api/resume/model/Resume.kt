package com.proseed.api.resume.model

import com.proseed.api.announce.model.Announce
import com.proseed.api.company.model.Company
import com.proseed.api.job.model.Job
import com.proseed.api.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "RESUME")
class Resume(
    @Id @GeneratedValue
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
    var address: String,

    ) {
}