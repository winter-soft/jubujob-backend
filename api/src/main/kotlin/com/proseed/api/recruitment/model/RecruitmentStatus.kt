package com.proseed.api.recruitment.model

import com.proseed.api.announce.model.AnnounceType
import com.proseed.api.config.exception.announce.announcetype.AnnounceTypeIllegalArgumentException
import com.proseed.api.config.exception.recruitment.RecruitmentStatusIllegalArgumentException

enum class RecruitmentStatus {
    RECEPTION,
    REVIEW,
    INTERVIEW,
    CONFIRMED;

    companion object {
        fun customValueOf(value: String): RecruitmentStatus {
            return RecruitmentStatus.values().find { it.name.equals(value, ignoreCase = true) }
                ?: throw RecruitmentStatusIllegalArgumentException()
        }
    }
}