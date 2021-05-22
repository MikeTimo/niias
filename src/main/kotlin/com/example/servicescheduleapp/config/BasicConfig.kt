package com.example.servicescheduleapp.config

import com.example.servicescheduleapp.model.Driver
import com.example.servicescheduleapp.service.DriverService
import org.springframework.beans.factory.annotation.Autowired


class BasicConfig {

    @Autowired
    lateinit var driverService: DriverService

    val driver: Driver = Driver(0, 123, "Игорь", "Александрович", "Иванов", true)
    val driver1: Driver = Driver(0, 125, "Михаил", "Васильевич", "Сергеев", true)
    val driver2: Driver = Driver(0, 387, "Василий", "Никитич", "Смирнов", true)
    val driver3: Driver = Driver(0, 766, "Алеександр", "Сергеевич", "Кутепов", true)
    val driver4: Driver = Driver(0, 65, "Петр", "Николаевич", "Иванов", true)

    fun addDriversInList() {
        driverService.driverMap[driver.number] = driver
        driverService.driverMap[driver1.number] = driver1
        driverService.driverMap[driver2.number] = driver2
        driverService.driverMap[driver3.number] = driver3
        driverService.driverMap[driver4.number] = driver4
    }
}