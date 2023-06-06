package com.proseed.api.announce

import com.proseed.api.announce.dto.AnnounceCreateRequestDto
import com.proseed.api.announce.dto.AnnounceUpdateRequestDto
import com.proseed.api.common.aop.RoleCheck
import com.proseed.api.config.exception.user.UserNotFoundException
import com.proseed.api.config.exception.user.UserRegisterStageInValidException
import com.proseed.api.user.model.Role
import com.proseed.api.user.model.User
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/announce")
class AnnounceController(
    val announceService: AnnounceService
) {

    @Operation(summary = "ANNOUNCE의 Id를 통해 조회")
    @GetMapping("/{announce_id}")
    fun select(
        @PathVariable announce_id: Long
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(announceService.select(announce_id))
    }

    @Operation(summary = "ANNOUNCE type에 따른 리스트 조회")
    @GetMapping("/")
    fun selectAll(
        @RequestParam("type") type: String,
        pageable: Pageable
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(announceService.selectAll(type, pageable))
    }

    @Operation(summary = "ANNOUNCE 생성")
    @PostMapping("/")
    fun create(
        @AuthenticationPrincipal user: User,
        @RequestBody(required = true) requestDto: AnnounceCreateRequestDto
    ): ResponseEntity<Any> {

        return ResponseEntity.ok(announceService.create(user, requestDto))
    }

    @Operation(summary = "ANNOUNCE의 Id를 통해 수정")
    @PatchMapping("/{announce_id}")
    fun update(
        @AuthenticationPrincipal user: User,
        @RequestBody(required = true) requestDto: AnnounceUpdateRequestDto,
        @PathVariable announce_id: Long
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(announceService.update(user, requestDto, announce_id))
    }
}