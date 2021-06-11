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
class BasicProperties() {

    /** Код технической операции, совершаемой поездом */
    var codOfTechnicalOperationWithTrains: Int = 0

    /** Номер маршрута поезда */
    var trainNumberOuterCircle: Int = 0

    /** Индекс поеззда */
    var trainIndex: Int = 0

    /** Количество поездов на линии */
    var countTrainOnLine: Int = 0

    /** Ноиер бригады */
    var sequentialNumberOfBrigade: Int = 0

    /** Код головного вагона */
    var codeOfHeadWagon: String = ""

    /** Время начала движения */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var startWorkOfRollingStock: LocalDateTime? = null

    /** Время конца движения */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var endWorkOfRollingStock: LocalDateTime? = null

    /** Время смены машиниста */
    @DateTimeFormat(pattern = "HH:mm:ss")
    var longWorkShifts: LocalTime? = null

    /** Время стоянки поезда на остановке */
    @DateTimeFormat(pattern = "HH:mm:ss")
    var durationOfTrainStop: LocalTime? = null

    /** Время движения поезда между стациями */
    @DateTimeFormat(pattern = "HH:mm:ss")
    var durationOfBlock: LocalTime? = null

    /** Время целого круга движения */
    @DateTimeFormat(pattern = "HH:mm:ss")
    var timeLap: LocalTime? = null

    /** Список рабочих смен машинистов */
    var workShifts: List<WorkShift> = mutableListOf()

    @ConfigurationPropertiesBinding
    class WorkShift() {
        /** Время начала смены машиниста */
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        var startWorkTime: LocalDateTime? = null

        /** Время конца смены машиниста */
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        var endWorkTime: LocalDateTime? = null
    }
}
