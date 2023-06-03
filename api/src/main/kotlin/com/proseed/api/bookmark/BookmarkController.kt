package com.proseed.api.bookmark

import com.proseed.api.bookmark.dto.BookmarkCreateRequestDto
import com.proseed.api.user.model.User
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/bookmark")
class BookmarkController(
    val bookmarkService: BookmarkService
) {

    @PostMapping("/")
    fun create(
        @AuthenticationPrincipal user: User,
        @RequestBody requestDto: BookmarkCreateRequestDto
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(bookmarkService.create(user, requestDto))
    }
}