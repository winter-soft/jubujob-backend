package com.proseed.api.announce.repository

import com.proseed.api.announce.model.Announce
import com.proseed.api.announce.model.QAnnounce.Companion.announce
import com.proseed.api.company.model.QCompany.Companion.company
import com.proseed.api.location.model.QLocation.Companion.location
import com.proseed.api.user.model.QUser.Companion.user
import com.querydsl.jpa.impl.JPAQueryFactory

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

}