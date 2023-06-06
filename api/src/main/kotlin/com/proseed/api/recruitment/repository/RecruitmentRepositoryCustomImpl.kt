package com.proseed.api.recruitment.repository

import com.proseed.api.announce.model.QAnnounce.Companion.announce
import com.proseed.api.recruitment.dto.RecruitmentDefaultDto
import com.proseed.api.recruitment.dto.RecruitmentUserDefaultDto
import com.proseed.api.recruitment.model.QRecruitment.Companion.recruitment
import com.proseed.api.recruitment.model.Recruitment
import com.proseed.api.user.model.QUser.Companion.user
import com.proseed.api.user.model.User
import com.querydsl.core.group.GroupBy.groupBy
import com.querydsl.core.group.GroupBy.list
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory

class RecruitmentRepositoryCustomImpl(
    val queryFactory: JPAQueryFactory
) : RecruitmentRepositoryCustom {
    override fun findByUserAndAnnounce(user_id: Long, announce_id: Long): Recruitment? {
        return queryFactory
            .selectFrom(recruitment)
            .join(recruitment.user, user)
            .join(recruitment.announce, announce)
            .where(user.id.eq(user_id)
                .and(announce.id.eq(announce_id)))
            .fetchOne()
    }

    override fun findAllByJobGroup(announce_id: Long): List<List<RecruitmentUserDefaultDto>> {
        return queryFactory
            .from(recruitment)
            .join(recruitment.user, user)
            .join(recruitment.announce, announce)
            .where(announce.id.eq(announce_id))
            .orderBy(recruitment.id.asc())
            .transform(
                groupBy(recruitment.status).list(
                    list(
                        Projections.fields(
                            RecruitmentUserDefaultDto::class.java,
                            user.id.`as`("user_id"),
                            user.nickName.`as`("user_nickName"),
                            recruitment.id.`as`("recruitment_id"),
                            recruitment.status.`as`("recruitment_status")
                        )
                    )
                )
            )
    }
}