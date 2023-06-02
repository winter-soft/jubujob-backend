package com.proseed.api.announce

import com.proseed.api.announce.dto.AnnounceCreateRequestDto
import com.proseed.api.announce.dto.AnnounceUpdateRequestDto
import com.proseed.api.common.aop.RoleCheck
import com.proseed.api.config.exception.user.UserNotFoundException
import com.proseed.api.config.exception.user.UserRegisterStageInValidException
import com.proseed.api.user.model.Role
import com.proseed.api.user.model.User
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/announce")
class AnnounceController(
    val announceService: AnnounceService
) {

    @PostMapping("/")
    fun create(
        @AuthenticationPrincipal user: User,
        @RequestBody(required = true) requestDto: AnnounceCreateRequestDto
    ): ResponseEntity<Any> {

        return ResponseEntity.ok(announceService.create(user, requestDto))
    }

    @GetMapping("/{id}")
    fun select(): ResponseEntity<Any> {
        TODO("Not yet implemented")
    }

    @PatchMapping("/{announce_id}")
    fun update(
        @AuthenticationPrincipal user: User,
        @RequestBody(required = true) requestDto: AnnounceUpdateRequestDto,
        @PathVariable announce_id: Long
    ): ResponseEntity<Any> {
        return ResponseEntity.ok(announceService.update(user, requestDto, announce_id))
    }
}