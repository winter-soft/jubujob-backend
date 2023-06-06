package com.proseed.api.bookmark

import com.proseed.api.bookmark.dto.BookmarkCreateRequestDto
import com.proseed.api.user.model.User
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bookmark")
class BookmarkController(
    val bookmarkService: BookmarkService
) {
    @Operation(summary = "ANNOUNCE의 Id를 통해 해당 사용자가 북마크를 등록했는지 가져옴")
    @GetMapping("/{announce_id}")
    fun select(
        @AuthenticationPrincipal user: User,
        @PathVariable("announce_id") announce_id: Long
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(bookmarkService.select(user, announce_id))
    }

    // 사용자의 관심 공고
    @Operation(summary = "JWT 토큰으로 사용자의 관심 공고를 가져옴")
    @GetMapping("/")
    fun selectAll(
        @AuthenticationPrincipal user: User,
        @RequestParam("type") type: String,
        pageable: Pageable
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(bookmarkService.selectAll(user, type, pageable))
    }

    @Operation(summary = "BOOKMARK 추가, 이미 등록되어있는 상태면 삭제")
    @PostMapping("/")
    fun create(
        @AuthenticationPrincipal user: User,
        @RequestBody requestDto: BookmarkCreateRequestDto
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(bookmarkService.create(user, requestDto))
    }
}