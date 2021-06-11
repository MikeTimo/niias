package com.example.servicescheduleapp.config

import com.example.servicescheduleapp.model.RollingStock
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import kotlin.collections.ArrayList

@Component
@ConfigurationProperties(prefix = "app")
class RollingStockProperties {
    var countUsedRollingStock: Int = 0
    var rollingStocks: List<RollingStock> = ArrayList()
}