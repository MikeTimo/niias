package com.example.servicescheduleapp.model

data class Driver(
    val id: Int, val number: Int, val firstName: String,
    val middleName: String, val lastName: String, val isAvailable: Boolean
)
