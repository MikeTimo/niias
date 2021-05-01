package com.example.restservice.utils

import java.time.LocalDateTime

data class ScheduleTest(
    val trainNumber: Int,
    val driverNumber: Int,
    val departureStation: Int,
    val departureTime: LocalDateTime,
    val arrivalStation: Int,
    val arrivalTime: LocalDateTime
)