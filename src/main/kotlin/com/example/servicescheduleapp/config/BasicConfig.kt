package com.example.servicescheduleapp.config

import com.example.servicescheduleapp.model.Driver
import com.example.servicescheduleapp.model.RollingStock
import com.example.servicescheduleapp.model.Schedule
import com.example.servicescheduleapp.service.DriverService
import com.example.servicescheduleapp.service.ScheduleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component("basicConfigBean")
@ConfigurationProperties(prefix = "server")
class BasicConfig {

    @Autowired
    lateinit var driverService: DriverService

    @Autowired
    lateinit var scheduleService: ScheduleService

    private val driver: Driver = Driver(1, 123, "Игорь", "Александрович", "Иванов", true)
    private val driver1: Driver = Driver(2, 125, "Михаил", "Васильевич", "Сергеев", true)
    private val driver2: Driver = Driver(3, 387, "Василий", "Никитич", "Смирнов", true)
    private val driver3: Driver = Driver(4, 766, "Алеександр", "Сергеевич", "Кутепов", true)
    private val driver4: Driver = Driver(5, 65, "Петр", "Николаевич", "Иванов", true)

    private val rs113: RollingStock = RollingStock(0, "ЭС2Г", 113, true)
    private val rs136: RollingStock = RollingStock(0, "ЭС2Г", 136, true)

    private val trains = arrayListOf<RollingStock>(rs113, rs136)

    fun addDriversInList() {
        driverService.driverMap[driver.number] = driver
        driverService.driverMap[driver1.number] = driver1
        driverService.driverMap[driver2.number] = driver2
        driverService.driverMap[driver3.number] = driver3
        driverService.driverMap[driver4.number] = driver4
    }

    private val driverList = arrayListOf<Driver>(driver, driver1, driver2, driver3, driver4)

    fun addAllSchedulesInList() {
        var departureTimeParse: LocalDateTime = LocalDateTime.of(2021, 4, 20, 4, 0, 0)
        var arrivalTimeParse: LocalDateTime = LocalDateTime.of(2021, 4, 20, 5, 29, 0)
        var departureTimeWithBrigadeParse: LocalDateTime = departureTimeParse
        var arrivalTimeWithBrigadeParse: LocalDateTime = arrivalTimeParse
        val scheduleList: MutableList<Schedule> = ArrayList()
        var runNumber = 8001
        var driverNumber = getDriverNumber()

        for (i in 1..14) {
            val schedule = Schedule(
                1, runNumber, 2213,
                10, 225, driverNumber,
                1, 1, departureTimeParse,
                departureTimeWithBrigadeParse, 1, 1,
                arrivalTimeParse, arrivalTimeWithBrigadeParse, "56"
            )

            scheduleList.add(schedule)

            departureTimeParse = arrivalTimeParse.plusMinutes(1)
            arrivalTimeParse = arrivalTimeParse.plusHours(1).plusMinutes(29)
            departureTimeWithBrigadeParse = departureTimeParse
            arrivalTimeWithBrigadeParse = arrivalTimeParse

            runNumber += 2

            if (i == 7) {
                val scheduleListForFirstShift: MutableList<Schedule> = ArrayList(scheduleList)
                driverNumber = getDriverNumber()
                val trainNumber = getTrainNumber()
                scheduleService.scheduleMap[trainNumber] = scheduleListForFirstShift
                createArrayOfSchedule(trainNumber, driverNumber, scheduleListForFirstShift)
                scheduleList.removeAll(scheduleList)
            }
            if (i == 14) {
                val trainNumber = getTrainNumber()
                createArrayOfSchedule(trainNumber, driverNumber, scheduleList)
                scheduleService.scheduleMap[trainNumber] = scheduleList
                createArrayOfSchedule(trainNumber, driverNumber, scheduleList)
            }
        }
    }

    fun getDriverNumber(): Int {
        var driver = driverList[(0 until driverList.size).random()]
        var driverNumber = 0
        if (driver.isAvailable) {
            driverNumber = driver.number
            driver.isAvailable = false
        } else {
            driverNumber = getDriverNumber()
        }
        return driverNumber
    }

    fun createArrayOfSchedule(trainNumber: Int, driverNumber: Int, scheduleList: MutableList<Schedule>) {
        val trainDriverScheduleList: MutableList<TrainDriverScheduleConfig> = ArrayList()
        trainDriverScheduleList.add(TrainDriverScheduleConfig(trainNumber, driverNumber, scheduleList))
    }

    fun getTrainNumber(): Int{
        val train = trains[(0 until trains.size).random()]
        var trainNumber = 0
        if (train.isAvailable) {
            train.isAvailable = false
            trainNumber = train.number
        } else {
            trainNumber = getTrainNumber()
        }
        return trainNumber
    }
}