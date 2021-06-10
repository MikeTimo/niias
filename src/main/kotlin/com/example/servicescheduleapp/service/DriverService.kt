package com.example.servicescheduleapp.service

import com.example.servicescheduleapp.config.DriversProperties
import com.example.servicescheduleapp.model.Driver
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.concurrent.ConcurrentHashMap

@Service
class DriverService(val driversProperties: DriversProperties) {
    var driverMap: MutableMap<Int, Driver> = ConcurrentHashMap()

    init {

    }

    @Synchronized
    fun getAvailableDrivers(): List<Driver> {
        val availableDrivers: MutableList<Driver> = ArrayList()
        for (driver in driverMap.values) {
            if (driver.isAvailable!!) {
                availableDrivers.add(driver)
            }
        }
        if (availableDrivers.isEmpty()) throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return availableDrivers
    }

    @Synchronized
    fun getAllDrivers(): List<Driver> {
        val drivers = ArrayList(driverMap.values)
        if (drivers.isEmpty()) throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return drivers
    }

    @Synchronized
    fun getDriverBiId(id: Int): Driver {
        if (id == null || id == 0) throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        var driver: Driver
        if (driverMap.containsKey(id) && driverMap[id] != null) {
            driver = driverMap[id]!!
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
        return driver
    }

    @Synchronized
    fun deleteDriverBiId(id: Int) {
        if (id == null || id == 0) throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        if (driverMap.containsKey(id) && driverMap[id] != null) {
            driverMap.remove(id)
        } else {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
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