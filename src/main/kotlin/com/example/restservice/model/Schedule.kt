package com.example.restservice.model

import java.time.LocalDateTime

class Schedule(
    trainNumber: Int,
    private var driverNumber: Int,
    private var departureStation: Int,
    departureTime: LocalDateTime,
    private var arrivalStation: Int,
    private var arrivalTime: LocalDateTime
) {
    var trainNumber: Int = trainNumber
        private set
    var departureTime: LocalDateTime = departureTime
        private set
}
