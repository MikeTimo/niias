package com.example.servicescheduleapp.service

import com.example.servicescheduleapp.config.BasicProperties
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
class ScheduleService(@Qualifier("basicConfigBean") val basicProperties: BasicProperties,
                      val driverService: DriverService,
                      val rollingStockService: RollingStockService,
                      val driversProperties: DriversProperties) {

    /**
     * Мапа рабочих смен машинистов
     * Ключ: Id машиниста
     * Значение: Объект с параметрами начала и конца рабочей смены
     */
    val workShiftOfDriver: MutableMap<Int, BasicProperties.WorkShift> = HashMap()

    /**
     * Время отправления с начальной станции и время прибытия на конечную станцию для всех кругов
     * которые совершит поезд
     */
    val timeAllLaps: MutableMap<LocalDateTime?, LocalDateTime> = TreeMap()

    /**
     * Мапа для хранения списка расписаний для поезда
     * Ключ: Номер поезда
     * Значение: Список расписаний
     */
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

                if (schedule.departureTime.isBefore(endDataTime) && schedule.arrivalTime.isAfter(startDataTime)) {
                    listWithTrueSchedule.add(schedule)
                }
//                } else if (schedule.departureTime.isBefore(startDataTime) && schedule.arrivalTime.isAfter(startDataTime) && schedule.arrivalTime.isBefore(endScheduleTime)) {
//                    listWithTrueSchedule.add(schedule)
//                } else if (schedule.departureTime.isAfter(startDataTime) && schedule.departureTime.isBefore(endScheduleTime) && schedule.arrivalTime.isAfter(endScheduleTime)) {
//                    listWithTrueSchedule.add(schedule)
//                } else if (schedule.departureTime.isBefore(startDataTime) && schedule.arrivalTime.isAfter(endScheduleTime)) {
//                    listWithTrueSchedule.add(schedule)
//                }
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

    /**
     * создает список расписания для поезда
     */
    fun createScheduleListForTrain() {
        var scheduleList: MutableList<Schedule>
        for (i in 0 until rollingStockService.listUsedOfRollingStock.size) {
            scheduleList = createScheduleList()
            scheduleMap[rollingStockService.getRandomTrainNumber()] = scheduleList
        }
    }

    /**
     * происходит выбор машиниста: если previousDriverId == 0, то происходит проверка машинистов
     * из списка машинисов(проверяется время начала работы, время конца работы и доступность)
     * если машинст выбран previousDriverId != 0, то происходит проверка на время завершения работы машиниста
     *
     * @param startTimeLap - время начала отправления поезда с начальной станции
     * @param endTimeLap - время прибытия поезда на конечную станцию
     * @param previousDriverId - номер машиниста, который управлял поездом,
     * если никого не было, то равно 0
     * @return id машиниста
     */
    fun chooseDriver(startTimeLap: LocalDateTime?, endTimeLap: LocalDateTime?, previousDriverId: Int): Int {
        if (startTimeLap == null || endTimeLap == null) throw Exception("Failed choose driver")

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
    }

    /**
     * метод для проверки, начинает ли машинист раньше, чем время отправления поезда с начальной станции
     * @param startTimeOfWorkOfDriver начала смены машиниста
     * @param startTimeLap отправления поезда с начальной станции
     * @return true если машинист начинает раньше, чем поезд отправляет с начальной станции
     * @return false если машинист начинает позже, чем поезд отправляет с начальной станции
     */
    fun checkTimeOfStartWorkOfDriver(startTimeOfWorkOfDriver: LocalDateTime?, startTimeLap: LocalDateTime?): Boolean {
        if (startTimeOfWorkOfDriver != null && startTimeLap != null) {
            return startTimeOfWorkOfDriver.isBefore(startTimeLap)
        } else {
            throw Exception("Failed check time in checkTimeOfStartWorkOfDriver")
        }
    }

    /**
     * метод для проверки, заканчивается ли время работы машиниста раньше, чем время прибытия поезда на конечную станцию
     * @param endTimeOfWorkOfDriver - время конца смены машиниста
     * @param endTimeLap: - время прибытия поезда на конечную станцию
     * @return возврашает true если время машинист заканчивает позже, чем прибывает поезд на еонечную станцию
     * @return возвращает false если машинист заканчивает раньше, чем поезд прибывает на последнюю станцию
     */
    fun checkTimeOfEndWorkOfDriver(endTimeOfWorkOfDriver: LocalDateTime?, endTimeLap: LocalDateTime?): Boolean {
        if (endTimeOfWorkOfDriver != null && endTimeLap != null) {
            return endTimeOfWorkOfDriver.isAfter(endTimeLap)
        } else {
            throw Exception("Failed check time in checkTimeOfEndWorkOfDriver")
        }
    }

    /**
     * Метод для складывания дат
     * @param startTimeLap, к которой нужно прибавить время
     * @param timeLap, которое прибавляем
     * @return Возвращение времени
     */
    fun sumTime(startTimeLap: LocalDateTime?, timeLap: LocalTime?): LocalDateTime {
        if (startTimeLap != null && timeLap != null) {
            return startTimeLap?.plusHours(timeLap.hour.toLong()).plusMinutes(timeLap.minute.toLong())
        } else {
            throw Exception("Failed sumTime")
        }
    }

    /**
     * Заполнение workShiftOfDriver
     */
    private fun createWorkShiftOfDriver() {
        for (i in driversProperties.drivers.indices) {
            workShiftOfDriver[driversProperties.drivers[i].id] = basicProperties.workShifts[i]
        }
    }

    /**
     * Заполнение timeAllLaps данными
     */
    private fun createMapOfTimeAllLaps() {
        if (basicProperties.startWorkOfRollingStock != null) {
            var startTimeLap = basicProperties.startWorkOfRollingStock
            var endTimeOfLap = sumTime(startTimeLap, basicProperties.timeLap)
            do {
                timeAllLaps[startTimeLap] = endTimeOfLap
                startTimeLap = endTimeOfLap
                endTimeOfLap = sumTime(startTimeLap, basicProperties.timeLap)
            } while (endTimeOfLap.isBefore(basicProperties.endWorkOfRollingStock))
        }
    }

    /**
     * метод для создания списка расписания
     * @return scheduleList - список расписаний
     */
    private fun createScheduleList(): MutableList<Schedule> {
        val scheduleList: MutableList<Schedule> = ArrayList()
        var trainNumberOuterCircle = basicProperties.trainNumberOuterCircle
        var previousDriverId = 0
        for ((startTimeLap, endTimeLap) in timeAllLaps) {
            var driverId = chooseDriver(startTimeLap, endTimeLap, previousDriverId)
            if (driverId == 0) throw Exception("createScheduleList: Driver Id = 0")
            scheduleList.add(createSchedule(basicProperties.codOfTechnicalOperationWithTrains, trainNumberOuterCircle, basicProperties.trainIndex, basicProperties.countTrainOnLine, basicProperties.sequentialNumberOfBrigade, driverId,
                    startTimeLap, endTimeLap, basicProperties.codeOfHeadWagon))
            trainNumberOuterCircle += 2
            previousDriverId = driverId
        }
        return scheduleList
    }

    /**
     * метод для создания расписания
     * @return schedule расписания
     */
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
