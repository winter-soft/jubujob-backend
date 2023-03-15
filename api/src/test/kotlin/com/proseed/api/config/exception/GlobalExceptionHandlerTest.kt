package com.proseed.api.config.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.proseed.api.auth.dto.AuthRegisterRequest
import com.proseed.api.auth.dto.AuthRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

@SpringBootTest
@AutoConfigureMockMvc
internal class GlobalExceptionHandlerTest(
    @Autowired var mockMvc: MockMvc
) {

    @Test
    fun `Controller Advice Hook Check`() {
        val authenticate = AuthRequest("test@naver.com", "1234") // 존재하지 않는 계정
        val authenticateJson = ObjectMapper().writeValueAsString(authenticate)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authenticateJson)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(
                jsonPath("\$.message").value("유저 정보를 찾지 못했습니다.")
            )
            .andDo(MockMvcResultHandlers.print())

    }

}