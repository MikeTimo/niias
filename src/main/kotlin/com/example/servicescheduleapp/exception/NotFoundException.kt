package com.example.servicescheduleapp.exception

class NotFoundException(override val message: String?): Exception() {
    constructor() : this("exception")
}