package com.proseed.api.resume

import com.proseed.api.resume.dto.ResumeCreateRequestDto
import com.proseed.api.resume.dto.ResumeUpdateRequestDto
import com.proseed.api.user.model.User
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/resume")
class ResumeController(
    val resumeService: ResumeService
) {
    @Operation(summary = "유저의 이력사항을 단일 조회")
    @GetMapping("/{resume_id}")
    fun select(
        @AuthenticationPrincipal user: User,
        @PathVariable("resume_id") resume_id: Long
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(resumeService.select(user, resume_id))
    }

    @Operation(summary = "JOB별 이력사항")
    @GetMapping("/")
    fun selectAllByGroup(
        @AuthenticationPrincipal user: User
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(resumeService.selectAllByGroup(user))
    }

    @Operation(summary = "유저의 이력사항을 생성")
    @PostMapping("/")
    fun create(
        @AuthenticationPrincipal user: User,
        @RequestBody requestDto: ResumeCreateRequestDto
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(resumeService.create(user, requestDto))
    }

    @Operation(summary = "유저의 이력사항을 수정")
    @PatchMapping("/")
    fun update(
        @AuthenticationPrincipal user: User,
        @RequestBody requestDto: ResumeUpdateRequestDto
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(resumeService.update(user, requestDto))
    }
}