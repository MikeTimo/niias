package com.example.restservice.model

import java.time.LocalDateTime

data class Schedule(
    val trainNumber: Int,
    val driverNumber: Int,
    val departureStation: Int,
    val departureTime: LocalDateTime,
    val arrivalStation: Int,
    val arrivalTime: LocalDateTime
)
