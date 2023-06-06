package com.proseed.api.recruitment.dto

import com.proseed.api.recruitment.model.Recruitment
import com.proseed.api.recruitment.model.RecruitmentStatus

data class RecruitmentDefaultDto(
    val recruitment_id: Long,
    val recruitment_status: RecruitmentStatus,
) {
    constructor() : this(0L,RecruitmentStatus.RECEPTION)
    constructor(recruitment: Recruitment) : this(
        recruitment_id = recruitment.id!!,
        recruitment_status = recruitment.status!!
    )
}