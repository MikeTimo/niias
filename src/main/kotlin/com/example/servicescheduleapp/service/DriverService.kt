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
    fun getAvailableDrivers(): List<Driver> = driverMap.values.filter { it.isAvailable }

    @Synchronized
    fun getAllDrivers(): List<Driver> = driverMap.values.toList()

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

    /**
     * Проверка машиниста на доступность
     * @param driverId - id машиниста
     * @return driverAvailable - статус доступности машиниста
     */
    fun checkDriverIsAvailable(driverId: Int): Boolean {
        var driverAvailable = false;
        driversProperties.drivers.filter { x -> x.id == driverId && x.isAvailable }.map { driverAvailable = true }
        return driverAvailable
    }

    /**
     * Обновление статуса isAvailable машиниста
     * @param driverId - id машиниста
     */
    fun updateIsAvailableOnFalse(driverId: Int) {
        driversProperties.drivers.filter { x -> x.id == driverId }.map { x -> x.isAvailable = false }
    }

    /**
     * Метод для внесения списка машинистов из конфигурации в driverMap
     */
    private fun addDriversFromConfigToDriverMap() {
        for (driver in driversProperties.drivers) {
            driverMap[driver.id] = driver
        }
    }
}