package com.proseed.api.announce.repository

import com.proseed.api.announce.dto.AnnounceDefaultResponseDto
import com.proseed.api.announce.model.Announce
import com.proseed.api.announce.model.AnnounceType
import com.proseed.api.announce.model.QAnnounce.Companion.announce
import com.proseed.api.company.model.QCompany.Companion.company
import com.proseed.api.location.model.QLocation.Companion.location
import com.proseed.api.user.dto.UserDefaultDto
import com.proseed.api.user.model.QUser.Companion.user
import com.querydsl.core.group.GroupBy.groupBy
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class AnnounceRepositoryCustomImpl(
    val queryFactory: JPAQueryFactory
) : AnnounceRepositoryCustom {

    override fun findByIdJoinAll(id: Long): Announce? {
        return queryFactory
            .selectFrom(announce)
            .innerJoin(announce.location, location).fetchJoin()
            .innerJoin(announce.user, user).fetchJoin()
            .innerJoin(announce.company, company).fetchJoin()
            .where(announce.id.eq(id))
            .fetchOne()
    }

    override fun findSearchAll(type: AnnounceType, pageable: Pageable): Page<Announce> {
        val query = queryFactory
            .selectFrom(announce)
            .innerJoin(announce.location, location).fetchJoin()
            .innerJoin(announce.user, user).fetchJoin()
            .innerJoin(announce.company, company).fetchJoin()
            .where(announce.type.eq(type))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(announce.id.desc())
            .fetchResults()

        return PageImpl(query.results, pageable, query.total)
    }

    override fun findByIdAndUser(announce_id: Long, user_id: Long): Announce? {
        return queryFactory
            .selectFrom(announce)
            .innerJoin(announce.user, user).fetchJoin()
            .where(announce.id.eq(announce_id)
                .and(user.id.eq(user_id)))
            .fetchOne()
    }
}