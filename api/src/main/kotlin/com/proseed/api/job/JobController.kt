package com.proseed.api.job

import com.proseed.api.bookmark.dto.BookmarkCreateRequestDto
import com.proseed.api.job.dto.JobCreateRequestDto
import com.proseed.api.user.model.User
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/job")
class JobController(
    val jobService: JobService
) {

    @Operation(summary = "JOB의 Id를 통해 JOB 1개를 조회")
    @GetMapping("/{job_id}")
    fun select(
        @PathVariable("job_id") job_id: Long
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(jobService.select(job_id))
    }

    // 직업 리스트
    @Operation(summary = "JOB 리스트를 가져옴")
    @GetMapping("/")
    fun selectAll(
        pageable: Pageable
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(jobService.selectAll(pageable))
    }

    // 사용자의 선호 직업
    @Operation(summary = "사용자의 Preference를 통해 JOB을 가져옴")
    @GetMapping("/preference/")
    fun preferenceJob(
        @AuthenticationPrincipal user: User,
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(jobService.preferenceJob(user))
    }

    @Operation(summary = "JOB 생성")
    @PostMapping("/")
    fun create(
        @AuthenticationPrincipal user: User,
        @RequestBody requestDto: JobCreateRequestDto
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(jobService.create(user, requestDto))
    }
}