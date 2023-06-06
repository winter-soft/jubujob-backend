package com.proseed.api.resume.dto

import java.time.LocalDateTime

data class ResumeCreateRequestDto(
    val job_id: Long,
    val company_id: Long,
    val announce_id: Long? = null,

    val resume_title: String,
    val resume_detail: String,
    val resume_address: String,
    val resume_startDay: LocalDateTime,
    val resume_endDay: LocalDateTime
)