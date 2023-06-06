package com.proseed.api.resume.repository

import com.proseed.api.announce.model.QAnnounce.Companion.announce
import com.proseed.api.company.dto.CompanyDefaultDto
import com.proseed.api.company.model.QCompany.Companion.company
import com.proseed.api.job.model.Job
import com.proseed.api.job.model.QJob.Companion.job
import com.proseed.api.resume.dto.ResumeDefaultResponseDto
import com.proseed.api.resume.model.QResume.Companion.resume
import com.proseed.api.resume.model.Resume
import com.proseed.api.user.dto.UserDefaultDto
import com.proseed.api.user.model.QUser.Companion.user
import com.proseed.api.user.model.User
import com.querydsl.core.group.GroupBy.groupBy
import com.querydsl.core.group.GroupBy.list
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory

class ResumeRepositoryCustomImpl(
    val queryFactory: JPAQueryFactory
) : ResumeRepositoryCustom {

    override fun findJoinAll(resume_id: Long, _user: User): Resume? {
        return queryFactory
            .selectFrom(resume)
            .innerJoin(resume.job, job).fetchJoin()
            .innerJoin(resume.user, user).fetchJoin()
            .innerJoin(resume.company, company).fetchJoin()
            .leftJoin(resume.announce, announce).fetchJoin()
            .where(resume.id.eq(resume_id)
                .and(user.eq(_user)))
            .fetchOne()
    }

    override fun findAllByJobGroup(_user: User): MutableList<MutableList<ResumeDefaultResponseDto>> {
        return queryFactory
            .from(resume)
            .join(resume.job, job)
            .join(resume.user, user)
            .join(resume.company, company)
            .orderBy(job.id.asc())
            .orderBy(resume.id.desc())
            .transform(
                groupBy(job.id).list(
                    list(
                        Projections.fields(
                            ResumeDefaultResponseDto::class.java,
                            Projections.fields(
                                UserDefaultDto::class.java,
                                user.profileImageUrl.`as`("user_profileImageUrl"),
                                user.nickName.`as`("user_nickName")
                            ).`as`("user"),
                            Projections.fields(
                                Job::class.java,
                                job.id.`as`("id"),
                                job.name.`as`("name")
                            ).`as`("job"),
                            Projections.fields(
                                CompanyDefaultDto::class.java,
                                company.name.`as`("company_name"),
                                company.imageUrl.`as`("company_imageUrl"),
                                company.introduction.`as`("company_introduction")
                            ).`as`("company"),
                            resume.id.`as`("resume_id"),
                            resume.title.`as`("resume_title"),
                            resume.detail.`as`("resume_detail"),
                            resume.address.`as`("resume_address"),
                            resume.certificationCheck.`as`("resume_certificationCheck"),
                            resume.updateCheck.`as`("resume_updateCheck"),
                            resume.startDay.`as`("resume_startDay"),
                            resume.endDay.`as`("resume_endDay")
                        )
                    )
                )
            )
    }
}