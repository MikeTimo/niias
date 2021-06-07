package com.example.servicescheduleapp.config

import com.example.servicescheduleapp.model.Schedule
import com.example.servicescheduleapp.service.ScheduleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Component("basicConfigBean")
@ConfigurationProperties(prefix = "conf")
class BasicConfig {

    var codOfTechnicalOperationWithTrains: Int = 0
    var trainNumber: Int = 0
    var trainIndex: Int= 0
    var countTrainOnLine: Int = 0
    var sequentialNumberOfBrigade: Int = 0
    var codeOfHeadWagon: String = ""
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")var startWorkOfRollingStock: LocalDateTime? = null
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")var endWorkOfRollingStock: LocalDateTime? = null
    @DateTimeFormat(pattern = "HH:mm:ss") var longWorkShifts: LocalTime? = null
    @DateTimeFormat(pattern = "HH:mm:ss") var durationOfTrainStop: LocalTime? = null
    @DateTimeFormat(pattern = "HH:mm:ss") var durationOfBlock: LocalTime? = null
    @DateTimeFormat(pattern = "HH:mm:ss") var timeLap: LocalTime? = null
    var workShifts: List<WorkShift> = ArrayList()

    @Autowired
    lateinit var scheduleService: ScheduleService

    @Autowired
    lateinit var driversProperties: DriversProperties

    @Autowired
    lateinit var rollingStockProperties: RollingStockProperties

    private val workShiftOfDriver: MutableMap<Int, WorkShift> = HashMap()

    private val timeAllLaps: MutableMap<LocalDateTime?, LocalDateTime> = TreeMap()

    fun createScheduleOfDay() {
        val scheduleList: MutableList<Schedule> = ArrayList()
        getWorkShiftForDriver()
        timeCalculator()
        var previousDriverId = 0
        for ((startTimeLap, endTimeLap) in timeAllLaps) {
            var driverId = chooseDriver(startTimeLap, endTimeLap, previousDriverId)
            scheduleList.add(createSchedule(codOfTechnicalOperationWithTrains, trainNumber, trainIndex, countTrainOnLine, sequentialNumberOfBrigade, driverId,
                    startTimeLap, endTimeLap, codeOfHeadWagon))
            trainNumber += 2
            previousDriverId = driverId
        }
        scheduleService.scheduleMap[rollingStockProperties.getRandomTrainNumber()] = scheduleList
    }

    private fun createSchedule(codOfTechnicalOperationWithTrains: Int, trainNumber: Int, trainIndex: Int,
                               countTrainOnLine: Int, sequentialNumberOfBrigade: Int, driverId: Int, startTimeLap: LocalDateTime?,
                               endTimeLap: LocalDateTime, codeOfHeadWagon: String): Schedule {
        val schedule: Schedule
        if (startTimeLap != null) {
            schedule = Schedule(codOfTechnicalOperationWithTrains, trainNumber, trainIndex, countTrainOnLine,
                    sequentialNumberOfBrigade, driverId, "Андроновка Оп", "Андроновка Оп",
                    startTimeLap, startTimeLap, "Андроновка Оп", "Андроновка Оп", endTimeLap, endTimeLap, codeOfHeadWagon)
        } else {
            throw Exception()
        }
        return schedule
    }

    fun chooseDriver(startTimeLap: LocalDateTime?, endTimeLap: LocalDateTime?, previousDriverId: Int): Int {
        if (startTimeLap != null && endTimeLap != null) {
            var id = 0
            if (previousDriverId != 0) {
                if (checkTimeOfEndWorkOfDriver(workShiftOfDriver[previousDriverId]?.endWorkTime, endTimeLap)) {
                    id = previousDriverId
                } else {
                    id = chooseDriver(startTimeLap, endTimeLap, 0)
                }
            } else {
                for ((driverId, workShift) in workShiftOfDriver) {
                    if (checkTimeOfStartWorkOfDriver(workShift.startWorkTime, startTimeLap) && checkTimeOfEndWorkOfDriver(workShift.endWorkTime, endTimeLap) && driversProperties.checkDriverIsAvailable(driverId)) {
                        id = driverId
                        driversProperties.updateIsAvailableOnFalse(driverId)
                    }
                }
            }
            return id
        } else {
            throw Exception("Failed choose driver")
        }
    }

    fun checkTimeOfStartWorkOfDriver(startTimeOfWorkOfDriver: LocalDateTime?, startTimeLap: LocalDateTime?): Boolean {
        if (startTimeOfWorkOfDriver != null && startTimeLap != null) {
            return startTimeOfWorkOfDriver.isBefore(startTimeLap)
        } else {
            throw Exception("Failed check time in checkTimeOfStartWorkOfDriver")
        }
    }

    fun checkTimeOfEndWorkOfDriver(endTimeOfWorkOfDriver: LocalDateTime?, endTimeLap: LocalDateTime?): Boolean {
        if (endTimeOfWorkOfDriver != null && endTimeLap != null) {
            return endTimeOfWorkOfDriver.isAfter(endTimeLap)
        } else {
            throw Exception("Failed check time in checkTimeOfEndWorkOfDriver")
        }
    }


    private fun getWorkShiftForDriver() {
        if (workShifts.isEmpty() || driversProperties.drivers.isEmpty()) throw Exception("Не хватает данных")
        if (driversProperties.drivers.size == workShifts.size) {
            for (element in driversProperties.drivers) {
                workShiftOfDriver[element.id] = workShifts[(workShifts.indices).random()]
            }
        }
    }

    /*
    Fill timeAllLaps
     */
    private fun timeCalculator() {
        if (startWorkOfRollingStock != null) {
            var startTimeLap = startWorkOfRollingStock
            var endTimeOfLap = sumTime(startTimeLap, timeLap)
            do {
                timeAllLaps[startTimeLap] = endTimeOfLap
                startTimeLap = endTimeOfLap
                endTimeOfLap = sumTime(startTimeLap, timeLap)
            } while (endTimeOfLap.isBefore(endWorkOfRollingStock))
        }
    }

    fun sumTime(startTimeLap: LocalDateTime?, timeLap: LocalTime?): LocalDateTime {
        if (startTimeLap != null && timeLap != null) {
            return startTimeLap?.plusHours(timeLap.hour.toLong()).plusMinutes(timeLap.minute.toLong())
        } else {
            throw Exception()
        }
    }

    @ConfigurationPropertiesBinding
     class WorkShift() {
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") var startWorkTime: LocalDateTime? =null
        @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") var endWorkTime: LocalDateTime? =null
    }
}
