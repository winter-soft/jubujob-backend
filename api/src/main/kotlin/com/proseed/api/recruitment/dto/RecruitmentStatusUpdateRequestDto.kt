package com.proseed.api.recruitment.dto

data class RecruitmentStatusUpdateRequestDto(
    val recruitment_status: String,
    val user_id: Long
)
