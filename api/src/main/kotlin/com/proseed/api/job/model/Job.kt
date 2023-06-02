package com.proseed.api.job.model

import com.proseed.api.common.model.TimeZone
import jakarta.persistence.*

@Entity
@Table(name = "JOB")
class Job(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    // Parameter
    var name: String
) : TimeZone() {

    constructor() : this(
        name = ""
        )
}