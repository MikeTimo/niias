package com.example.servicescheduleapp.service

import com.example.servicescheduleapp.config.BasicConfig
import com.example.servicescheduleapp.config.DriversProperties
import com.example.servicescheduleapp.exception.BadRequestException
import com.example.servicescheduleapp.exception.NotFoundException
import com.example.servicescheduleapp.model.Schedule
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Service
class ScheduleService(@Qualifier("basicConfigBean") val config: BasicConfig,
                      val driverService: DriverService,
                      val rollingStockService: RollingStockService,
                      val driversProperties: DriversProperties) {

    val workShiftOfDriver: MutableMap<Int, BasicConfig.WorkShift> = HashMap()
    val timeAllLaps: MutableMap<LocalDateTime?, LocalDateTime> = TreeMap()
    var scheduleMap: MutableMap<Int, MutableList<Schedule>> = ConcurrentHashMap()

    init {
        createWorkShiftOfDriver()
        createMapOfTimeAllLaps()
        createScheduleListForTrain()
    }

    @Synchronized
    fun getScheduleOnDayByTrain(trainNumber: Int): List<Schedule>? {
        var scheduleList: MutableList<Schedule> = ArrayList()
        if (trainNumber == 0) throw BadRequestException("Train number = 0")
        if (scheduleMap.containsKey(trainNumber) && scheduleMap[trainNumber] != null) {
            scheduleList = scheduleMap[trainNumber]!!
        } else {
            throw NotFoundException("ScheduleList of train with train number = $trainNumber not found")
        }
        return scheduleList
    }

    @Synchronized
    fun getSchedulesBetweenTimePoint(startDataTime: LocalDateTime, endDataTime: LocalDateTime?): List<Schedule> {
        if (startDataTime == null) throw BadRequestException("Param TrainNumber is null")
        val endScheduleTime: LocalDateTime
        if (endDataTime == null) {
            val dataRequest = startDataTime.toLocalDate()
            endScheduleTime = dataRequest.atTime(23, 59, 59)
        } else {
            endScheduleTime = endDataTime
        }
        val listWithTrueSchedule: MutableList<Schedule> = ArrayList()
        for ((key, value) in scheduleMap) {
            for (schedule in value) {
                if (schedule.departureTime.isAfter(startDataTime) && schedule.arrivalTime.isBefore(endScheduleTime)) {
                    listWithTrueSchedule.add(schedule)
                } else if (schedule.departureTime.isBefore(startDataTime) && schedule.arrivalTime.isAfter(startDataTime) && schedule.arrivalTime.isBefore(endScheduleTime)) {
                    listWithTrueSchedule.add(schedule)
                } else if (schedule.departureTime.isAfter(startDataTime) && schedule.departureTime.isBefore(endScheduleTime) && schedule.arrivalTime.isAfter(endScheduleTime)) {
                    listWithTrueSchedule.add(schedule)
                } else if (schedule.departureTime.isBefore(startDataTime) && schedule.arrivalTime.isAfter(endScheduleTime)) {
                    listWithTrueSchedule.add(schedule)
                }
            }
        }
        return listWithTrueSchedule
    }

    @Synchronized
    fun saveSchedule(trainNumber: Int, schedule: Schedule?) {
        var scheduleList: MutableList<Schedule> = ArrayList()
        if (trainNumber == 0) throw BadRequestException("Train number is $trainNumber")
        if (scheduleMap.containsKey(trainNumber)) {
            if (scheduleMap.containsKey(trainNumber) != null) {
                scheduleList = scheduleMap[trainNumber]!!
                if (schedule != null) {
                    scheduleList.add(schedule)
                }
                scheduleMap[trainNumber] = scheduleList
            }
        } else {
            if (schedule != null) {
                scheduleList.add(schedule)
            }
            scheduleMap[trainNumber] = scheduleList
        }
    }

    fun createScheduleListForTrain() {
        var scheduleList: MutableList<Schedule>
        for (i in 0 until rollingStockService.listUsedOfRollingStock.size) {
            scheduleList = createScheduleList()
            scheduleMap[rollingStockService.getRandomTrainNumber()] = scheduleList
        }
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
                    if (checkTimeOfStartWorkOfDriver(workShift.startWorkTime, startTimeLap) && checkTimeOfEndWorkOfDriver(workShift.endWorkTime, endTimeLap) && driverService.checkDriverIsAvailable(driverId)) {
                        id = driverId
                        driverService.updateIsAvailableOnFalse(driverId)
                        break
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

    fun sumTime(startTimeLap: LocalDateTime?, timeLap: LocalTime?): LocalDateTime {
        if (startTimeLap != null && timeLap != null) {
            return startTimeLap?.plusHours(timeLap.hour.toLong()).plusMinutes(timeLap.minute.toLong())
        } else {
            throw Exception("Failed sumTime")
        }
    }

    private fun createWorkShiftOfDriver() {
        if (driversProperties.drivers.size == config.workShifts.size) {
            for (i in driversProperties.drivers.indices) {
                workShiftOfDriver[driversProperties.drivers[i].id] = config.workShifts[i]
            }
        }
    }

    private fun createMapOfTimeAllLaps() {
        if (config.startWorkOfRollingStock != null) {
            var startTimeLap = config.startWorkOfRollingStock
            var endTimeOfLap = sumTime(startTimeLap, config.timeLap)
            do {
                timeAllLaps[startTimeLap] = endTimeOfLap
                startTimeLap = endTimeOfLap
                endTimeOfLap = sumTime(startTimeLap, config.timeLap)
            } while (endTimeOfLap.isBefore(config.endWorkOfRollingStock))
        }
    }

    private fun createScheduleList(): MutableList<Schedule> {
        val scheduleList: MutableList<Schedule> = ArrayList()
        var trainNumberOuterCircle = config.trainNumberOuterCircle
        var trainNumberInnerCircle = config.trainNumberInnerCircle
        var previousDriverId = 0
        for ((startTimeLap, endTimeLap) in timeAllLaps) {
            var driverId = chooseDriver(startTimeLap, endTimeLap, previousDriverId)
            if (driverId == 0) throw Exception("createScheduleList: Driver Id = 0")
            scheduleList.add(createSchedule(config.codOfTechnicalOperationWithTrains, trainNumberOuterCircle, config.trainIndex, config.countTrainOnLine, config.sequentialNumberOfBrigade, driverId,
                    startTimeLap, endTimeLap, config.codeOfHeadWagon))
            trainNumberOuterCircle += 2
            previousDriverId = driverId
        }
        return scheduleList
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
}
