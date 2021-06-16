package com.example.servicescheduleapp.service

import com.example.servicescheduleapp.config.RollingStockProperties
import com.example.servicescheduleapp.exception.BadRequestException
import com.example.servicescheduleapp.exception.NotFoundException
import com.example.servicescheduleapp.model.RollingStock
import org.springframework.stereotype.Service
import kotlin.streams.toList

@Service
class RollingStockService(val rollingStockProperties: RollingStockProperties) {
    /**
     * Список поездов, для которых нужно составить расписание
     */
    var listUsedOfRollingStock: MutableList<RollingStock> = ArrayList()

    init {
        createUsedRollingStockList()
    }

    @Synchronized
    fun getRollingStockById(id: Int): RollingStock {
        if (id == 0) throw BadRequestException("RollingStock id = $id")
        return listUsedOfRollingStock.firstOrNull { x -> x.id == id }
                ?: throw NotFoundException("RollingStock with id = $id not found")
    }

    @Synchronized
    fun getRollingStockByNumber(number: Int): RollingStock {
        if (number == 0) throw BadRequestException("RollingStock id = $number")
        return listUsedOfRollingStock.firstOrNull { x -> x.number == number }
                ?: throw NotFoundException("RollingStock with id = $number not found")
    }

    @Synchronized
    fun getAllRollingStock(): List<RollingStock> {
        return listUsedOfRollingStock
    }

    /**
     * Метод для получения номера поезда в рандомном порядке
     * @return trainNumber - номер поезда
     */
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

    /**
     * Метод для внесения поездов из файла конфигурации в список поездов для формирования
     * расписания в зависимости от параметра countUsedRollingStock, который указывает на
     * количество поездов, которым нужно составить расписание. Параметр задается в файле конфигурации
     */
    private fun createUsedRollingStockList() {
        listUsedOfRollingStock = rollingStockProperties.rollingStocks.stream().limit(rollingStockProperties.countUsedRollingStock.toLong()).toList() as MutableList<RollingStock>
    }
}