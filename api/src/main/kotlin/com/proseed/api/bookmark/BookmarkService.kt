package com.proseed.api.bookmark

import com.proseed.api.announce.model.Announce
import com.proseed.api.announce.repository.AnnounceRepository
import com.proseed.api.bookmark.dto.BookmarkCreateRequestDto
import com.proseed.api.bookmark.model.Bookmark
import com.proseed.api.bookmark.repository.BookmarkRepository
import com.proseed.api.config.exception.announce.AnnounceNotFoundException
import com.proseed.api.user.model.User
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class BookmarkService(
    val announceRepository: AnnounceRepository,
    val bookmarkRepository: BookmarkRepository
) {

    @Transactional
    fun create(user: User, requestDto: BookmarkCreateRequestDto): Any {
        val announce = announceRepository.findById(requestDto.announce_id).orElseThrow{
            throw AnnounceNotFoundException()
        }

        val bookmark = bookmarkRepository.findByUserIdAndAnnounceIdWithAll(user, announce)

        if (bookmark != null) {
            bookmarkRepository.delete(bookmark)
        } else {
            bookmarkRepository.saveAndFlush(
                Bookmark(
                    user = user,
                    announce = announce
                )
            )
        }

        return "ok"
    }
}