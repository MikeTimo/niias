package com.example.servicescheduleapp

import com.example.servicescheduleapp.controller.DriverController
import com.example.servicescheduleapp.exception.BadRequestException
import com.example.servicescheduleapp.exception.NotFoundException
import com.example.servicescheduleapp.model.Driver
import com.example.servicescheduleapp.service.DriverService
import com.example.servicescheduleapp.service.ScheduleService
import org.junit.jupiter.api.Test

import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest(DriverController::class)
class DriverControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var driverService: DriverService

    @MockBean
    lateinit var scheduleService: ScheduleService

    @Test
    fun getAvailableDriver() {
        val driver = Driver(1, 454, "Олег", "Иванович", "Иванов", true)
        val driver1 = Driver(2, 400, "Иван", "Иванович", "Иванов", true)
        val driversList = arrayListOf<Driver>(driver, driver1)

        given(driverService.getAllDrivers()).willReturn(driversList)
        mockMvc.perform(get("/drivers/available")).andExpect(status().isOk)
    }

    @Test
    fun getAllDrivers() {
        val driver = Driver(1, 454, "Олег", "Иванович", "Иванов", true)
        val driver1 = Driver(2, 400, "Иван", "Иванович", "Иванов", false)
        val driversList = arrayListOf<Driver>(driver, driver1)

        given(driverService.getAllDrivers()).willReturn(driversList)
        mockMvc.perform(get("/drivers")).andExpect(status().isOk)
    }

    @Test
    fun getDriver() {
        val driver = Driver(1, 454, "Олег", "Иванович", "Иванов", true)
        val driverId = 1

        given(driverService.getDriverById(driverId)).willReturn(driver)
        mockMvc.perform(get("/drivers/1")).andExpect(status().isOk)
    }

    @Test
    fun getDriverWhenDriverIdIsZero() {
        val driverId = 0

        given(driverService.getDriverById(driverId)).willAnswer {throw BadRequestException()}
        mockMvc.perform(get("/drivers/0")).andExpect(status().isBadRequest)
    }

    @Test
    fun getDriverWhenDriverIdIsWrong() {
        val driverId = 7

        given(driverService.getDriverById(driverId)).willAnswer {throw NotFoundException()}
        mockMvc.perform(get("/drivers/7")).andExpect(status().isNotFound)
    }

    @Test
    fun deleteDriver() {
        val driverId = 1

        doNothing().`when`(driverService).deleteDriverById(anyInt())
        driverService.deleteDriverById(driverId)
        mockMvc.perform(get("/drivers/1")).andExpect(status().isOk)
    }
}