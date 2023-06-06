package com.proseed.api.recruitment

import com.proseed.api.recruitment.dto.RecruitmentCreateRequestDto
import com.proseed.api.recruitment.dto.RecruitmentSelectRequestDto
import com.proseed.api.recruitment.dto.RecruitmentStatusUpdateRequestDto
import com.proseed.api.user.model.User
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/recruit")
class RecruitmentController(
    val recruitmentService: RecruitmentService
) {
    @Operation(summary = "RECRUITMENT 단일조회")
    @GetMapping("/detail/{announce_id}")
    fun select(
        @AuthenticationPrincipal user: User,
        @PathVariable("announce_id") announce_id: Long,
        @RequestBody requestDto: RecruitmentSelectRequestDto
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(recruitmentService.select(announce_id, user, requestDto))
    }

    @Operation(summary = "RECRUITMENT_STATUS별 지원자조회")
    @GetMapping("/{announce_id}")
    fun selectAllGroupBy(
        @AuthenticationPrincipal user: User,
        @PathVariable("announce_id") announce_id: Long
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(recruitmentService.selectAllGroupBy(announce_id, user))
    }

    @Operation(summary = "공고 지원하기")
    @PostMapping("/{announce_id}")
    fun create(
        @AuthenticationPrincipal user: User,
        @PathVariable("announce_id") announce_id: Long,
        @RequestBody requestDto: RecruitmentCreateRequestDto
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(recruitmentService.create(announce_id , user, requestDto))
    }

    @Operation(summary = "상태 변경하기")
    @PatchMapping("/{announce_id}")
    fun update(
        @AuthenticationPrincipal user: User,
        @PathVariable("announce_id") announce_id: Long,
        @RequestBody requestDto: RecruitmentStatusUpdateRequestDto
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(recruitmentService.update(announce_id, user, requestDto))
    }
}