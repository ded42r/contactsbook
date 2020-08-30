package com.example.contactsbook.controllers

import com.example.contactsbook.createBadRequestErrorResponseEntity
import com.example.contactsbook.createInternalServerErrorResponseEntity
import com.example.contactsbook.createNotFoundErrorResponseEntity
import com.example.contactsbook.createValidationErrorResponseEntity
import com.example.contactsbook.dto.ResponseDTO
import com.example.contactsbook.exceptions.ContactNotFoundException
import com.example.contactsbook.exceptions.NotAcceptableDataException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class MvcExceptionHandler : ResponseEntityExceptionHandler() {

    companion object {
        private const val INTERNAL_SERVER_ERROR = "Internal server error"
        private const val UNKNOWN_ERROR = "Unknown error"
    }

    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        logger.error("Validation error.", ex)
        return ex.createValidationErrorResponseEntity()
    }

    override fun handleMissingServletRequestParameter(ex: MissingServletRequestParameterException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any> {
        logger.error("Parameters error.", ex)
        return ex.createBadRequestErrorResponseEntity()
    }

    @ExceptionHandler(NotAcceptableDataException::class)
    fun handleNotAcceptableDataException(ex: NotAcceptableDataException): ResponseEntity<ResponseDTO<Any>> {
        logger.error("Validation error.", ex)
        return ex.createValidationErrorResponseEntity()
    }

    @ExceptionHandler(ContactNotFoundException::class)
    fun handleContactNotFoundException(ex: ContactNotFoundException): ResponseEntity<ResponseDTO<Any>> {
        logger.error("Contact not found.", ex)
        return ex.createNotFoundErrorResponseEntity()
    }


    @ExceptionHandler(Throwable::class)
    fun handleThrowable(t: Throwable): ResponseEntity<Any> {
        logger.error(INTERNAL_SERVER_ERROR, t)
        return createInternalServerErrorResponseEntity("$INTERNAL_SERVER_ERROR. ${t.message ?: UNKNOWN_ERROR}")
    }

}