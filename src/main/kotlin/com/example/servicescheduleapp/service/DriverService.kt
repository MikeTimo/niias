package com.example.servicescheduleapp.service

import com.example.servicescheduleapp.model.Driver
import java.util.concurrent.ConcurrentHashMap

class DriverService {
    var driverMap: MutableMap<Int, Driver> = ConcurrentHashMap()

    @Synchronized
    fun getAvailableDrivers(): List<Driver> {
        val availableDrivers: MutableList<Driver> = ArrayList()
        for (driver in driverMap.values) {
            if (driver.isAvailable) {
                availableDrivers.add(driver)
            }
        }
        return availableDrivers
    }

    @Synchronized
    fun getAllDrivers(): List<Driver> {
        return ArrayList(driverMap.values)
    }
}