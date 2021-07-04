package com.example.servicescheduleapp.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(NotFoundException::class)
    protected fun handleResourceNotFound(ex: NotFoundException): ResponseEntity<Any> {
        return ResponseEntity<Any>(ex.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(BadRequestException::class)
    protected fun handleResourceNotFound(ex: BadRequestException): ResponseEntity<Any> {
        return ResponseEntity<Any>(ex.message, HttpStatus.BAD_REQUEST)
    }
}