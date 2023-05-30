package com.proseed.api.announce

import com.proseed.api.announce.model.Announce
import org.springframework.data.jpa.repository.JpaRepository

interface AnnounceRepository : JpaRepository<Announce, Long> {

}