package com.example.servicescheduleapp.model

import java.time.LocalDateTime

data class Schedule(
    val codOfTechnicalOperationWithTrains: Int,
    val trainNumber: Int,
    val trainIndex: Int,
    val countTrainOnLine: Int,
    val sequentialNumberOfBrigade: Int,
    val driverNumber: Int,
    val codeOfDepartureStation: Int,
    val codeOfDepartureStationWithBrigade: Int,
    var departureTime: LocalDateTime,
    var departureTimeWithBrigade: LocalDateTime,
    val codeOfArrivalStation: Int,
    val codeOfArrivalStationWithBrigade: Int,
    var arrivalTime: LocalDateTime,
    var arrivalTimeWithBrigade: LocalDateTime,
    val codeOfHeadWagon: String
)
