package com.proseed.api.company.repository

import com.proseed.api.company.model.CompanyMember
import com.proseed.api.company.model.QCompany.Companion.company
import com.proseed.api.company.model.QCompanyMember.Companion.companyMember
import com.proseed.api.user.model.QUser.Companion.user
import com.proseed.api.user.model.User
import com.querydsl.jpa.impl.JPAQueryFactory

class CompanyMemberRepositoryCustomImpl(
    val queryFactory: JPAQueryFactory
) : CompanyMemberRepositoryCustom {
    override fun findByUserAndName(_user: User, name: String): CompanyMember? {
        return queryFactory
            .selectFrom(companyMember)
            .innerJoin(companyMember.user, user).fetchJoin()
            .innerJoin(companyMember.company, company).fetchJoin()
            .where(user.eq(_user)
                .and(company.name.eq(name)))
            .fetchOne()
    }
}