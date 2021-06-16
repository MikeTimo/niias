package com.example.servicescheduleapp.model

data class Driver(
        var id: Int = 0,
        var number: Int = 0,
        var firstName: String = "",
        var middleName: String = "",
        var lastName: String = "",
        var isAvailable: Boolean = true
)

