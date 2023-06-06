package com.proseed.api.recruitment.dto

import com.proseed.api.recruitment.model.Recruitment
import com.proseed.api.recruitment.model.RecruitmentStatus
import com.proseed.api.user.model.User

data class RecruitmentUserDefaultDto(
    val user_id: Long,
    val user_nickName: String,

    val recruitment_id: Long,
    val recruitment_status: RecruitmentStatus,
) {
    constructor() : this(0L, "",0L, RecruitmentStatus.RECEPTION)
    constructor(user: User, recruitment: Recruitment) : this(
        user_id = user.id!!,
        user_nickName = user.nickName,

        recruitment_id = recruitment.id!!,
        recruitment_status = recruitment.status!!
    )
}