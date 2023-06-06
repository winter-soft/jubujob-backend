package com.proseed.api.bookmark.repository

import com.proseed.api.announce.model.Announce
import com.proseed.api.bookmark.model.Bookmark
import com.proseed.api.user.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface BookmarkRepository : JpaRepository<Bookmark, Long>, BookmarkRepositoryCustom {

    fun findByUserAndAnnounce(user: User, announce: Announce): Bookmark?
}