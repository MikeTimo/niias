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
    @GetMapping("/available")
    fun getAvailableDriver(): List<Driver> {
        return driverService.getAvailableDrivers()
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    fun getAllDrivers(): List<Driver> {
        return driverService.getAllDrivers()
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    fun getDriver(@PathVariable id: Int): Driver {
        return driverService.getDriverById(id)
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}/delete")
    fun deleteDriver(@PathVariable id: Int) {
        driverService.deleteDriverById(id)
    }
}