package com.proseed.api.bookmark.repository

import com.proseed.api.announce.dto.AnnounceDefaultResponseDto
import com.proseed.api.announce.model.Announce
import com.proseed.api.announce.model.AnnounceType
import com.proseed.api.announce.model.QAnnounce.Companion.announce
import com.proseed.api.bookmark.model.Bookmark
import com.proseed.api.bookmark.model.QBookmark.Companion.bookmark
import com.proseed.api.company.dto.CompanyDefaultDto
import com.proseed.api.company.model.QCompany.Companion.company
import com.proseed.api.location.dto.LocationDefaultDto
import com.proseed.api.location.model.QLocation.Companion.location
import com.proseed.api.user.dto.UserDefaultDto
import com.proseed.api.user.model.QUser.Companion.user
import com.proseed.api.user.model.User
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

class BookmarkRepositoryCustomImpl(
    val queryFactory: JPAQueryFactory
) : BookmarkRepositoryCustom {

    override fun findByUserIdAndAnnounceIdWithAll(_user: User, _announce: Announce): Bookmark? {
        return queryFactory
            .selectFrom(bookmark)
            .innerJoin(bookmark.user, user).fetchJoin()
            .innerJoin(bookmark.announce, announce).fetchJoin()
            .where(user.eq(_user)
                .and(announce.eq(_announce)))
            .fetchFirst()
    }

    override fun findSearchAll(_user: User, type: AnnounceType, pageable: Pageable): Page<AnnounceDefaultResponseDto> {
        val query = queryFactory
            .select(
                Projections.fields(
                        AnnounceDefaultResponseDto::class.java,
                    Projections.fields(
                        UserDefaultDto::class.java,
                        user.profileImageUrl.`as`("user_profileImageUrl"),
                        user.nickName.`as`("user_nickName")
                    ).`as`("user"),
                    Projections.fields(
                        LocationDefaultDto::class.java,
                        location.address.`as`("address"),
                        location.latitude.`as`("latitude"),
                        location.longitude.`as`("longitude")
                    ).`as`("location"),
                    Projections.fields(
                        CompanyDefaultDto::class.java,
                        company.name.`as`("company_name"),
                        company.imageUrl.`as`("company_imageUrl"),
                        company.introduction.`as`("company_introduction")
                    ).`as`("company"),
                    announce.id.`as`("announce_id"),
                    announce.type.`as`("announce_type"),
                    announce.title.`as`("announce_title"),
                    announce.detail.`as`("announce_detail"),
                    announce.imageUrl.`as`("announce_imageUrl"),
                    announce.startDay.`as`("announce_startDay"),
                    announce.endDay.`as`("announce_endDay")
                )
            )
            .from(bookmark)
            .innerJoin(bookmark.announce, announce)
            .innerJoin(announce.user, user)
            .innerJoin(announce.company, company)
            .innerJoin(announce.location, location)
            .where(announce.type.eq(type)
                .and(bookmark.user.eq(_user)))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        return PageImpl(query, pageable, query.size.toLong())
    }
}