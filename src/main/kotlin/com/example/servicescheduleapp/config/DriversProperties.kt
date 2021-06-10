package com.example.servicescheduleapp.config

import com.example.servicescheduleapp.model.Driver
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app")
class DriversProperties() {
    var drivers: List<Driver> = ArrayList()
}