//package com.proseed.api.config.jwt
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.http.MediaType
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
//
//@SpringBootTest
//@AutoConfigureMockMvc
//internal class JwtAuthenticationFilterTest(
//    @Autowired var mockMvc: MockMvc
//) {
//    @Test
//    fun `JWT Token objectMapper response test`() {
//        val token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3IiwiaWF0IjoxNjc3NTg0OTk3LCJleHAiOjE2Nzc1ODY0Mzd9.09x0heRo3vnDfDvzIP_XKw_WkoM6aq0CAUXVDc8FFWE"
//
//        mockMvc.perform(
//            MockMvcRequestBuilders.get("/auth/valid")
//                .header("Authorization", "Bearer " + token) // 존재하지 않는 토큰
//        )
//            .andExpect(status().isForbidden)
//            .andExpect(
//                jsonPath("\$.message").isNotEmpty
//            )
//            .andDo(MockMvcResultHandlers.print())
//    }
//
//}