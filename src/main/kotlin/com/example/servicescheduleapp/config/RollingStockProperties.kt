package com.example.servicescheduleapp.config

import com.example.servicescheduleapp.model.RollingStock
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app-rolling-stock")
class RollingStockProperties {
    var rollingStocks: List<RollingStock> = ArrayList()

    fun getRandomTrainNumber(): Int{
        val trains = rollingStocks
        val train = trains[(trains.indices).random()]
        var trainNumber = 0
        if (train.isAvailable) {
            train.isAvailable = false
            trainNumber = train.number
        } else {
            trainNumber = getRandomTrainNumber()
        }
        return trainNumber
    }
}