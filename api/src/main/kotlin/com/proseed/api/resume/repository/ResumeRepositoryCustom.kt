package com.proseed.api.resume.repository

import com.proseed.api.resume.dto.ResumeDefaultResponseDto
import com.proseed.api.resume.model.Resume
import com.proseed.api.user.model.User

interface ResumeRepositoryCustom {

    open fun findJoinAll(resume_id: Long, _user: User): Resume?
    open fun findAllByJobGroup(_user: User): MutableList<MutableList<ResumeDefaultResponseDto>>
}