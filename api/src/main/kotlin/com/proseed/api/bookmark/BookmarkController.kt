package com.proseed.api.bookmark

import com.proseed.api.bookmark.dto.BookmarkCreateRequestDto
import com.proseed.api.user.model.User
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bookmark")
class BookmarkController(
    val bookmarkService: BookmarkService
) {
    @GetMapping("/{announce_id}")
    fun select(
        @AuthenticationPrincipal user: User,
        @PathVariable("announce_id") announce_id: Long
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(bookmarkService.select(user, announce_id))
    }

    // 사용자의 관심 공고
    @GetMapping("/")
    fun selectAll(
        @AuthenticationPrincipal user: User,
        @RequestParam("type") type: String,
        pageable: Pageable
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(bookmarkService.selectAll(user, type, pageable))
    }

    @PostMapping("/")
    fun create(
        @AuthenticationPrincipal user: User,
        @RequestBody requestDto: BookmarkCreateRequestDto
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(bookmarkService.create(user, requestDto))
    }
}