package com.example.servicescheduleapp

import com.example.servicescheduleapp.exception.BadRequestException
import com.example.servicescheduleapp.exception.NotFoundException
import com.example.servicescheduleapp.model.RollingStock
import com.example.servicescheduleapp.service.RollingStockService
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RollingStockServiceTest {

    @Autowired
    lateinit var rollingStockService: RollingStockService

    @Test
    fun getRollingStockById() {
        val rollingStock = RollingStock(1, "ЭС2Г", 113, true)
        rollingStockService.listUsedOfRollingStock.add(rollingStock)

        val actualRollingStock = rollingStockService.getRollingStockById(1)
        assertNotNull(actualRollingStock)
        assertEquals(rollingStock, actualRollingStock)
    }

    @Test
    fun getRollingStockByIdIsZero() {
        val rollingStock = RollingStock(1, "ЭС2Г", 113, true)
        rollingStockService.listUsedOfRollingStock.add(rollingStock)

        assertThrows(BadRequestException::class.java) {rollingStockService.getRollingStockById(0)}
    }

    @Test
    fun getRollingStockByIdIsWrong() {
        val rollingStock = RollingStock(1, "ЭС2Г", 113, true)
        rollingStockService.listUsedOfRollingStock.add(rollingStock)

        assertThrows(NotFoundException::class.java) {rollingStockService.getRollingStockById(10)}
    }

    @Test
    fun getRollingStockByNumber() {
        val rollingStock = RollingStock(1, "ЭС2Г", 113, true)
        rollingStockService.listUsedOfRollingStock.add(rollingStock)

        val actualRollingStock = rollingStockService.getRollingStockByNumber(113)
        assertNotNull(actualRollingStock)
        assertEquals(rollingStock, actualRollingStock)
    }

    @Test
    fun getRollingStockByNumberIsZero() {
        val rollingStock = RollingStock(1, "ЭС2Г", 113, true)
        rollingStockService.listUsedOfRollingStock.add(rollingStock)

        assertThrows(BadRequestException::class.java) {rollingStockService.getRollingStockByNumber(0)}
    }

    @Test
    fun getRollingStockByNumberIsWrong() {
        val rollingStock = RollingStock(1, "ЭС2Г", 113, true)
        rollingStockService.listUsedOfRollingStock.add(rollingStock)

        assertThrows(NotFoundException::class.java) {rollingStockService.getRollingStockByNumber(10)}
    }

    @Test
    fun getAllRollingStock() {
        rollingStockService.listUsedOfRollingStock.add(RollingStock(1, "ЭС2Г", 113, true))
        rollingStockService.listUsedOfRollingStock.add(RollingStock(2, "ЭС2Г", 136, true))

        val actualListSize = rollingStockService.getAllRollingStock().size
        assertEquals(2, actualListSize)
    }
}