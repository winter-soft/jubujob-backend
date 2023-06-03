package com.proseed.api.announce.model

import com.proseed.api.config.exception.announce.announcetype.AnnounceTypeIllegalArgumentException

enum class AnnounceType {
    RECRUIT, // 채용
    CLASS; // 교육

    companion object {
        fun customValueOf(value: String): AnnounceType {
            return values().find { it.name.equals(value, ignoreCase = true) }
                ?: throw AnnounceTypeIllegalArgumentException()
        }
    }
}