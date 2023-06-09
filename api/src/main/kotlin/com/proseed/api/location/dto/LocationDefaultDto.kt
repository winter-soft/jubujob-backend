package com.proseed.api.location.dto

import com.proseed.api.location.model.Location

data class LocationDefaultDto(
    val address: String,
    val latitude: Double,
    val longitude: Double
) {
    constructor() : this("", 0.0 ,0.0)
    constructor(location: Location) : this(
        address = location.address,
        latitude = location.latitude,
        longitude = location.longitude
    )
}