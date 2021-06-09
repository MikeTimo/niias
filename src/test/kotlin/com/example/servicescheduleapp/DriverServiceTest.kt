package com.example.servicescheduleapp

import com.example.servicescheduleapp.model.Driver
import com.example.servicescheduleapp.service.DriverService
import com.example.servicescheduleapp.service.ScheduleService
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class DriverServiceTest {

    @Autowired
    lateinit var driverService: DriverService

    @MockBean
    lateinit var scheduleService: ScheduleService

    @BeforeEach
    fun clearDriverMap() {
        driverService.driverMap.clear()
    }

    @Test
    fun getAvailableDrivers() {
        val driver = Driver(1, 454, "Олег", "Иванович", "Иванов", true)
        val driver1 = Driver(2, 400, "Иван", "Иванович", "Иванов", false)
        val driver2 = Driver(3, 454, "Олег", "Иванович", "Иванов", true)
        val driver3 = Driver(4, 400, "Иван", "Иванович", "Иванов", false)

        driverService.driverMap[1] = driver
        driverService.driverMap[2] = driver1
        driverService.driverMap[3] = driver2
        driverService.driverMap[4] = driver3

        val actualDriverList = driverService.getAvailableDrivers()
        val actualDriverListSize = actualDriverList.size
        val expectedSize = 2

        assertEquals(expectedSize, actualDriverListSize)
    }

    @Test
    fun getAllDrivers() {
        val driver = Driver(1, 454, "Олег", "Иванович", "Иванов", true)
        val driver1 = Driver(2, 400, "Иван", "Иванович", "Иванов", false)

        driverService.driverMap[1] = driver
        driverService.driverMap[2] = driver1

        val actualDriverList = driverService.getAllDrivers()
        val actualDriverListSize = actualDriverList.size
        val expectedSize = 2

        assertEquals(expectedSize, actualDriverListSize)
    }

    @Test
    fun getDriverById() {
        val driver = Driver(1, 454, "Олег", "Иванович", "Иванов", true)
        val driver1 = Driver(2, 400, "Иван", "Иванович", "Иванов", false)

        driverService.driverMap[1] = driver
        driverService.driverMap[2] = driver1

        val actualDriver = driverService.getDriverBiId(1);
        assertNotNull(actualDriver)

        assertEquals(driver, actualDriver)
    }

    @Test
    fun deleteDriverById() {
        val driver = Driver(1, 454, "Олег", "Иванович", "Иванов", true)
        val driver1 = Driver(2, 400, "Иван", "Иванович", "Иванов", false)

        driverService.driverMap[1] = driver
        driverService.driverMap[2] = driver1

        driverService.deleteDriverBiId(1)
        val actualDriverListSize = driverService.getAllDrivers().size

        assertEquals(1, actualDriverListSize)
    }
}