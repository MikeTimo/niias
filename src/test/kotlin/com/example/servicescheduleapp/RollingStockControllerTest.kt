package com.example.servicescheduleapp

import com.example.servicescheduleapp.controller.RollingStockController
import com.example.servicescheduleapp.exception.BadRequestException
import com.example.servicescheduleapp.exception.NotFoundException
import com.example.servicescheduleapp.model.RollingStock
import com.example.servicescheduleapp.service.RollingStockService
import org.junit.jupiter.api.Test

import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(RollingStockController::class)
class RollingStockControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var rollingStockService: RollingStockService

    @Test
    fun getRollingStockById() {
        val rollingStock1 = RollingStock(1, "ЭС2Г", 113, true)

        given(rollingStockService.getRollingStockById(1)).willReturn(rollingStock1)
        mockMvc.perform(get("/rolling-stock/1")).andExpect(status().isOk)
    }

    @Test
    fun getRollingStockByIdIsZero() {
        val rollingStockId = 0

        given(rollingStockService.getRollingStockById(rollingStockId)).willAnswer {throw BadRequestException()}
        mockMvc.perform(get("/rolling-stock/0")).andExpect(status().isBadRequest)
    }

    @Test
    fun getRollingStockByIdIsWrong() {
        val rollingStockId = 10

        given(rollingStockService.getRollingStockById(rollingStockId)).willAnswer {throw NotFoundException()}
        mockMvc.perform(get("/rolling-stock/10")).andExpect(status().isNotFound)
    }

    @Test
    fun getRollingStockByNumber() {
        val rollingStock1 = RollingStock(1, "ЭС2Г", 113, true)

        given(rollingStockService.getRollingStockByNumber(113)).willReturn(rollingStock1)
        mockMvc.perform(get("/rolling-stock?number=113")).andExpect(status().isOk)
    }

    @Test
    fun getRollingStockByNumberIsZero() {
        val rollingStockNumber = 0

        given(rollingStockService.getRollingStockByNumber(rollingStockNumber)).willAnswer {throw BadRequestException()}
        mockMvc.perform(get("/rolling-stock?number=0")).andExpect(status().isBadRequest)
    }

    @Test
    fun getRollingStockByNumberIsWrong() {
        val rollingStockNumber = 10

        given(rollingStockService.getRollingStockByNumber(rollingStockNumber)).willAnswer {throw NotFoundException()}
        mockMvc.perform(get("/rolling-stock?number=10")).andExpect(status().isNotFound)
    }

    @Test
    fun getAllRollingStock() {
        val rollingStock1 = RollingStock(1, "ЭС2Г", 113, true)
        val rollingStock2 = RollingStock(2, "ЭС2Г", 136, true)

        val rollingStocks = arrayListOf<RollingStock>(rollingStock1, rollingStock2)

        given(rollingStockService.getAllRollingStock()).willReturn(rollingStocks)
        mockMvc.perform(get("/rolling-stock/list")).andExpect(status().isOk)
    }
}