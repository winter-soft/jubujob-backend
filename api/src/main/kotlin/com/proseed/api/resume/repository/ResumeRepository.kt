package com.proseed.api.resume.repository

import com.proseed.api.resume.model.Resume
import org.springframework.data.jpa.repository.JpaRepository

interface ResumeRepository : JpaRepository<Resume, Long>, ResumeRepositoryCustom {

}