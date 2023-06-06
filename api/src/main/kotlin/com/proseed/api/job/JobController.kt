package com.proseed.api.job

import com.proseed.api.bookmark.dto.BookmarkCreateRequestDto
import com.proseed.api.job.dto.JobCreateRequestDto
import com.proseed.api.user.model.User
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/job")
class JobController(
    val jobService: JobService
) {

    @GetMapping("/{job_id}")
    fun select(
        @PathVariable("job_id") job_id: Long
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(jobService.select(job_id))
    }

    // 직업 리스트
    @GetMapping("/")
    fun selectAll(
        pageable: Pageable
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(jobService.selectAll(pageable))
    }

    // 사용자의 선호 직업
    @GetMapping("/preference/")
    fun preferenceJob(
        @AuthenticationPrincipal user: User,
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(jobService.preferenceJob(user))
    }

    @PostMapping("/")
    fun create(
        @AuthenticationPrincipal user: User,
        @RequestBody requestDto: JobCreateRequestDto
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(jobService.create(user, requestDto))
    }
}