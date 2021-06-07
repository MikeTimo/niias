package com.example.servicescheduleapp.controller

import com.example.servicescheduleapp.model.Driver
import com.example.servicescheduleapp.service.DriverService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class DriverController {

    @Autowired
    lateinit var driverService: DriverService

    @GetMapping("/drivers/available")
    fun getAvailableDriver(): List<Driver> {
        return driverService.getAvailableDrivers()
    }

    @GetMapping("/drivers")
    fun getAllDrivers(): List<Driver> {
        return driverService.getAllDrivers()
    }

    @GetMapping("/drivers/{id}")
    fun getDriver(@PathVariable id: Int): Driver {
        return driverService.getDriverBiId(id)
    }

    @DeleteMapping("/drivers/{id}")
    fun deleteDriver(@PathVariable id: Int) {
        driverService.deleteDriverBiId(id)
    }
}