package com.proseed.api.job.repository

import com.proseed.api.job.model.Job
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface JobRepositoryCustom {
    open fun findSearchAll(pageable: Pageable): Page<Job>
    open fun findJobOnList(jobList: List<String>): List<Job>
}