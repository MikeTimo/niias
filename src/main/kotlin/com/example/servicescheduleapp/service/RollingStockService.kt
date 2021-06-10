package com.example.servicescheduleapp.service

import com.example.servicescheduleapp.config.RollingStockProperties
import com.example.servicescheduleapp.model.RollingStock
import org.springframework.stereotype.Service
import kotlin.streams.toList

@Service
class RollingStockService(val rollingStockProperties: RollingStockProperties) {
    var listUsedOfRollingStock: MutableList<RollingStock> = ArrayList()

    init {
        createUsedRollingStockList()
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