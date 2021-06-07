package com.example.servicescheduleapp.config

import com.example.servicescheduleapp.model.Driver
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app-drivers")
class DriversProperties() {
    val drivers: List<Driver> = ArrayList()

    fun checkDriverIsAvailable(driverId: Int): Boolean {
        var driverAvailable = false;
        for (driver in drivers) {
            if (driver.id == driverId && driver.isAvailable)
                driverAvailable = true
        }
        return driverAvailable
    }

    fun updateIsAvailableOnFalse(driverId: Int) {
        for (driver in drivers) {
            if (driver.id == driverId) {
                driver.isAvailable = false
            }
        }
    }
}