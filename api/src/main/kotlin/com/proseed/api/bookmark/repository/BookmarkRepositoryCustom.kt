package com.proseed.api.bookmark.repository

import com.proseed.api.announce.dto.AnnounceDefaultResponseDto
import com.proseed.api.announce.model.Announce
import com.proseed.api.announce.model.AnnounceType
import com.proseed.api.bookmark.model.Bookmark
import com.proseed.api.user.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BookmarkRepositoryCustom {

    open fun findByUserIdAndAnnounceIdWithAll(_user: User, _announce: Announce): Bookmark?
    open fun findSearchAll(_user: User, type: AnnounceType, pageable: Pageable): Page<AnnounceDefaultResponseDto>
}