package com.example.restservice.model

import java.time.LocalDateTime

class Schedule(
    private var trainNumber: Int,
    private var driverNumber: Int,
    private var departureStation: Int,
    private var departureTime: LocalDateTime,
    private var arrivalStation: Int,
    private var arrivalTime: LocalDateTime
)
