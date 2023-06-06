package com.proseed.api.resume.dto

import java.time.LocalDateTime

data class ResumeUpdateRequestDto(
    val job_id: Long? = null,
    val company_id: Long? = null,

    val resume_id: Long,
    val resume_title: String? = null,
    val resume_detail: String? = null,
    val resume_address: String? = null,
    val resume_startDay: LocalDateTime,
    val resume_endDay: LocalDateTime,
) {
}