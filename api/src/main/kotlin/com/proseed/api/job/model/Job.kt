package com.proseed.api.job.model

import com.proseed.api.common.model.TimeZone
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "JOB")
class Job(
    @Id @GeneratedValue
    val id: Long? = null,

    // Parameter
    var name: String
) : TimeZone() {

    constructor() : this(
        name = ""
        )
}