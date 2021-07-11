package com.example.demo.utils

import org.springframework.http.HttpStatus

data class ApiError(val status: HttpStatus, val errors: List<String>) {
    constructor(status: HttpStatus, error: String) : this(status, listOf(error))
}
