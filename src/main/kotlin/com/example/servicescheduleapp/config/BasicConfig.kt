package com.example.servicescheduleapp.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.collections.ArrayList

@Component("basicConfigBean")
@ConfigurationProperties(prefix = "app")
class BasicConfig() {

    var codOfTechnicalOperationWithTrains: Int = 0
    var trainNumberOuterCircle: Int = 0
    var trainNumberInnerCircle: Int = 0
    var trainIndex: Int = 0
    var countTrainOnLine: Int = 0
    var sequentialNumberOfBrigade: Int = 0
    var codeOfHeadWagon: String = ""

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var startWorkOfRollingStock: LocalDateTime? = null

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var endWorkOfRollingStock: LocalDateTime? = null

    @DateTimeFormat(pattern = "HH:mm:ss")
    var longWorkShifts: LocalTime? = null

    @DateTimeFormat(pattern = "HH:mm:ss")
    var durationOfTrainStop: LocalTime? = null

    @DateTimeFormat(pattern = "HH:mm:ss")
    var durationOfBlock: LocalTime? = null

    @DateTimeFormat(pattern = "HH:mm:ss")
    var timeLap: LocalTime? = null
    var workShifts: List<WorkShift> = ArrayList()

    @ConfigurationPropertiesBinding
    class WorkShift() {
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        var startWorkTime: LocalDateTime? = null

        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        var endWorkTime: LocalDateTime? = null
    }
}
