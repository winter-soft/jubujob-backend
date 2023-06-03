package com.proseed.api.bookmark.repository

import com.proseed.api.announce.model.Announce
import com.proseed.api.announce.model.QAnnounce.Companion.announce
import com.proseed.api.bookmark.model.Bookmark
import com.proseed.api.bookmark.model.QBookmark.Companion.bookmark
import com.proseed.api.config.exception.bookmark.BookmarkQueryException
import com.proseed.api.user.model.QUser.Companion.user
import com.proseed.api.user.model.User
import com.querydsl.jpa.impl.JPAQueryFactory

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

}