package com.example.servicescheduleapp.controller

import com.example.servicescheduleapp.model.Driver
import com.example.servicescheduleapp.service.DriverService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/drivers")
class DriverController {

    @Autowired
    lateinit var driverService: DriverService

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/available")
    fun getAvailableDriver(): List<Driver> {
        return driverService.getAvailableDrivers()
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping()
    fun getAllDrivers(): List<Driver> {
        return driverService.getAllDrivers()
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/{id}")
    fun getDriver(@PathVariable id: Int): Driver {
        return driverService.getDriverById(id)
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/{id}/delete")
    fun deleteDriver(@PathVariable id: Int) {
        driverService.deleteDriverById(id)
    }
}