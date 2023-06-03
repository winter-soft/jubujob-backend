package com.proseed.api.announce.dto

import com.proseed.api.announce.model.Announce
import com.proseed.api.company.dto.CompanyDefaultDto
import com.proseed.api.company.model.Company
import com.proseed.api.config.exception.company.CompanyNotFoundException
import com.proseed.api.config.exception.location.LocationNotFoundException
import com.proseed.api.config.exception.user.UserNotFoundException
import com.proseed.api.location.dto.LocationDefaultDto
import com.proseed.api.location.model.Location
import com.proseed.api.user.dto.UserDefaultDto
import com.proseed.api.user.model.User
import com.querydsl.core.annotations.QueryProjection
import jakarta.persistence.Lob

data class AnnounceDefaultResponseDto(
    val user: UserDefaultDto,
    val location: LocationDefaultDto,
    val company: CompanyDefaultDto,

    val announce_type: String,
    val announce_title: String,
    val announce_detail: String,
    val announce_imageUrl: String
) {
    @QueryProjection
    constructor(announce: Announce) : this(
        user = UserDefaultDto(announce?.user ?: throw UserNotFoundException()),
        location = LocationDefaultDto(announce?.location ?: throw LocationNotFoundException()),
        company = CompanyDefaultDto(announce?.company ?: throw CompanyNotFoundException()),

        announce_type = announce.type.toString(),
        announce_title = announce.title,
        announce_detail = announce.detail,
        announce_imageUrl = announce.imageUrl
    )
}