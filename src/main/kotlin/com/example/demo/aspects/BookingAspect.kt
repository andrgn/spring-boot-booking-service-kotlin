package com.example.demo.aspects

import com.example.demo.utils.ApiError
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class BookingAspect : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors = arrayListOf<String>()
        ex.bindingResult.fieldErrors.map { errors.add("${it.field}: ${it.defaultMessage}") }
        ex.bindingResult.globalErrors.map { errors.add("${it.objectName}: ${it.defaultMessage}") }
        val apiError = ApiError(HttpStatus.BAD_REQUEST, errors)

        return handleExceptionInternal(ex, apiError, headers, apiError.status, request)
    }

    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ) : ResponseEntity<Any> {
        val apiError = ApiError(
            HttpStatus.BAD_REQUEST,
            "${ex.parameterName} parameter is missing")

        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    override fun handleHttpRequestMethodNotSupported(
        ex: HttpRequestMethodNotSupportedException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
        ) : ResponseEntity<Any> {
        val apiError = ApiError(
            HttpStatus.METHOD_NOT_ALLOWED,
            "${ex.method} method isn't supported for this request")

        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatch(
        ex: MethodArgumentTypeMismatchException
    ) : ResponseEntity<ApiError> {
        val apiError = ApiError(
            HttpStatus.BAD_REQUEST,
            "${ex.name} must be of type ${ex.requiredType?.name}")

        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleEmptyResultDataAccessException(
        ex: EmptyResultDataAccessException
    ) : ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.NOT_FOUND, "There is no such id")

        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }

    @ExceptionHandler(Exception::class)
    fun handleAll(ex: Exception) : ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred")

        return ResponseEntity(apiError, HttpHeaders(), apiError.status)
    }
}
