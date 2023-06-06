package com.proseed.api.job.repository

import com.proseed.api.announce.model.Announce
import com.proseed.api.announce.model.AnnounceType
import com.proseed.api.announce.model.QAnnounce
import com.proseed.api.company.model.QCompany
import com.proseed.api.job.model.Job
import com.proseed.api.job.model.QJob.Companion.job
import com.proseed.api.location.model.QLocation
import com.proseed.api.user.model.QUser
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class JobRepositoryCustomImpl(
    val queryFactory: JPAQueryFactory
) : JobRepositoryCustom {

    override fun findSearchAll(pageable: Pageable): Page<Job> {
        val query = queryFactory
            .selectFrom(job)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetchResults()

        return PageImpl(query.results, pageable, query.total)
    }

    override fun findJobOnList(jobList: List<String>): List<Job> {
        return queryFactory
            .selectFrom(job)
            .where(job.name.`in`(jobList))
            .fetch()
    }
}