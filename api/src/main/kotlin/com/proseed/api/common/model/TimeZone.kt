package com.proseed.api.common.model

import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import java.time.LocalDateTime

@MappedSuperclass
open class TimeZone(
    var createdTime: LocalDateTime? = null,
    var updatedTime: LocalDateTime? = null
) {

    @PrePersist
    fun createdTime() {
        val now = LocalDateTime.now().withNano(0)
        this.createdTime = now
        this.updatedTime = now
    }

    @PreUpdate
    fun updatedTime() {
        this.updatedTime = LocalDateTime.now().withNano(0)
    }
}