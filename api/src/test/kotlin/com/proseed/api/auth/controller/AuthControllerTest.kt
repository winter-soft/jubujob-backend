package com.proseed.api.auth.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.proseed.api.auth.dto.AuthRegisterRequest
import com.proseed.api.auth.dto.AuthRequest
import com.proseed.api.auth.service.AuthService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
internal class AuthControllerTest(
    @Autowired val authService: AuthService,
    @Autowired var mockMvc: MockMvc
) {

    @Test
    fun `register 테스트`() {
        val register = AuthRegisterRequest("hongchanhui", "ghdcksgml2@naver.com", "1234567", "KAKAO")
        val registerJson = ObjectMapper().writeValueAsString(register)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerJson)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andDo(MockMvcResultHandlers.print())
    }

//    @Test
//    fun `authenticate 테스트`() {
//        val register = AuthRegisterRequest("hongchanhui", "ghdcksgml2@naver.com", "123456722", "KAKAO")
//        authService.register(register)
//
//        val authenticate = AuthRequest("ghdcksgml2@naver.com", "123456722")
//        val authenticateJson = ObjectMapper().writeValueAsString(authenticate)
//
//        mockMvc.perform(
//            MockMvcRequestBuilders.post("/auth/authenticate")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(authenticateJson)
//        )
//            .andExpect(MockMvcResultMatchers.status().isOk)
//            .andDo(MockMvcResultHandlers.print())
//    }

    @Test
    fun `kakoLoginPage 테스트`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/auth/kakao/loginPage")
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                jsonPath("\$.loginPage").isNotEmpty
            )
            .andDo(MockMvcResultHandlers.print())
    }

}