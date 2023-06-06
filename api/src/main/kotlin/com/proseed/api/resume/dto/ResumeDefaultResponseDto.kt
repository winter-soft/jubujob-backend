package com.proseed.api.resume.dto

import com.proseed.api.company.dto.CompanyDefaultDto
import com.proseed.api.company.model.Company
import com.proseed.api.config.exception.JobNotFoundException
import com.proseed.api.config.exception.company.CompanyNotFoundException
import com.proseed.api.config.exception.user.UserNotFoundException
import com.proseed.api.job.model.Job
import com.proseed.api.resume.model.Resume
import com.proseed.api.user.dto.UserDefaultDto
import com.proseed.api.user.model.User
import java.time.LocalDateTime

data class ResumeDefaultResponseDto(
    val user: UserDefaultDto,
    val job: Job,
    val company: CompanyDefaultDto,

    val resume_id: Long,
    val resume_title: String,
    val resume_detail: String,
    val resume_address: String,
    val resume_certificationCheck: Boolean,
    val resume_updateCheck: Boolean,
    val resume_startDay: LocalDateTime,
    val resume_endDay: LocalDateTime
){
    constructor() : this(UserDefaultDto(), Job(), CompanyDefaultDto(), 0L, "","","",false,false, LocalDateTime.now(), LocalDateTime.now())
    constructor(resume: Resume) : this(
        user = UserDefaultDto(resume?.user ?: throw UserNotFoundException()),
        job = resume?.job ?: throw JobNotFoundException(),
        company = CompanyDefaultDto(resume?.company ?: throw CompanyNotFoundException()),

        resume_id = resume.id!!,
        resume_title = resume.title,
        resume_detail = resume.detail,
        resume_address = resume.address,
        resume_certificationCheck = resume.certificationCheck!!,
        resume_updateCheck = resume.updateCheck!!,
        resume_startDay = resume.startDay,
        resume_endDay = resume.endDay
    )

}