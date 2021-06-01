package com.example.servicescheduleapp.config

import com.example.servicescheduleapp.model.RollingStock
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "app-rolling-stock")
data class RollingStockProperties(val rollingStocks: List<RollingStock>) {

    fun getRandomTrainNumber(): Int{
        val trains = rollingStocks
        val train = trains[(0 until trains.size).random()]
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