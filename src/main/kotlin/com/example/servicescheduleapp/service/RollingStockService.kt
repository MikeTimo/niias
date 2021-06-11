package com.example.servicescheduleapp.service

import com.example.servicescheduleapp.config.RollingStockProperties
import com.example.servicescheduleapp.exception.BadRequestException
import com.example.servicescheduleapp.exception.NotFoundException
import com.example.servicescheduleapp.model.RollingStock
import org.springframework.stereotype.Service
import kotlin.streams.toList

@Service
class RollingStockService(val rollingStockProperties: RollingStockProperties) {
    var listUsedOfRollingStock: MutableList<RollingStock> = ArrayList()

    init {
        createUsedRollingStockList()
    }

    fun getRollingStockById(id: Int): RollingStock {
        if (id == 0) throw BadRequestException("RollingStock id = $id")
        val rollingStock = listUsedOfRollingStock.filter { x -> x.id == id }.firstOrNull()
        if (rollingStock == null) throw NotFoundException("RollingStock with id = $id not found")
        return rollingStock
    }

    fun getRollingStockByNumber(number: Int): RollingStock {
        if (number == 0) throw BadRequestException("RollingStock id = $number")
        val rollingStock = listUsedOfRollingStock.filter { x -> x.number == number }.firstOrNull()
        if (rollingStock == null) throw NotFoundException("RollingStock with id = $number not found")
        return rollingStock
    }

    fun getAllRollingStock(): List<RollingStock> {
        return listUsedOfRollingStock
    }

    fun getRandomTrainNumber(): Int {
        val train = listUsedOfRollingStock[(listUsedOfRollingStock.indices).random()]
        var trainNumber = 0
        if (train.isAvailable) {
            train.isAvailable = false
            trainNumber = train.number
        } else {
            trainNumber = getRandomTrainNumber()
        }
        return trainNumber
    }

    private fun createUsedRollingStockList() {
        listUsedOfRollingStock = rollingStockProperties.rollingStocks.stream().limit(rollingStockProperties.countUsedRollingStock.toLong()).toList() as MutableList<RollingStock>
    }
}