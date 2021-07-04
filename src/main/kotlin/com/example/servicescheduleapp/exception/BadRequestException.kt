package com.example.servicescheduleapp.exception

class BadRequestException(override val message: String?): Exception() {
    constructor() : this("exception")
}