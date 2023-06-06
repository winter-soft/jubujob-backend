package com.proseed.api.announce.dto

import com.proseed.api.announce.model.Announce
import com.proseed.api.config.exception.announce.AnnounceNotFoundException
import java.time.LocalDateTime

data class AnnounceCreateResponseDto(
    val announce_id: Long,
    val announce_type: String, // RECRUIT or CLASS
    val announce_title: String,
    val announce_detail: String,
    val announce_imageUrl: String,
    val announce_startDay: LocalDateTime,
    val announce_endDay: LocalDateTime
) {
    constructor(announce: Announce) : this(
        announce_id = announce?.id ?: throw AnnounceNotFoundException(),
        announce_type = announce.type.name,
        announce_title = announce.title,
        announce_detail = announce.detail,
        announce_imageUrl = announce.imageUrl,
        announce_startDay = announce.startDay,
        announce_endDay = announce.endDay
    )
}