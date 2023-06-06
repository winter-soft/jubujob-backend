package com.proseed.api.recruitment.repository

import com.proseed.api.recruitment.dto.RecruitmentUserDefaultDto
import com.proseed.api.recruitment.model.Recruitment

interface RecruitmentRepositoryCustom {
    open fun findByUserAndAnnounce(user_id: Long, announce_id: Long): Recruitment?
    open fun findAllByJobGroup(announce_id: Long): List<List<RecruitmentUserDefaultDto>>
}