package com.example.servicescheduleapp.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app")
class StationsProperties {

    /** Список станций */
    val stations: List<String> = ArrayList()
}