package com.proseed.api

import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {

    @GetMapping("/")
    @Operation(hidden = true)
    fun healthCheck(): String {
        return "OK"
    }
}