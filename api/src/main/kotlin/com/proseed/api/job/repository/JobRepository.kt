package com.proseed.api.job.repository

import com.proseed.api.job.model.Job
import org.springframework.data.jpa.repository.JpaRepository

interface JobRepository : JpaRepository<Job, Long>, JobRepositoryCustom {

    fun findByName(name: String): Job?
}