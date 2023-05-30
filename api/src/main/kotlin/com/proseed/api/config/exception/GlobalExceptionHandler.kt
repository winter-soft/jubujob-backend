package com.proseed.api.config.exception

import com.proseed.api.config.exception.user.UserNotRegisterException
import com.proseed.api.utils.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice
@RestController
class GlobalExceptionHandler {
    private val logger = logger<GlobalExceptionHandler>()

    @ExceptionHandler(value = [ApiException::class])
    fun apiException(exception: ApiException): ResponseEntity<ExceptionDto> {
        logger.info("ApiException Running..")

        return ResponseEntity(ExceptionDto(message = exception.message ?: "에러발생"), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    fun bindingException(exception: HttpMessageNotReadableException): ResponseEntity<ExceptionDto> {
        logger.info("HttpMessageNotReadableException Running..")

        return ResponseEntity(ExceptionDto(message = exception.message ?: "에러발생"), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [UserNotRegisterException::class])
    fun userNotRegisterException(exception: UserNotRegisterException): ResponseEntity<UserNotRegisterException.UserRegisterDto> {
        logger.info("UserNotRegisterException Running..")

        return ResponseEntity(exception.errorMessage(), HttpStatus.BAD_REQUEST)
    }
}