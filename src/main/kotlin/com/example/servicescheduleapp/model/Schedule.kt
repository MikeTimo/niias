package com.example.servicescheduleapp.model

import java.time.LocalDateTime

data class Schedule(
    val codOfTechnicalOperationWithTrains: Int,
    val trainNumber: Int,
    val trainIndex: Int,
    val countTrainOnLine: Int,
    val sequentialNumberOfBrigade: Int,
    val driverNumber: Int,
    val nameOfDepartureStation: String,
    val nameOfDepartureStationWithBrigade: String,
    var departureTime: LocalDateTime,
    var departureTimeWithBrigade: LocalDateTime,
    val nameOfArrivalStation: String,
    val nameOfArrivalStationWithBrigade: String,
    var arrivalTime: LocalDateTime,
    var arrivalTimeWithBrigade: LocalDateTime,
    val codeOfHeadWagon: String
)
