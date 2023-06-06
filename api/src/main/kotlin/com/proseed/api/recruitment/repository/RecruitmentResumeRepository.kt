package com.proseed.api.recruitment.repository

import com.proseed.api.recruitment.model.RecruitmentResume
import org.springframework.data.jpa.repository.JpaRepository

interface RecruitmentResumeRepository : JpaRepository<RecruitmentResume, Long> {
}