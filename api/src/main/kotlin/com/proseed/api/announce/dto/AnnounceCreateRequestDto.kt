package com.proseed.api.announce.dto

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class AnnounceCreateRequestDto(
    // location 정보
    val location_address: String,
    val location_latitude: Double,
    val location_longitude: Double,

    // company 정보
    val company_name: String,

    // announce 정보
    val announce_type: String, // RECRUIT or CLASS
    val announce_title: String,
    val announce_detail: String,
    val announce_imageUrl: String,
    val announce_startDay: LocalDateTime,
    val announce_endDay: LocalDateTime
)