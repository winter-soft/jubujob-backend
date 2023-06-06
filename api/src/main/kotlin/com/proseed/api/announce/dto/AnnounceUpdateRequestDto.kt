package com.proseed.api.announce.dto

import com.proseed.api.announce.model.AnnounceType
import jakarta.persistence.Lob
import java.time.LocalDateTime

class AnnounceUpdateRequestDto(
    // location 정보
    val location_address: String? = null,
    val location_latitude: Double? = null,
    val location_longitude: Double? = null,

    // announce 정보
    val announce_title: String? = null,
    val announce_detail: String? = null,
    val announce_imageUrl: String? = null,
    val announce_startDay: LocalDateTime? = LocalDateTime.now(),
    val announce_endDay: LocalDateTime? = LocalDateTime.now().plusDays(7L)
) {
}