package com.proseed.api.location

import com.proseed.api.location.model.Location
import org.springframework.data.jpa.repository.JpaRepository

interface LocationRepository : JpaRepository<Location, Long> {

    fun findByLatitudeAndLongitudeAndAddress(latitude: Double, longitude: Double, address: String): Location?
}