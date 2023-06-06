package com.proseed.api.announce.repository

import com.proseed.api.announce.dto.AnnounceDefaultResponseDto
import com.proseed.api.announce.model.Announce
import com.proseed.api.announce.model.AnnounceType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface AnnounceRepositoryCustom {

    open fun findByIdJoinAll(id: Long): Announce? // 모든게 조인된 Announce 리턴
    open fun findSearchAll(type: AnnounceType, pageable: Pageable): Page<Announce>
    open fun findByIdAndUser(announce_id: Long, user_id: Long): Announce?
}