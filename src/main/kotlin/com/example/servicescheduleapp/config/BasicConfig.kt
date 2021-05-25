package com.example.servicescheduleapp.config

import com.example.servicescheduleapp.model.Driver
import com.example.servicescheduleapp.model.RollingStock
import com.example.servicescheduleapp.model.Schedule
import com.example.servicescheduleapp.service.DriverService
import com.example.servicescheduleapp.service.ScheduleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@ConfigurationProperties(prefix = "server")
class BasicConfig {

    @Autowired
    lateinit var driverService: DriverService

    @Autowired
    lateinit var scheduleService: ScheduleService

    private val driver: Driver = Driver(0, 123, "Игорь", "Александрович", "Иванов", true)
    private val driver1: Driver = Driver(0, 125, "Михаил", "Васильевич", "Сергеев", true)
    private val driver2: Driver = Driver(0, 387, "Василий", "Никитич", "Смирнов", true)
    private val driver3: Driver = Driver(0, 766, "Алеександр", "Сергеевич", "Кутепов", true)
    private val driver4: Driver = Driver(0, 65, "Петр", "Николаевич", "Иванов", true)

    private val rs113: RollingStock = RollingStock(0, "ЭС2Г", 113)
    private val rs136: RollingStock = RollingStock(0, "ЭС2Г", 136)

    fun addDriversInList() {
        driverService.driverMap[driver.number] = driver
        driverService.driverMap[driver1.number] = driver1
        driverService.driverMap[driver2.number] = driver2
        driverService.driverMap[driver3.number] = driver3
        driverService.driverMap[driver4.number] = driver4
    }

    fun addAllSchedulesInList() {
        var departureTimeParse: LocalDateTime = LocalDateTime.of(2021, 4, 20, 4, 0, 0)
        var arrivalTimeParse: LocalDateTime = LocalDateTime.of(2021, 4, 20, 5, 29, 0)
        var departureTimeWithBrigadeParse: LocalDateTime = departureTimeParse
        var arrivalTimeWithBrigadeParse: LocalDateTime = arrivalTimeParse
        val scheduleList: MutableList<Schedule> = ArrayList()
        for (i in 1..14) {
            var driverNumber = driver.number
            if (i > 8) driverNumber = driver1.number
            val schedule = Schedule(
                1, rs113.number, 2213,
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
        }

        scheduleService.scheduleMap[rs113.number] = scheduleList
        println(scheduleList.size)

    }
}