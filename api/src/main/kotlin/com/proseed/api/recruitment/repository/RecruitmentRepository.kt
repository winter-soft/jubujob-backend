package com.proseed.api.recruitment.repository

import com.proseed.api.recruitment.model.Recruitment
import org.springframework.data.jpa.repository.JpaRepository

interface RecruitmentRepository : JpaRepository<Recruitment, Long>, RecruitmentRepositoryCustom {
}