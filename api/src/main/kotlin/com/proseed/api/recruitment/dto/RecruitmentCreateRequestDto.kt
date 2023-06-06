package com.proseed.api.recruitment.dto

import com.proseed.api.resume.dto.ResumeIdRequestDto
import com.proseed.api.resume.model.Resume

data class RecruitmentCreateRequestDto(
    val resumes: MutableList<ResumeIdRequestDto>
) {
}