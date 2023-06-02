package com.proseed.api.announce.repository

import com.proseed.api.announce.model.Announce

interface AnnounceRepositoryCustom {

    open fun findByIdJoinAll(id: Long): Announce? // 모든게 조인된 Announce 리턴

}