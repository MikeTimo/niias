package com.example.servicescheduleapp.config

import com.example.servicescheduleapp.model.RollingStock
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import kotlin.collections.ArrayList

@Component
@ConfigurationProperties(prefix = "app")
class RollingStockProperties {
    /** Количество поездовЮ для которых нужно составить расписание движения */
    var countUsedRollingStock: Int = 0

    /** Список подвижного состава */
    var rollingStocks: List<RollingStock> = ArrayList()
}