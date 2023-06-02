package com.proseed.api.location.model

import com.proseed.api.common.model.TimeZone
import jakarta.persistence.*

@Entity
@Table(name = "LOCATION")
class Location(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var address: String,
    var latitude: Double,
    var longitude: Double
) : TimeZone() {

    constructor() : this (
        id = null,
        address = "",
        latitude = 0.0,
        longitude = 0.0
            )
}