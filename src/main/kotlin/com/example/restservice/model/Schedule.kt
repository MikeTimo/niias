package com.example.restservice.model

import java.time.LocalDateTime

data class Schedule(
    val trainNumber: Int,
    val driverNumber: Int,
    val departureStation: Int,
    var departureTime: LocalDateTime,
    val arrivalStation: Int,
    var arrivalTime: LocalDateTime
)
