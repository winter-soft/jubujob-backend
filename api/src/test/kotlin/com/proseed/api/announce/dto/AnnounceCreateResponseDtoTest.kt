package com.proseed.api.announce.dto

import com.proseed.api.announce.model.Announce
import com.proseed.api.announce.model.AnnounceType
import com.proseed.api.config.exception.announce.AnnounceNotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class AnnounceCreateResponseDtoTest {

    @Test
    fun `Announce id가 Null일때 throw AnnounceNotFoundException`() {
        // given
        val announce = Announce(
            user = null,
            location = null,
            company = null,
            type = AnnounceType.RECRUIT,
            title = "hi",
            detail = "hello",
            imageUrl = "naver.com"
        )

        // when
        assertThrows<AnnounceNotFoundException>{
            AnnounceCreateResponseDto(announce)
        }
    }
}