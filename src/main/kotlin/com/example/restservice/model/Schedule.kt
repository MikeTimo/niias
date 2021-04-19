package com.example.restservice.model

import java.time.LocalDateTime

class Schedule(
    private var trainNumber: Int,
    private var driverNumber: Int,
    private var stationFrom: Int,
    private var timeFrom: LocalDateTime,
    private var stationTo: Int,
    private var timeTo: LocalDateTime?
)
