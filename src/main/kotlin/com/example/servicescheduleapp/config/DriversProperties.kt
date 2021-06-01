package com.example.servicescheduleapp.config

import com.example.servicescheduleapp.model.Driver
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app-drivers")
data class DriversProperties(val drivers: List<Driver>) {

    fun getRandomDriverNumber(): Int {
        val driverList = drivers
        var driver = driverList[(0 until driverList.size).random()]
        var driverNumber = 0
        if (driver.isAvailable) {
            driverNumber = driver.number
            driver.isAvailable = false
        } else {
            driverNumber = getRandomDriverNumber()
        }
        return driverNumber
    }
}