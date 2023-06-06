package com.proseed.api.announce.dto

import com.proseed.api.announce.model.Announce
import com.proseed.api.announce.model.AnnounceType
import com.proseed.api.company.dto.CompanyDefaultDto
import com.proseed.api.config.exception.announce.AnnounceIdNullPointerException
import com.proseed.api.config.exception.company.CompanyNotFoundException
import com.proseed.api.config.exception.location.LocationNotFoundException
import com.proseed.api.config.exception.user.UserNotFoundException
import com.proseed.api.location.dto.LocationDefaultDto
import com.proseed.api.user.dto.UserDefaultDto

data class AnnounceDefaultResponseDto(
    val user: UserDefaultDto,
    val location: LocationDefaultDto,
    val company: CompanyDefaultDto,

    val announce_id: Long,
    val announce_type: AnnounceType,
    val announce_title: String,
    val announce_detail: String,
    val announce_imageUrl: String
) {
    constructor() : this(UserDefaultDto(), LocationDefaultDto(), CompanyDefaultDto(), 0L,AnnounceType.RECRUIT, "", "","")
    constructor(announce: Announce) : this(
        user = UserDefaultDto(announce?.user ?: throw UserNotFoundException()),
        location = LocationDefaultDto(announce?.location ?: throw LocationNotFoundException()),
        company = CompanyDefaultDto(announce?.company ?: throw CompanyNotFoundException()),

        announce_id = announce?.id ?: throw AnnounceIdNullPointerException(),
        announce_type = announce.type,
        announce_title = announce.title,
        announce_detail = announce.detail,
        announce_imageUrl = announce.imageUrl
    )
}