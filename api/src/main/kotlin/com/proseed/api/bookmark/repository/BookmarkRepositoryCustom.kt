package com.proseed.api.bookmark.repository

import com.proseed.api.announce.model.Announce
import com.proseed.api.bookmark.model.Bookmark
import com.proseed.api.user.model.User

interface BookmarkRepositoryCustom {

    open fun findByUserIdAndAnnounceIdWithAll(_user: User, _announce: Announce): Bookmark?

}