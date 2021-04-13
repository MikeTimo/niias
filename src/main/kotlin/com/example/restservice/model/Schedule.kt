package com.example.restservice.model

import java.time.LocalDateTime
//import javax.persistence.Entity
//import javax.persistence.GeneratedValue
//import javax.persistence.Id

//@Entity
class Schedule {
//    @Id
//    @GeneratedValue
//    var id: Long? = null
    private var trainNumber: Int = 0
    private var driverNumber: Int = 0
    private var stationFrom: Int = 0
    private var timeFrom: LocalDateTime? = null
    private var stationTo: Int = 0
    private var timeTo: LocalDateTime? = null

    constructor()

    constructor(
        trainNumber: Int, driverNumber: Int, stationFrom: Int, timeFrom: LocalDateTime,
        stationTo: Int, timeTo: LocalDateTime
    ) : this() {
        this.trainNumber = trainNumber
        this.driverNumber = driverNumber
        this.stationFrom = stationFrom
        this.timeFrom = timeFrom
        this.stationTo = stationTo
        this.timeTo = timeTo
    }
}