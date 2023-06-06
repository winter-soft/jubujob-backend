package com.proseed.api.bookmark

import com.proseed.api.announce.dto.AnnounceDefaultResponseDto
import com.proseed.api.announce.model.Announce
import com.proseed.api.announce.model.AnnounceType
import com.proseed.api.announce.repository.AnnounceRepository
import com.proseed.api.bookmark.dto.BookmarkCreateRequestDto
import com.proseed.api.bookmark.dto.BookmarkSelectResponseDto
import com.proseed.api.bookmark.model.Bookmark
import com.proseed.api.bookmark.repository.BookmarkRepository
import com.proseed.api.config.exception.announce.AnnounceNotFoundException
import com.proseed.api.user.model.User
import jakarta.transaction.Transactional
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookmarkService(
    val announceRepository: AnnounceRepository,
    val bookmarkRepository: BookmarkRepository
) {

    fun select(user: User, announce_id: Long): Any {
        val announce = announceRepository.findAnnounceById(announce_id)
            ?: throw AnnounceNotFoundException()

        val bookmark = bookmarkRepository.findByUserAndAnnounce(user, announce)

        return BookmarkSelectResponseDto(response = bookmark != null)
    }

    fun selectAll(user: User, type: String, pageable: Pageable): Any {
        return bookmarkRepository.findSearchAll(user, AnnounceType.customValueOf(type), pageable)
    }

    @Transactional
    fun create(user: User, requestDto: BookmarkCreateRequestDto): Any {
        val announce = announceRepository.findAnnounceById(requestDto.announce_id)
            ?: throw AnnounceNotFoundException()

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