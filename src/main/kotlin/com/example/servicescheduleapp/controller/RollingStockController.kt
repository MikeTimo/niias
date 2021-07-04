package com.example.servicescheduleapp.controller

import com.example.servicescheduleapp.model.RollingStock
import com.example.servicescheduleapp.service.RollingStockService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rolling-stock")
class RollingStockController {

    @Autowired
    lateinit var rollingStockService: RollingStockService

    @RequestMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getRollingStockById(@PathVariable id: Int): RollingStock {
        return rollingStockService.getRollingStockById(id)
    }

    @RequestMapping()
    @ResponseStatus(HttpStatus.OK)
    fun getRollingStockByNumber(@RequestParam(value = "number") number: Int): RollingStock {
        return rollingStockService.getRollingStockByNumber(number)
    }

    @RequestMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    fun getAllRollingStock(): List<RollingStock> {
        return rollingStockService.getAllRollingStock()
    }
}