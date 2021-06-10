package com.example.servicescheduleapp.service

import com.example.servicescheduleapp.config.DriversProperties
import com.example.servicescheduleapp.exception.BadRequestException
import com.example.servicescheduleapp.exception.NotFoundException
import com.example.servicescheduleapp.model.Driver
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class DriverService(val driversProperties: DriversProperties) {
    var driverMap: MutableMap<Int, Driver> = ConcurrentHashMap()

    init {
        addDriversFromConfigToDriverMap()
    }

    @Synchronized
    fun getAvailableDrivers(): List<Driver> {
        val availableDrivers: MutableList<Driver> = ArrayList()
        for (driver in driverMap.values) {
            if (driver.isAvailable!!) {
                availableDrivers.add(driver)
            }
        }
        return availableDrivers
    }

    @Synchronized
    fun getAllDrivers(): List<Driver> {
        val drivers = ArrayList(driverMap.values)
        return drivers
    }

    @Synchronized
    fun getDriverById(id: Int): Driver {
        if (id == null || id == 0) throw BadRequestException("Driver id is null or id = $id")
        var driver: Driver
        if (driverMap.containsKey(id) && driverMap[id] != null) {
            driver = driverMap[id]!!
        } else {
            throw NotFoundException("Driver with id = $id not found")
        }
        return driver
    }

    @Synchronized
    fun deleteDriverById(id: Int) {
        if (id == null || id == 0) throw BadRequestException("Driver id is null or id = $id")
        if (driverMap.containsKey(id) && driverMap[id] != null) {
            driverMap.remove(id)
        } else {
            throw NotFoundException("Driver with id = $id not found")
        }
    }

    fun checkDriverIsAvailable(driverId: Int): Boolean {
        var driverAvailable = false;
        for (driver in driversProperties.drivers) {
            if (driver.id == driverId && driver.isAvailable)
                driverAvailable = true
        }
        return driverAvailable
    }

    fun updateIsAvailableOnFalse(driverId: Int) {
        for (driver in driversProperties.drivers) {
            if (driver.id == driverId) {
                driver.isAvailable = false
            }
        }
    }

    private fun addDriversFromConfigToDriverMap() {
        for (driver in driversProperties.drivers) {
            driverMap[driver.id] = driver
        }
    }
}