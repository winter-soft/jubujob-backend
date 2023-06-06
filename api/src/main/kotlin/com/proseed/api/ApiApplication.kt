package com.proseed.api

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(info= Info(title = "주부의직업 API 명세서"))
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)

}