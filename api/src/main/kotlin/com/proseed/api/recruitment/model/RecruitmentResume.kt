package com.proseed.api.recruitment.model

import com.proseed.api.announce.model.Announce
import com.proseed.api.resume.model.Resume
import com.proseed.api.user.model.User
import jakarta.persistence.*

@Entity
@Table(name = "RECRUITMENTRESUME")
class RecruitmentResume(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    // Foreign Key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    val resume: Resume?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruitment_id")
    val recruitment: Recruitment?
) {
    constructor() : this(null,null,null,null)
}